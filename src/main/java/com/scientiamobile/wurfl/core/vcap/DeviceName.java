package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.io.Serial;
import java.io.Serializable;

public class DeviceName implements VirtualCapabilityEvaluator, Serializable {
    @Serial
    private static final long serialVersionUID = 6339082037173595673L;

    @Override
    public String eval(Device device, WURFLRequest request) {
        StringBuilder builder = new StringBuilder(device.getCapability("brand_name"));
        String namePart;
        namePart = device.getCapability("marketing_name");
        if (namePart.isEmpty()) {
            namePart = device.getCapability("model_name");
        }

        builder.append(" ").append(namePart);
        return builder.toString().trim();
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "device_name";
    }
}
