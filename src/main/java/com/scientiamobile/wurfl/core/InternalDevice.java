package com.scientiamobile.wurfl.core;

import java.util.Map;

public interface InternalDevice {
   String getId();

   String getWURFLUserAgent();

   String getCapability(String capabilityName);

   int getCapabilityAsInt(String capabilityName);

   boolean getCapabilityAsBool(String capabilityName);

   Map getCapabilities();

   boolean isActualDeviceRoot();

   String getDeviceRootId();
}
