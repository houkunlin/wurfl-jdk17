package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否为 Windows Phone 的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code device_os} 能力值
 * 是否等于 "Windows Phone OS" 来识别 Windows Phone 设备。</p>
 */

public class IsWindowsPhone implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 7780353517392752318L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("Windows Phone OS".equals(device.getCapability("device_os")));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_windows_phone";
    }
}
