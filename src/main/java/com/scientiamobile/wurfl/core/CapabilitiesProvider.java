package com.scientiamobile.wurfl.core;

import java.util.Map;

interface CapabilitiesProvider {
   Map<String, String> getAllCapabilities();

   String getCapability(Map<String, String> capabilities, String capabilityName);
}

