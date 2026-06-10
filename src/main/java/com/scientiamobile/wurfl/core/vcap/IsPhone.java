package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * 判断设备是否为手机（Phone）的虚拟能力评估器。
 * <p>通过检查 WURFL 设备数据库中 {@code can_assign_phone_number} 和
 * {@code is_tablet} 能力值来判断：如果设备可以分配电话号码且不是平板，
 * 则认为它是手机。</p>
 */

public class IsPhone implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -8329753363071363291L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(device.getCapabilityAsBool("can_assign_phone_number") && !device.getCapabilityAsBool("is_tablet"));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_phone";
    }
}
