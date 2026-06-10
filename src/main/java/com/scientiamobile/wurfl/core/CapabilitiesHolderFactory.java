package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;

import java.util.Set;

/**
 * Factory for creating Capabilities Holder instances.
 */

public interface CapabilitiesHolderFactory {
    CapabilitiesHolder create(ModelDevice modelDevice);

    Set<String> getModelCapabilities();
}
