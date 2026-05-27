package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serializable;

public class IsTouchscreen implements VirtualCapabilityEvaluator, Serializable {
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
