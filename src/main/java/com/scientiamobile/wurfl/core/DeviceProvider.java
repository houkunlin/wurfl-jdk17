package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

public interface DeviceProvider {
  InternalDevice getInternalDevice(String paramString);
  
  Device buildDevice(InternalDevice paramInternalDevice, String paramString1, MatchType paramMatchType, String paramString2, String paramString3);
  
  Device buildDevice(InternalDevice paramInternalDevice, WURFLRequest paramWURFLRequest, MatchType paramMatchType, String paramString1, String paramString2);
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\DeviceProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */