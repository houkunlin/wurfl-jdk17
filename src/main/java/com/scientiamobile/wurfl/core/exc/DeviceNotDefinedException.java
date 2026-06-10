package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Exception thrown when device not defined occurs.
 */

public class DeviceNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String deviceId;

    public DeviceNotDefinedException(String deviceId, String message) {
        super(message);
        this.deviceId = deviceId;
    }

    public DeviceNotDefinedException(String deviceId) {
        this(deviceId, (new StringBuilder("Device: ")).append(deviceId).append(" is not defined in WURFL").toString());
    }

    /**
     * Returns the devic ed.
     */

    public String getDeviceId() {
        return this.deviceId;
    }
}
