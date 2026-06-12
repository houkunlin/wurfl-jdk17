package com.scientiamobile.wurfl.core.resource;

import com.scientiamobile.wurfl.core.resource.exc.WURFLParsingException;
import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * WURFL XML 数据的 SAX 解析处理器。
 * <p>使用 SAX 事件驱动模型解析 WURFL XML 格式的设备数据，
 * 通过状态机跟踪解析进度，逐步构建设备对象及其功能点。
 * 支持主 WURFL 文件和补丁文件的解析，并在解析过程中进行
 * 基本的合法性校验（如设备 ID 和 User-Agent 的唯一性）。</p>
 */

final class WurflXmlHandler extends DefaultHandler {
    private final Map<String, ModelDevice> actualDeviceRootsById;
    private int parseState;
    private String currentUserAgent;
    private String currentDeviceId;
    private String currentFallback;
    private boolean currentActualDeviceRoot;
    private String currentGroupId;
    private String currentCapabilityName;
    private String currentCapabilityValue;
    private Set<String> seenUserAgents;
    private Set<String> seenDeviceIds;
    private Map<String, String> currentCapabilities;
    private Map<String, String> currentCapabilitiesByGroup;
    private ModelDevices devices;
    private String wurflVersion;
    private String wurflLastUpdated;
    private String wurflSmid;
    private boolean patch;
    private Set<String> includedCapabilities;

    WurflXmlHandler(Set<String> includedCapabilities) {
        this.actualDeviceRootsById = new HashMap<>();
        this.patch = false;
        this.includedCapabilities = includedCapabilities;
        this.parseState = WurflXmlParseState.START_DOCUMENT;
    }

    /**
     * 文档解析开始时的回调。
     * <p>初始化去重集合和设备容器，准备开始解析设备数据。</p>
     */

    public final void startDocument() {
        this.seenUserAgents = new HashSet<>();
        this.seenDeviceIds = new HashSet<>();
        this.devices = new ModelDevices();
    }

    /**
     * 文档解析结束时的回调。
     * <p>当前为无操作实现，所有数据处理逻辑已在元素结束事件中完成。</p>
     */

    public final void endDocument() {
    }

