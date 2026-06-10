package com.scientiamobile.wurfl.core.resource;

import java.util.Map;

/**
 * 设备对象构建器（Builder 模式）。
 * <p>用于逐步构建 {@link ModelDevice} 实例。由于 ModelDevice 需要同时设置
 * 多个关联字段（如 capabilities 和 groupsByCapability），使用构建器可以
 * 避免构造函数参数过长，并在构建过程中保持对象状态的一致性。</p>
 */

public final class ModelDeviceBuilder {
    private final ModelDevice device = new ModelDevice();

    /**
     * 使用设备的三个必需属性创建构建器。
     *
     * @param deviceId  设备唯一标识
     * @param userAgent 设备的 User-Agent
     * @param fallBack  回退设备 ID
     */
    public ModelDeviceBuilder(String deviceId, String userAgent, String fallBack) {
        this.device.setId(deviceId);
        this.device.setUserAgent(userAgent);
        this.device.setFallBack(fallBack);
    }

    /**
     * 设置是否为实际设备根节点。
     *
     * @param actualDeviceRoot 是否为实际设备根节点
     * @return 当前构建器实例（链式调用）
     */

    public final ModelDeviceBuilder setActualDeviceRoot(boolean actualDeviceRoot) {
        this.device.setActualDeviceRoot(actualDeviceRoot);
        return this;
    }

    /**
     * 设置设备的功能点（能力名称→值）映射。
     *
     * @param capabilities 功能点映射
     * @return 当前构建器实例（链式调用）
     */

    public final ModelDeviceBuilder setCapabilities(Map<String, String> capabilities) {
        this.device.setCapabilities(capabilities);
        return this;
    }

    /**
     * 设置功能点到所属组的映射关系。
     *
     * @param groupsByCapability 功能点名称到组 ID 的映射
     * @return 当前构建器实例（链式调用）
     */

    public final ModelDeviceBuilder setCapabilitiesByGroup(Map<String, String> groupsByCapability) {
        this.device.setGroupsByCapability(groupsByCapability);
        return this;
    }

    /**
     * 设置设备的祖先设备。
     *
     * @param ancestor 祖先设备
     * @return 当前构建器实例（链式调用）
     */

    public final ModelDeviceBuilder setAncestor(ModelDevice ancestor) {
        this.device.setAncestor(ancestor);
        return this;
    }

    /**
     * 构建最终的 ModelDevice 实例。
     *
     * @return 构建完成的设备对象
     */

    public final ModelDevice build() {
        return this.device;
    }
}
