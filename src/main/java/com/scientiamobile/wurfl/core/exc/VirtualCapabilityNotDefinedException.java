package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Exception thrown when virtual capability not defined occurs.
 */

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

    /**
     * Returns the capabilit yame.
     */

    public String getCapabilityName() {
        return this.capabilityName;
    }
}
