package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is HTML Preferred.
 */

public class IsHTMLPreferred implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = -4750338441403942375L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(device.getCapability("preferred_markup").startsWith("html_web"));
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_html_preferred";
    }
}
