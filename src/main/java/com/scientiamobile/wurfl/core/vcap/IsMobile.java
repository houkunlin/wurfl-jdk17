package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is Mobile.
 */

public class IsMobile implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -3052242731391430427L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return device.getCapability("is_wireless_device");
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_mobile";
    }
}
