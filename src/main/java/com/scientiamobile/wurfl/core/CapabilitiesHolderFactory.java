package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.ModelDevice;
import java.util.Set;

public interface CapabilitiesHolderFactory {
   a create(ModelDevice var1);

   Set getModelCapabilities();
}
