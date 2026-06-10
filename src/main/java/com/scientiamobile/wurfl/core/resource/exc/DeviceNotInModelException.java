package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

import java.io.Serial;

/**
 * 设备不在模型中管理异常。
 * <p>当通过设备 ID 查找 fall_back 设备时，如果 fall_back 指向的设备 ID
 * 不在当前 WURFL 模型（{@link WURFLModel}）中，则抛出该异常。
 * 这通常意味着数据加载不完整或模型被错误地构建。</p>
 */

public class DeviceNotInModelException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 无法在模型中找到其 fall_back 设备的设备
     */
    private final ModelDevice modelDevice;

    /**
     * 构造设备不在模型中异常，不携带根原因。
     *
     * @param modelDevice 无法在模型中找到其 fall_back 的设备
     */
    public DeviceNotInModelException(ModelDevice modelDevice) {
        this(modelDevice, null);
    }

    /**
     * 构造设备不在模型中异常，携带根原因。
     *
     * @param modelDevice 无法在模型中找到其 fall_back 的设备
     * @param cause       导致该异常的根原因
     */
    public DeviceNotInModelException(ModelDevice modelDevice, Throwable cause) {
        super("Device: " + modelDevice.getID() + " is not managed by model", cause);
        this.modelDevice = modelDevice;
    }

    /**
     * 获取无法在模型中找到 fall_back 的设备。
     *
     * @return 触发异常的设备
     */

    public ModelDevice getModelDevice() {
        return this.modelDevice;
    }
}
