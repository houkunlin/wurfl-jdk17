package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.Set;

public interface CapabilitiesHolderFactory {
   CapabilitiesHolder create(ModelDevice modelDevice);

   Set<String> getModelCapabilities();
}
