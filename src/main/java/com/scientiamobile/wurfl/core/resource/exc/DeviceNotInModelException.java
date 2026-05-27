package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;

public class DeviceNotInModelException extends WURFLRuntimeException {
    private static final long serialVersionUID = 10L;
    private final ModelDevice modelDevice;

    public DeviceNotInModelException(ModelDevice modelDevice) {
        this(modelDevice, null);
    }

    public DeviceNotInModelException(ModelDevice modelDevice, Throwable cause) {
        super("Device: " + modelDevice.getID() + " is not managed by model", cause);
        this.modelDevice = modelDevice;
    }

    public ModelDevice getModelDevice() {
        return this.modelDevice;
    }
}
