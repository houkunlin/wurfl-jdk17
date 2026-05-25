package com.scientiamobile.wurfl.core;

import java.util.Map;

interface CapabilitiesProvider {
   Map getAllCapabilities();

   String getCapability(Map capabilities, String capabilityName);
}

