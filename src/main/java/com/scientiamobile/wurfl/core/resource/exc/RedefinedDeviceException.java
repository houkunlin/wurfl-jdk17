package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when redefined device occurs.
 */

public class RedefinedDeviceException extends WURFLConsistencyException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RedefinedDeviceException(String message) {
        super(message);
    }

    public RedefinedDeviceException(ModelDevice newDevice, ModelDevice existingDevice, String redefinedField) {
        this("New device " + newDevice.getID() + " with user-agent " + newDevice.getUserAgent() + " cannot redefine already loaded device with the same WURFL ID and " + redefinedField + " " + (redefinedField.equals("fall_back") ? existingDevice.getFallBack() : existingDevice.getUserAgent()));
    }
}
