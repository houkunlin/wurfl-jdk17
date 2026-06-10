package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否为移动设备的虚拟能力评估器。
 * <p>直接返回 WURFL 设备数据库中 {@code is_wireless_device} 能力值。
 * 该值由 WURFL 数据文件根据设备特征预定义，覆盖手机、平板等无线设备。</p>
 */

public class IsMobile implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -3052242731391430427L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return device.getCapability("is_wireless_device");
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_mobile";
    }
}
