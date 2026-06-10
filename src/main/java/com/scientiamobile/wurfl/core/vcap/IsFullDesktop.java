package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is Full Desktop.
 */

public class IsFullDesktop implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 4434275176350438714L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return device.getCapability("ux_full_desktop");
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_full_desktop";
    }
}
