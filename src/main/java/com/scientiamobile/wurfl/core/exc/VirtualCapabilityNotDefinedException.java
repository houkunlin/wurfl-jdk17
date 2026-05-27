package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

public class VirtualCapabilityNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 2L;
    private final String capabilityName;

    public VirtualCapabilityNotDefinedException(String capabilityName) {
        this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
    }

    public VirtualCapabilityNotDefinedException(String capabilityName, String message) {
        super("Virtual Capability: " + capabilityName + " - " + message);
        this.capabilityName = capabilityName;
    }

    public String getCapabilityName() {
        return this.capabilityName;
    }
}
