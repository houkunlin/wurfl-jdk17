package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * Abstract Implementation of Capabilities Holder.
 */

abstract class CapabilitiesHolder {
    public abstract String getCapability(String capabilityName);

    public final int getCapabilityAsInt(String capabilityName) {
        String capabilityValue = this.getCapability(capabilityName);

        try {
            return Integer.parseInt(capabilityValue);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("WURFL invalid capability value: " + capabilityName + " expected an integer, received: \"" + capabilityValue + "\"");
        }
    }

    /**
     * Returns the capabilities.
     */

    public abstract Map<String, String> getCapabilities();
}

