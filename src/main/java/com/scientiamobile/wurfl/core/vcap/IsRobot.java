package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

public class IsRobot extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 290928780375573277L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isRobot(request));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_robot";
    }
}
