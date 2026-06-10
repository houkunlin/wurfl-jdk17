package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when device consistency occurs.
 */

public abstract class DeviceConsistencyException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ModelDevice device;

    protected DeviceConsistencyException(ModelDevice device, String message) {
        super(message);
        this.device = device;
    }

    protected DeviceConsistencyException(ModelDevice device) {
        super("Device: " + device.getID() + " consistency error");
        this.device = device;
    }

    /**
     * Returns the device.
     */

    public ModelDevice getDevice() {
        return this.device;
    }
}
