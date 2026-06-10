package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is I Os.
 */

public class IsIOs implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 5384820129703085119L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("iOS".equals(device.getCapability("device_os")) || "iPhoneOS".equals(device.getCapability("device_os")));
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_ios";
    }
}
