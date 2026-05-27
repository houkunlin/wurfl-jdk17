package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

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

    public String getDeviceId() {
        return this.deviceId;
    }
}
