package com.scientiamobile.wurfl.core.resource.exc;

import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.io.Serial;

/**
 * Exception thrown when device not in model occurs.
 */

public class DeviceNotInModelException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final ModelDevice modelDevice;

    public DeviceNotInModelException(ModelDevice modelDevice) {
        this(modelDevice, null);
    }

    public DeviceNotInModelException(ModelDevice modelDevice, Throwable cause) {
        super("Device: " + modelDevice.getID() + " is not managed by model", cause);
        this.modelDevice = modelDevice;
    }

    /**
     * Returns the mode levice.
     */

    public ModelDevice getModelDevice() {
        return this.modelDevice;
    }
}
