package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * 当请求的设备在 WURFL 数据中未定义时抛出的异常。
 * <p>通常在以下场景中触发：</p>
 * <ul>
 *   <li>根据设备 ID 查询设备信息但未找到匹配记录</li>
 *   <li>设备数据中引用了不存在的父设备</li>
 * </ul>
 */

public class DeviceNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 未定义的设备 ID
     */
    private final String deviceId;

    /**
     * 使用指定的设备 ID 和错误信息创建异常实例。
     *
     * @param deviceId 未定义的设备 ID
     * @param message  异常描述信息
     */
    public DeviceNotDefinedException(String deviceId, String message) {
        super(message);
        this.deviceId = deviceId;
    }

    /**
     * 使用指定的设备 ID 创建异常实例，错误信息自动生成。
     *
     * @param deviceId 未定义的设备 ID
     */
    public DeviceNotDefinedException(String deviceId) {
        this(deviceId, (new StringBuilder("Device: ")).append(deviceId).append(" is not defined in WURFL").toString());
    }

    /**
     * 获取未定义的设备 ID。
     *
     * @return 设备 ID
     */
    public String getDeviceId() {
        return this.deviceId;
    }
}
