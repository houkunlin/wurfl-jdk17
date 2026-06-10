package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Complete Device Name.
 */

public class CompleteDeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -65030764132400949L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        StringBuilder builder = new StringBuilder(device.getCapability("brand_name"));
        String namePart;
        namePart = device.getCapability("model_name");
        if (!namePart.isEmpty()) {
            builder.append(" ").append(namePart);
        }

        namePart = device.getCapability("marketing_name");
        if (!namePart.isEmpty()) {
            builder.append(" (").append(namePart).append(")");
        }

        return builder.toString();
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "complete_device_name";
    }
}
