package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when capability consistency occurs.
 */

public abstract class CapabilityConsistencyException extends DeviceConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
   private final String capabilityName;

    protected CapabilityConsistencyException(ModelDevice device, String capabilityName) {
        super(device, "Device: " + device.getID() + " Capability: " + capabilityName + " consistency error");
        this.capabilityName = capabilityName;
    }

    protected CapabilityConsistencyException(ModelDevice device, String capabilityName, String message) {
        super(device, message);
        this.capabilityName = capabilityName;
    }

    /**
     * Returns the capability.
     */

    public String getCapability() {
        return this.capabilityName;
    }
}