    public final void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("capability") && this.parseState != WurflXmlParseState.GROUP) {
            String capabilityName = attributes.getValue("name");
            throw new WURFLParsingException("Capability '" + capabilityName + "'  does not belong to any group");
        } else {
            switch (this.parseState) {
                case WurflXmlParseState.START_DOCUMENT:
                    this.patch = "wurfl_patch".equals(qName);
                    if ("wurfl".equals(qName) || this.patch) {
                        this.parseState = WurflXmlParseState.WURFL;
                        return;
                    }
                    break;
                case WurflXmlParseState.WURFL:
                    if ("version".equals(qName)) {
                        this.parseState = WurflXmlParseState.VERSION;
                        return;
                    }

                    if ("devices".equals(qName)) {
                        this.parseState = WurflXmlParseState.DEVICES;
                        return;
                    }
                    break;
                case WurflXmlParseState.VERSION:
                    if ("ver".equals(qName)) {
                        this.parseState = WurflXmlParseState.VERSION_VER;
                        return;
                    }

                    if ("last_updated".equals(qName)) {
                        this.parseState = WurflXmlParseState.VERSION_LAST_UPDATED;
                        return;
                    }

                    if ("smid".equals(qName)) {
                        this.parseState = WurflXmlParseState.VERSION_SMID;
                        return;
                    }
                    break;
                case WurflXmlParseState.DEVICES:
                    if ("device".equals(qName)) {
                        this.parseState = WurflXmlParseState.DEVICE;
                        this.currentUserAgent = attributes.getValue("user_agent");
                        this.currentDeviceId = attributes.getValue("id");
                        this.currentFallback = attributes.getValue("fall_back");
                        this.currentActualDeviceRoot = Boolean.valueOf(attributes.getValue("actual_device_root"));
                        if (StringUtils.isEmpty(this.currentDeviceId)) {
                            throw new WURFLParsingException("device id is not a valid");
                        }

                        if (!"generic".equals(this.currentDeviceId) && StringUtils.isEmpty(this.currentUserAgent)) {
                            throw new WURFLParsingException("Device with id " + this.currentDeviceId + " has an invalid user agent");
                        }

                        if (this.seenDeviceIds.contains(this.currentDeviceId)) {
                            throw new WURFLParsingException("device id " + this.currentDeviceId + " already defined!!!");
                        }

                        if (this.seenUserAgents.contains(this.currentUserAgent)) {
                            throw new WURFLParsingException("user agent [" + this.currentUserAgent + "] already defined");
                        }

                        this.seenUserAgents.add(this.currentUserAgent);
                        this.seenDeviceIds.add(this.currentDeviceId);
                        this.currentCapabilities = new HashMap<>();
                        this.currentCapabilitiesByGroup = new HashMap<>();
                        return;
                    }
                    break;
                case WurflXmlParseState.DEVICE:
                    if ("group".equals(qName)) {
                        this.parseState = WurflXmlParseState.GROUP;
                        this.currentGroupId = attributes.getValue("id").intern();
                        return;
                    }
                    break;
                case WurflXmlParseState.GROUP:
                    if ("capability".equals(qName)) {
                        this.parseState = WurflXmlParseState.CAPABILITY;
                        if (!"virtual_capabilities".equals(this.currentGroupId)) {
                            this.currentCapabilityName = attributes.getValue("name");
                            if (this.includedCapabilities.isEmpty() || this.includedCapabilities.contains(this.currentCapabilityName) || this.currentCapabilityName.startsWith("controlcap_")) {
                                this.currentCapabilityValue = attributes.getValue("value");
                                if (StringUtils.isEmpty(this.currentCapabilityName) || this.currentCapabilityValue == null) {
                                    throw new WURFLParsingException("device with id " + this.currentDeviceId + " has capability with name or value not valid");
                                }

                                if (this.currentCapabilities.containsKey(this.currentCapabilityName)) {
                                    throw new WURFLParsingException("The device with id " + this.currentDeviceId + " defines capability " + this.currentCapabilityName + "more than once");
                                }

                                String internedCapabilityName = this.currentCapabilityName.intern();
                                if (!"experimental".equals(this.currentGroupId)) {
                                    String capabilityValue = this.currentCapabilityValue;
                                    if (StringUtils.isNotEmpty(capabilityValue) && capabilityValue.length() > 255) {
                                        capabilityValue = capabilityValue.substring(0, 255);
                                    }

                                    this.currentCapabilityValue = capabilityValue;
                                }

                                this.currentCapabilities.put(internedCapabilityName, this.currentCapabilityValue);
                                this.currentCapabilitiesByGroup.put(internedCapabilityName, this.currentGroupId);
                            }
                        }
                    }
                    break;
                default:
            }

        }
    }

    /**
     * XML 元素结束事件处理。
     * <p>根据当前解析状态处理元素关闭逻辑，包括设备对象的构建、
     * 状态回退等。每当遇到关闭标签时会根据状态机切换到相应的上一级状态。</p>
     *
     * @param uri       命名空间 URI
     * @param localName 本地名称
     * @param qName     限定名
     */
    @Override
    public final void endElement(String uri, String localName, String qName) {
        switch (this.parseState) {
            case WurflXmlParseState.WURFL:
                if ("wurfl".equals(qName) || "wurfl_patch".equals(qName)) {
                    this.parseState = WurflXmlParseState.END;
                }
                break;
            case WurflXmlParseState.VERSION:
                if ("version".equals(qName)) {
                    this.parseState = WurflXmlParseState.WURFL;
                }
                break;
            case WurflXmlParseState.DEVICES:
                if ("devices".equals(qName)) {
                    this.parseState = WurflXmlParseState.WURFL;
                }
                break;
            case WurflXmlParseState.DEVICE:
                if ("device".equals(qName)) {
                    buildDevice();
                }
                break;
            case WurflXmlParseState.GROUP:
                if ("group".equals(qName)) {
                    this.parseState = WurflXmlParseState.DEVICE;
                }
                break;
            case WurflXmlParseState.VERSION_VER:
                if ("ver".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                }
                break;
            case WurflXmlParseState.VERSION_LAST_UPDATED:
                if ("last_updated".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                }
                break;
            case WurflXmlParseState.VERSION_SMID:
                if ("smid".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                }
                break;
            case WurflXmlParseState.CAPABILITY:
                if ("capability".equals(qName)) {
                    this.parseState = WurflXmlParseState.GROUP;
                }
                break;
            default:
        }
    }

    /**
     * 构建设备对象并加入设备集合。
     * 从当前解析上下文中收集设备 ID、User-Agent、fallback、capabilities 等信息，
     * 通过 {@link ModelDeviceBuilder} 构建 ModelDevice，
     * 并将其添加到当前解析结果的设备集中。
     */
    private void buildDevice() {
        ModelDevice modelDevice = new ModelDeviceBuilder(this.currentDeviceId, this.currentUserAgent, this.currentFallback)
                .setActualDeviceRoot(this.currentActualDeviceRoot)
                .setCapabilities(this.currentCapabilities)
                .setCapabilitiesByGroup(this.currentCapabilitiesByGroup)
                .build();
        this.devices.add(modelDevice);
        if (modelDevice.isActualDeviceRoot()) {
            this.actualDeviceRootsById.put(this.currentDeviceId, modelDevice);
        }
        this.parseState = WurflXmlParseState.DEVICES;
    }

    /**
     * 处理元素中的文本内容。
     * <p>在版本信息相关的状态下，提取 ver、last_updated 和 smid 的文本值。</p>
     *
     * @param ch     字符数组
     * @param start  起始偏移
     * @param length 字符长度
     */

    public final void characters(char[] ch, int start, int length) {
        switch (this.parseState) {
            case WurflXmlParseState.VERSION_VER:
                this.wurflVersion = new String(ch, start, length);
                return;
            case WurflXmlParseState.VERSION_LAST_UPDATED:
                this.wurflLastUpdated = new String(ch, start, length);
                return;
            case WurflXmlParseState.VERSION_SMID:
                this.wurflSmid = new String(ch, start, length);
                break;
            default:
        }
    }

    String getWurflVersion() {
        return this.wurflVersion;
    }

    String getWurflLastUpdated() {
        return this.wurflLastUpdated;
    }

    String getWurflSmid() {
        return this.wurflSmid;
    }

    boolean isPatch() {
        return this.patch;
    }

    ModelDevices getDevices() {
        return this.devices;
    }
}
