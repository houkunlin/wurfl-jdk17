package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class RedefinedDeviceException extends WURFLConsistencyException {
    private static final long serialVersionUID = -7571571247607816104L;

    public RedefinedDeviceException(String message) {
        super(message);
    }

    public RedefinedDeviceException(ModelDevice newDevice, ModelDevice existingDevice, String redefinedField) {
        this("New device " + newDevice.getID() + " with user-agent " + newDevice.getUserAgent() + " cannot redefine already loaded device with the same WURFL ID and " + redefinedField + " " + (redefinedField.equals("fall_back") ? existingDevice.getFallBack() : existingDevice.getUserAgent()));
    }
}
