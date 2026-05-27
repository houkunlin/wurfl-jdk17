package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

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
