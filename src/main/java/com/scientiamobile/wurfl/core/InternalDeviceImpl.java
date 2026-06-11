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
    private final String id;
    private final String wurflUserAgent;
    private final boolean actualDeviceRoot;
    private final String deviceRootId;
    private final transient CapabilitiesHolder capabilitiesHolder;
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

    @Override
    /**
     * 获取设备 ID。
     *
     * @return 设备 ID
     */

    public String getId() {
        return this.id;
    }

    @Override
    /**
     * 获取 WURFL 数据中定义的设备 User-Agent。
     *
     * @return User-Agent 字符串
     */

    public String getWURFLUserAgent() {
        return this.wurflUserAgent;
    }

    @Override
    /**
     * 获取指定能力名称的值。
     *
     * @param capabilityName 能力名称
     * @return 能力值
     */

    public String getCapability(String capabilityName) {
        return this.capabilitiesHolder.getCapability(capabilityName);
    }

    final ModelDevice getAncestorModelDevice() {
        return this.ancestorModelDevice;
    }

    @Override
    /**
     * 获取能力值并转换为整数。
     *
     * @param capabilityName 能力名称
     * @return 整型的能力值
     */

    public int getCapabilityAsInt(String capabilityName) {
        return this.capabilitiesHolder.getCapabilityAsInt(capabilityName);
    }

    @Override
    /**
     * 获取能力值并转换为布尔值。
     * <p>值 {@code true}（不区分大小写）对应 {@code true}，其他值抛出异常。</p>
     *
     * @param capabilityName 能力名称
     * @return 布尔型的能力值
     * @throws NumberFormatException 如果能力值不是合法的布尔值
     */

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


    @Override
    /**
     * 获取设备的所有能力映射（排除控制能力）。
     *
     * @return 能力名称到值的映射
     */

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

    @Override
    /**
     * 判断该设备是否是其设备树的实际根节点。
     *
     * @return 如果是实际设备根节点返回 {@code true}
     */

    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }

    @Override
    /**
     * 获取该设备所属设备树的根节点 ID。
     * <p>如果根节点是 {@code generic}，则返回空字符串。</p>
     *
     * @return 设备树根节点 ID
     */

    public String getDeviceRootId() {
        String rootId = this.deviceRootId;
        if (this.deviceRootId.equals("generic")) {
            rootId = "";
        }

        return rootId;
    }

    @Override
    /**
     * 判断两个设备是否相等（基于设备 ID 比较）。
     *
     * @param other 要比较的对象
     * @return 如果设备 ID 相同返回 {@code true}
     */

    public boolean equals(Object other) {
        EqualsBuilder eb;
        eb = new EqualsBuilder();
        eb.appendSuper(this.getClass().isInstance(other));
        if (eb.isEquals()) {
            eb.append(this.id, ((InternalDeviceImpl) other).id);
        }

        return eb.isEquals();
    }

    @Override
    /**
     * 计算该设备的哈希码（基于设备 ID 和类）。
     *
     * @return 哈希码
     */

    public int hashCode() {
        return (new HashCodeBuilder(63, 89)).append(this.getClass()).append(this.id).toHashCode();
    }

    @Override
    /**
     * 返回设备的字符串表示，格式为 {@code [设备ID, match=, matcher=]}。
     *
     * @return 字符串表示
     */

    public String toString() {
        return "[" + this.id + ", match=, matcher=]";
    }
}
