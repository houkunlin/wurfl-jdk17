package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否具备触摸屏的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code pointing_method} 能力值
 * 是否为 {@code "touchscreen"}，或者 User-Agent 中同时包含
 * "Trident" 和 "Touch" 关键字（IE 触屏设备）来判断。</p>
 */

public class IsTouchscreen implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 3516513258503645772L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();
        return Boolean.toString("touchscreen".equals(device.getCapability("pointing_method")) || StringMatchUtils.containsAllOf(userAgent, "Trident", "Touch"));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_touchscreen";
    }
}
