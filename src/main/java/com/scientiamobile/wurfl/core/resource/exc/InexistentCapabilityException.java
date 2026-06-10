package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 引用不存在的功能点异常。
 * <p>在加载设备时，如果设备定义了在 generic 设备中不存在的功能点（Capability），
 * 则抛出此异常。所有设备使用的功能点必须在 generic 中预先声明。</p>
 */

public class InexistentCapabilityException extends CapabilityConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 构造引用不存在功能点异常。
     *
     * @param device         定义了未知功能点的设备
     * @param capabilityName 不存在的功能点名称
     */
    public InexistentCapabilityException(ModelDevice device, String capabilityName) {
        super(device, capabilityName, "Device: " + device.getID() + " define unknown capability: " + capabilityName);
    }
}
