package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

/**
 * Implementation of Is Smartphone.
 */

public class IsSmartphone extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 1131972797981270952L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isSmartphone(device));
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_smartphone";
    }
}
