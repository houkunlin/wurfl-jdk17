package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否为完整桌面（Full Desktop）的虚拟能力评估器。
 * <p>直接返回 WURFL 设备数据库中 {@code ux_full_desktop} 能力值，
 * 无需额外计算逻辑。该值由 WURFL 数据文件根据设备特征预定义。</p>
 */

public class IsFullDesktop implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 4434275176350438714L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return device.getCapability("ux_full_desktop");
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_full_desktop";
    }
}
