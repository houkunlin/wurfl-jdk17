package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

public class InexistentCapabilityException extends CapabilityConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InexistentCapabilityException(ModelDevice device, String capabilityName) {
        super(device, capabilityName, "Device: " + device.getID() + " define unknown capability: " + capabilityName);
    }
}
