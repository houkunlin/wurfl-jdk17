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
 * Handler for Wurfl Xml operations.
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
     * Star tocument.
     */

    public final void startDocument() {
        this.seenUserAgents = new HashSet<>();
        this.seenDeviceIds = new HashSet<>();
        this.devices = new ModelDevices();
    }

    /**
     * En document.
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
     * En dlement.
     */

    public final void endElement(String uri, String localName, String qName) {
        switch (this.parseState) {
            case WurflXmlParseState.WURFL:
                if ("wurfl".equals(qName) || "wurfl_patch".equals(qName)) {
                    this.parseState = WurflXmlParseState.END;
                    return;
                }
                break;
            case WurflXmlParseState.VERSION:
                if ("version".equals(qName)) {
                    this.parseState = WurflXmlParseState.WURFL;
                    return;
                }
                break;
            case WurflXmlParseState.DEVICES:
                if ("devices".equals(qName)) {
                    this.parseState = WurflXmlParseState.WURFL;
                    return;
                }
                break;
            case WurflXmlParseState.DEVICE:
                if ("device".equals(qName)) {
                    ModelDevice modelDevice = (new ModelDeviceBuilder(this.currentDeviceId, this.currentUserAgent, this.currentFallback)).setActualDeviceRoot(this.currentActualDeviceRoot).setCapabilities(this.currentCapabilities).setCapabilitiesByGroup(this.currentCapabilitiesByGroup).build();
                    this.devices.add(modelDevice);
                    if (modelDevice.isActualDeviceRoot()) {
                        this.actualDeviceRootsById.put(this.currentDeviceId, modelDevice);
                    }

                    this.parseState = WurflXmlParseState.DEVICES;
                    return;
                }
                break;
            case WurflXmlParseState.GROUP:
                if ("group".equals(qName)) {
                    this.parseState = WurflXmlParseState.DEVICE;
                    return;
                }
                break;
            case WurflXmlParseState.VERSION_VER:
                if ("ver".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                    return;
                }
                break;
            case WurflXmlParseState.VERSION_LAST_UPDATED:
                if ("last_updated".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                    return;
                }
                break;
            case WurflXmlParseState.VERSION_SMID:
                if ("smid".equals(qName)) {
                    this.parseState = WurflXmlParseState.VERSION;
                    return;
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
