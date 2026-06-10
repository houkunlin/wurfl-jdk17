package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否为大屏设备的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中的 {@code resolution_width} 和
 * {@code resolution_height} 能力值，当两者均不低于 480 像素时
 * 认为设备具有大屏幕。</p>
 */

public class IsLargescreen implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -7518577459129144687L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        try {
            return Boolean.toString(device.getCapabilityAsInt("resolution_width") >= 480 && device.getCapabilityAsInt("resolution_height") >= 480);
        } catch (RuntimeException e) {
            return "false";
        }
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_largescreen";
    }
}
