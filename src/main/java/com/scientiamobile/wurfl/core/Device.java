package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import java.util.Map;

public interface Device extends InternalDevice {
   MatchType getMatchType();

   String getVirtualCapability(String var1);

   int getVirtualCapabilityAsInt(String var1);

   boolean getVirtualCapabilityAsBool(String var1);

   Map getVirtualCapabilities();

   MarkUp getMarkUp();
}
