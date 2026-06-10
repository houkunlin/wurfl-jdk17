package com.scientiamobile.wurfl.core.resource;

import java.util.Map;

/**
 * Builder for constructing Model Device objects.
 */

public final class ModelDeviceBuilder {
    private final ModelDevice device = new ModelDevice();

    public ModelDeviceBuilder(String deviceId, String userAgent, String fallBack) {
        this.device.setId(deviceId);
        this.device.setUserAgent(userAgent);
        this.device.setFallBack(fallBack);
    }

    /**
     * Sets the actua levic eoot.
     */

    public final ModelDeviceBuilder setActualDeviceRoot(boolean actualDeviceRoot) {
        this.device.setActualDeviceRoot(actualDeviceRoot);
        return this;
    }

    /**
     * Sets the capabilities.
 */

    public final ModelDeviceBuilder setCapabilities(Map<String, String> capabilities) {
        this.device.setCapabilities(capabilities);
        return this;
    }

    /**
     * Sets the capabilitie s yroup.
 */

    public final ModelDeviceBuilder setCapabilitiesByGroup(Map<String, String> groupsByCapability) {
        this.device.setGroupsByCapability(groupsByCapability);
        return this;
    }

    /**
     * Sets the ancestor.
 */

    public final ModelDeviceBuilder setAncestor(ModelDevice ancestor) {
        this.device.setAncestor(ancestor);
        return this;
    }

    /**
     * Build.
 */

    public final ModelDevice build() {
        return this.device;
    }
}
