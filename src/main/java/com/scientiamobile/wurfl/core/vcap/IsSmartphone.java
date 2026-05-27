package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;

public class IsSmartphone extends AbstractVirtualCapabilityEvaluator {
    @Serial
    private static final long serialVersionUID = 1131972797981270952L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString(isSmartphone(device));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_smartphone";
    }
}
