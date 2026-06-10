package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * 设备数据一致性校验异常的抽象基类。
 * <p>在校验 WURFL 设备数据的合法性时，如果发现某个设备的数据存在问题，
 * 则使用此类或其子类来包装异常信息。该类持有关联的 {@link ModelDevice} 实例，
 * 以便定位具体是哪个设备触发了异常。</p>
 */

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 触发一致性校验异常的设备对象
     */
    private final ModelDevice device;

    /**
     * 使用指定设备和自定义消息构造异常。
     *
     * @param device  触发异常的设备
     * @param message 自定义异常描述信息
     */
    protected DeviceConsistencyException(ModelDevice device, String message) {
        super(message);
        this.device = device;
    }

    /**
     * 使用指定设备构造异常，异常消息会自动包含设备 ID。
     *
     * @param device 触发异常的设备
     */
    protected DeviceConsistencyException(ModelDevice device) {
        super("Device: " + device.getID() + " consistency error");
        this.device = device;
    }

    /**
     * 获取触发一致性校验异常的设备对象。
     *
     * @return 与当前异常关联的设备
     */

    public ModelDevice getDevice() {
        return this.device;
    }
}
