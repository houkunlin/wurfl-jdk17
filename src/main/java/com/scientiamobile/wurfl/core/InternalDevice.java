package com.scientiamobile.wurfl.core;

import java.util.Map;

public interface InternalDevice {
   String getId();

   String getWURFLUserAgent();

   String getCapability(String var1);

   int getCapabilityAsInt(String var1);

   boolean getCapabilityAsBool(String var1);

   Map getCapabilities();

   boolean isActualDeviceRoot();

   String getDeviceRootId();
}
