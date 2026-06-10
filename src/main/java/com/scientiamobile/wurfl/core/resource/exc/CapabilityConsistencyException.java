package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 功能点（Capability）一致性校验异常的抽象基类。
 * <p>在校验设备的功能点数据时，如果发现功能点未在 generic 中定义
 * 或分组信息不正确，则使用此类或其子类包装异常。
 * 该类持有关联的设备和有问题的功能点名称。</p>
 */

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 存在一致性问题的功能点名称
     */
   private final String capabilityName;

    /**
     * 使用指定设备、功能点名称构造异常，异常消息会自动包含设备和功能点信息。
     *
     * @param device         关联的设备
     * @param capabilityName 有问题的功能点名称
     */
    protected CapabilityConsistencyException(ModelDevice device, String capabilityName) {
        super(device, "Device: " + device.getID() + " Capability: " + capabilityName + " consistency error");
        this.capabilityName = capabilityName;
    }

    /**
     * 使用指定设备、功能点名称和自定义消息构造异常。
     *
     * @param device         关联的设备
     * @param capabilityName 有问题的功能点名称
     * @param message        自定义异常描述信息
     */
    protected CapabilityConsistencyException(ModelDevice device, String capabilityName, String message) {
        super(device, message);
        this.capabilityName = capabilityName;
    }

    /**
     * 获取存在一致性问题的功能点名称。
     *
     * @return 功能点名称
     */

    public String getCapability() {
        return this.capabilityName;
    }
}
