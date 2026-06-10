package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

/**
 * Implementation of Is Robot.
 */

public class IsRobot extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 290928780375573277L;

    @Override
/**
 * Eval.
 */

    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isRobot(request));
    }

    @Override
/**
 * Returns the handle dirtua lapabilit yame.
 */

    public String getHandledVirtualCapabilityName() {
        return "is_robot";
    }
}
