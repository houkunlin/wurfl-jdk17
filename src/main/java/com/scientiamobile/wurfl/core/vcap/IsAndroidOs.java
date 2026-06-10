package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is Android Os.
 */

public class IsAndroidOs implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6129742649965950877L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("Android".equals(device.getCapability("device_os")));
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_android";
    }
}
