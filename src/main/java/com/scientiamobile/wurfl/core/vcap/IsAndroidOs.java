package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备操作系统是否为 Android 的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code device_os} 能力值是否等于 "Android"
 * 来判断设备是否运行 Android 操作系统。</p>
 */

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6129742649965950877L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("Android".equals(device.getCapability("device_os")));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_android";
    }
}
