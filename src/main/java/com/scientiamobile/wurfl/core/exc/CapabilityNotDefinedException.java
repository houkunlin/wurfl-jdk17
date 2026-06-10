package com.scientiamobile.wurfl.core.exc;

import java.io.Serial;

/**
 * Exception thrown when capability not defined occurs.
 */

public class CapabilityNotDefinedException extends WURFLRuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String capabilityName;

    public CapabilityNotDefinedException(String capabilityName) {
        this(capabilityName, (new StringBuilder("Capability: ")).append(capabilityName).append(" is not defined in WURFL").toString());
    }

    public CapabilityNotDefinedException(String capabilityName, String message) {
        super("Capability: " + capabilityName + " - " + message);
        this.capabilityName = capabilityName;
    }

    /**
     * Returns the capabilit yame.
     */

    public String getCapabilityName() {
        return this.capabilityName;
    }
}
