package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is Largescreen.
 */

public class IsLargescreen implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -7518577459129144687L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        try {
            return Boolean.toString(device.getCapabilityAsInt("resolution_width") >= 480 && device.getCapabilityAsInt("resolution_height") >= 480);
        } catch (RuntimeException e) {
            return "false";
        }
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_largescreen";
    }
}
