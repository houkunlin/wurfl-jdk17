package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serializable;

public class IsWindowsPhone implements VirtualCapabilityEvaluator, Serializable {
    private static final long serialVersionUID = 7780353517392752318L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        return Boolean.toString("Windows Phone OS".equals(device.getCapability("device_os")));
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_windows_phone";
    }
}
