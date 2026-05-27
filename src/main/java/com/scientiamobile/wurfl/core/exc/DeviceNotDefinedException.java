package com.scientiamobile.wurfl.core.exc;

public class DeviceNotDefinedException extends WURFLRuntimeException {
    private static final long serialVersionUID = 1L;
    private String deviceId;

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
