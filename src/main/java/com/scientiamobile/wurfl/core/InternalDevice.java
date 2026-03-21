package com.scientiamobile.wurfl.core;

import java.util.Map;

public interface InternalDevice {
  String getId();
  
  String getWURFLUserAgent();
  
  String getCapability(String paramString);
  
  int getCapabilityAsInt(String paramString);
  
  boolean getCapabilityAsBool(String paramString);
  
  Map getCapabilities();
  
  boolean isActualDeviceRoot();
  
  String getDeviceRootId();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\InternalDevice.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */