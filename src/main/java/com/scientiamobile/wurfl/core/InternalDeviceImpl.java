package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 内部设备实现，持有设备的基本信息和能力持有器。
 * <p>实现了 {@link InternalDevice} 接口，封装了设备 ID、User-Agent、能力值、
 * 设备树结构等核心数据。能力值通过 {@link CapabilitiesHolder} 按需获取，
 * 并过滤掉控制能力（以 {@code controlcap_} 开头的能力）。</p>
 */

class InternalDeviceImpl implements InternalDevice, Serializable {
    @Serial
    private static final long serialVersionUID = 101L;
    /**
     * 设备 ID
     */
    private final String id;
    /** WURFL 数据中定义的设备 User-Agent 字符串 */
    private final String wurflUserAgent;
    /** 是否为设备树的实际根节点 */
    private final boolean actualDeviceRoot;
    /** 设备所属树的根节点 ID */
    private final String deviceRootId;
    /** 能力持有器，用于按需获取能力值 */
    private final transient CapabilitiesHolder capabilitiesHolder;
    /** 祖先模型设备 */
    private ModelDevice ancestorModelDevice;

    protected InternalDeviceImpl(ModelDevice modelDevice, String ancestorId, CapabilitiesHolder capabilitiesHolder) {
        this(modelDevice.getID(), modelDevice.getUserAgent(), modelDevice.isActualDeviceRoot(), ancestorId, capabilitiesHolder);
        this.ancestorModelDevice = modelDevice.getAncestor();
    }

    private InternalDeviceImpl(String id, String wurflUserAgent, boolean actualDeviceRoot, String deviceRootId, CapabilitiesHolder capabilitiesHolder) {
        Validate.notNull(capabilitiesHolder, "The capabilitiesHolder must be not null");
        this.id = id;
        this.wurflUserAgent = wurflUserAgent;
        this.actualDeviceRoot = actualDeviceRoot;
        this.deviceRootId = deviceRootId;
        this.capabilitiesHolder = capabilitiesHolder;
    }
    /**
     * 获取设备 ID。
     *
     * @return 设备 ID
     */
    @Override
    public String getId() {
        return this.id;
    }
    /**
     * 获取 WURFL 数据中定义的设备 User-Agent。
     *
     * @return User-Agent 字符串
     */
    @Override
    public String getWURFLUserAgent() {
        return this.wurflUserAgent;
    }
    /**
     * 获取指定能力名称的值。
     *
     * @param capabilityName 能力名称
     * @return 能力值
     */
    @Override
    public String getCapability(String capabilityName) {
        return this.capabilitiesHolder.getCapability(capabilityName);
    }

    /**
     * 获取祖先模型设备。
     *
     * @return 祖先模型设备实例
     */
    final ModelDevice getAncestorModelDevice() {
        return this.ancestorModelDevice;
    }
    /**
     * 获取能力值并转换为整数。
     *
     * @param capabilityName 能力名称
     * @return 整型的能力值
     */
    @Override
    public int getCapabilityAsInt(String capabilityName) {
        return this.capabilitiesHolder.getCapabilityAsInt(capabilityName);
    }
    /**
     * 获取能力值并转换为布尔值。
     * <p>值 {@code true}（不区分大小写）对应 {@code true}，其他值抛出异常。</p>
     *
     * @param capabilityName 能力名称
     * @return 布尔型的能力值
     * @throws NumberFormatException 如果能力值不是合法的布尔值
     */
    @Override
    public boolean getCapabilityAsBool(String capabilityName) {
        String originalCapabilityName = capabilityName;
        capabilityName = this.capabilitiesHolder.getCapability(capabilityName);
        if (capabilityName != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("true")) {
            return true;
        } else if (capabilityName != null && capabilityName.toLowerCase(Locale.ENGLISH).equals("false")) {
            return false;
        } else {
            throw new NumberFormatException("WURFL invalid capability value: " + originalCapabilityName + " expected \"true\" or \"false\", received: \"" + capabilityName + "\"");
        }
    }
    /**
     * 获取设备的所有能力映射（排除控制能力）。
     *
     * @return 能力名称到值的映射
     */
    @Override
    public Map<String, String> getCapabilities() {
        Map<String, String> allCapabilities = this.capabilitiesHolder.getCapabilities();
        HashMap<String, String> filteredCapabilities = new HashMap<>(allCapabilities.size());

        for (String s : allCapabilities.keySet()) {
            String capabilityName;
            capabilityName = s;
            if (!capabilityName.startsWith("controlcap_")) {
                filteredCapabilities.put(capabilityName, allCapabilities.get(capabilityName));
            }
        }

        return filteredCapabilities;
    }
    /**
     * 判断该设备是否是其设备树的实际根节点。
     *
     * @return 如果是实际设备根节点返回 {@code true}
     */
    @Override
    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }
    /**
     * 获取该设备所属设备树的根节点 ID。
     * <p>如果根节点是 {@code generic}，则返回空字符串。</p>
     *
     * @return 设备树根节点 ID
     */
    @Override
    public String getDeviceRootId() {
        String rootId = this.deviceRootId;
        if (this.deviceRootId.equals("generic")) {
            rootId = "";
        }

        return rootId;
    }
    /**
     * 判断两个设备是否相等（基于设备 ID 比较）。
     *
     * @param other 要比较的对象
     * @return 如果设备 ID 相同返回 {@code true}
     */
    @Override
    public boolean equals(Object other) {
        EqualsBuilder eb;
        eb = new EqualsBuilder();
        eb.appendSuper(this.getClass().isInstance(other));
        if (eb.isEquals()) {
            eb.append(this.id, ((InternalDeviceImpl) other).id);
        }

        return eb.isEquals();
    }
    /**
     * 计算该设备的哈希码（基于设备 ID 和类）。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.id).toHashCode();
    }
    /**
     * 返回设备的字符串表示，格式为 {@code [设备ID, match=, matcher=]}。
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return "[" + this.id + ", match=, matcher=]";
    }
}
