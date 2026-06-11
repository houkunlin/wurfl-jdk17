package com.scientiamobile.wurfl.core.resource;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * WURFL 设备数据模型。
 * <p>表示 WURFL 设备数据库中的单个设备实体，包含设备的唯一标识、
 * User-Agent、回退设备（fall_back）、功能点定义及其分组等信息。
 * 设备之间通过 fall_back 形成继承链，子设备可以继承和覆盖父设备的功能点。
 * 该对象通过 {@link ModelDeviceBuilder} 构建。</p>
 */

public class ModelDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = 10L;
    private static final boolean ASSERTIONS_DISABLED = !ModelDevice.class.desiredAssertionStatus();
    /**
     * 设备的 User-Agent 字符串，用于设备检测匹配
     */
    private String userAgent;
    /**
     * 设备的唯一标识 ID
     */
    private String id;
    /**
     * 回退设备的 ID，形成设备继承链
     */
    private String fallBack;
    /**
     * 是否为实际的设备根节点（即该设备自身就是匹配目标，而非中间节点）
     */
    private boolean actualDeviceRoot;
    /**
     * 设备定义的功能点名称到值的映射
     */
    private Map<String, String> capabilities;
    /**
     * 功能点名称到所属组 ID 的映射
     */
    private Map<String, String> groupsByCapability;
    /**
     * 缓存的最接近的祖先设备
     */
    private ModelDevice ancestor;

    protected ModelDevice() {
    }

    public ModelDevice(String userAgent, String id, String fallBack, boolean actualDeviceRoot, Map<String, String> capabilities, Map<String, String> groupsByCapability) {
        Validate.notEmpty(id, "The id must be not null");
        Validate.notEmpty(fallBack, "The fallBack must be not null");
        Validate.notEmpty(userAgent, "The userAgent must be not null");
        Validate.notNull(capabilities, "The capabilities must be not null");
        Validate.notNull(groupsByCapability, "The groupsByCapability must be not null");
        Validate.noNullElements(capabilities.values(), "The capabilities can not contain null value");
        Validate.noNullElements(groupsByCapability.values(), "The capabilities can not contain null value");
        Validate.isTrue(capabilities.keySet().equals(groupsByCapability.keySet()), "The capabilities and groups must be same Set");
        this.userAgent = userAgent;
        this.id = id;
        this.fallBack = fallBack;
        this.actualDeviceRoot = actualDeviceRoot;
        this.capabilities = Collections.unmodifiableMap(capabilities);
        this.groupsByCapability = Collections.unmodifiableMap(groupsByCapability);
    }

    /**
     * 获取设备的 User-Agent。
     *
     * @return User-Agent 字符串
     */

    public String getUserAgent() {
        return this.userAgent;
    }

    final void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取设备的唯一标识 ID。
     *
     * @return 设备 ID
     */

    public String getID() {
        return this.id;
    }

    /**
     * 获取回退设备的 ID。
     *
     * @return fall_back 设备 ID
     */
    public String getFallBack() {
        return this.fallBack;
    }

    final void setFallBack(String fallBack) {
        this.fallBack = fallBack;
    }

    /**
     * 判断是否为实际设备根节点（即该设备是否标记为 actual_device_root）。
     *
     * @return 如果是实际设备根节点则返回 true
     */

    public boolean isActualDeviceRoot() {
        return this.actualDeviceRoot;
    }

    final void setActualDeviceRoot(boolean actualDeviceRoot) {
        this.actualDeviceRoot = actualDeviceRoot;
    }

    /**
     * 获取设备定义的所有功能点（能力名称→值）。
     *
     * @return 不可修改的功能点映射
     */

    public Map<String, String> getCapabilities() {
        return this.capabilities;
    }

    final void setCapabilities(Map<String, String> capabilities) {
        this.capabilities = capabilities;
    }

    /**
     * 获取功能点名称到所属组 ID 的映射。
     *
     * @return 不可修改的组映射
     */

    public Map<String, String> getGroupsByCapability() {
        return this.groupsByCapability;
    }

    final void setGroupsByCapability(Map<String, String> groupsByCapability) {
        this.groupsByCapability = groupsByCapability;
    }

    /**
     * 判断当前设备是否定义了指定的功能点。
     *
     * @param capabilityName 功能点名称
     * @return 如果当前设备定义了该功能点则返回 true
     */

    public boolean defineCapability(String capabilityName) {
        return this.capabilities.containsKey(capabilityName);
    }

    /**
     * 获取指定功能点的值。
     *
     * @param capabilityName 功能点名称
     * @return 功能点的值（字符串形式）
     * @throws AssertionError 如果断言启用且当前设备未定义该功能点
     */
    public String getCapability(String capabilityName) {
        if (!ASSERTIONS_DISABLED && !this.defineCapability(capabilityName)) {
            throw new AssertionError(this.id + " do not define " + capabilityName);
        } else {
            return this.capabilities.get(capabilityName);
        }
    }

    /**
     * 判断当前设备是否定义了指定的组（即是否有功能点属于该组）。
     *
     * @param groupId 组 ID
     * @return 如果当前设备定义了该组则返回 true
     */

    public boolean defineGroup(String groupId) {
        return this.groupsByCapability.containsValue(groupId);
    }

    /**
     * 获取当前设备涉及的所有组 ID。
     *
     * @return 组 ID 集合
     */
    public Set<String> getGroups() {
        return new HashSet<>(this.groupsByCapability.values());
    }

    /**
     * 获取指定功能点所属的组 ID。
     *
     * @param capabilityName 功能点名称
     * @return 该功能点所属的组 ID
     * @throws AssertionError 如果断言启用且当前设备未定义该功能点
     */

    public String getGroupForCapability(String capabilityName) {
        if (!ASSERTIONS_DISABLED && !this.defineCapability(capabilityName)) {
            throw new AssertionError();
        } else {
            return this.groupsByCapability.get(capabilityName);
        }
    }

    /**
     * 获取指定组中的所有功能点名称。
     *
     * @param groupId 组 ID
     * @return 该组中的功能点名称集合
     * @throws AssertionError 如果断言启用且当前设备未定义该组
     */

    public Set<String> getCapabilitiesNamesForGroup(String groupId) {
        if (!ASSERTIONS_DISABLED && !this.defineGroup(groupId)) {
            throw new AssertionError();
        } else {
            HashSet<String> capabilityNames = new HashSet<>();

            for (Map.Entry<String, String> entry : this.groupsByCapability.entrySet()) {
                if (entry.getValue().equals(groupId)) {
                    capabilityNames.add(entry.getKey());
                }
            }

            return capabilityNames;
        }
    }

    /**
     * 获取指定组中的所有功能点（名称→值）。
     *
     * @param groupId 组 ID
     * @return 该组中的功能点映射
     */

    public Map<String, String> getCapabilitiesForGroup(String groupId) {
        HashMap<String, String> capabilities = new HashMap<>();

        for (String capabilityName : this.getCapabilitiesNamesForGroup(groupId)) {
            capabilities.put(capabilityName, this.capabilities.get(capabilityName));
        }

        return capabilities;
    }

    /**
     * 获取该设备的最接近的祖先设备（通过 setAncestor 设置）。
     *
     * @return 祖先设备，可能为 null
     */

    public ModelDevice getAncestor() {
        return this.ancestor;
    }

    /**
     * 设置该设备的最接近的祖先设备。
     *
     * @param ancestor 祖先设备
     */
    public void setAncestor(ModelDevice ancestor) {
        this.ancestor = ancestor;
    }

    /**
     * 计算哈希码，基于设备的 ID。
     *
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(11, 45);
        hashCodeBuilder.append(this.getClass()).append(this.id);
        return hashCodeBuilder.toHashCode();
    }

    /**
     * 判断两个设备是否相等，基于设备的 ID 进行比较。
     *
     * @param obj 要比较的对象
     * @return 如果设备 ID 相同则返回 true
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof ModelDevice)) {
            return false;
        } else {
            ModelDevice other = (ModelDevice) obj;
            return (new EqualsBuilder()).append(this.id, other.id).isEquals();
        }
    }

    /**
     * 返回设备的字符串表示。
     *
     * @return 包含设备 ID 的字符串
     */
    @Override
    public String toString() {
        ToStringBuilder toStringBuilder = new ToStringBuilder(this);
        toStringBuilder.append(this.id);
        return toStringBuilder.toString();
    }

    final void setId(String id) {
        this.id = id;
    }
}
