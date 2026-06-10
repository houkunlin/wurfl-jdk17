package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

/**
 * Implementation of Is WML Preferred.
 */

public class IsWMLPreferred implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 4429460118740181952L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        try {
            return Boolean.toString(device.getCapabilityAsInt("xhtml_support_level") <= 0);
        } catch (RuntimeException e) {
            return "false";
        }
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_wml_preferred";
    }
}
