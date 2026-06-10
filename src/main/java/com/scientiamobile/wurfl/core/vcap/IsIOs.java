package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备操作系统是否为 iOS 的虚拟能力评估器。
 * <p>检查 WURFL 设备数据库中的 {@code device_os} 能力值是否等于 "iOS" 或 "iPhoneOS"，
 * 以识别 iPhone、iPad 和 iPod Touch 等 Apple 移动设备。</p>
 */

public class IsIOs implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 5384820129703085119L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("iOS".equals(device.getCapability("device_os")) || "iPhoneOS".equals(device.getCapability("device_os")));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_ios";
    }
}
