package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public interface DeviceProvider {
   InternalDevice getInternalDevice(String var1);

   Device buildDevice(InternalDevice var1, String var2, MatchType var3, String var4, String var5);

   Device buildDevice(InternalDevice var1, WURFLRequest var2, MatchType var3, String var4, String var5);
}
