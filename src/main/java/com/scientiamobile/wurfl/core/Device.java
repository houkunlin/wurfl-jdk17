package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;
import java.util.Map;

public interface Device extends InternalDevice {
  MatchType getMatchType();
  
  String getVirtualCapability(String paramString);
  
  int getVirtualCapabilityAsInt(String paramString);
  
  boolean getVirtualCapabilityAsBool(String paramString);
  
  Map getVirtualCapabilities();
  
  MarkUp getMarkUp();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\Device.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */