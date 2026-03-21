package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CacheProvider {
  public static final Logger logger = LoggerFactory.getLogger(CacheProvider.class);
  
  InternalDevice getDevice(String paramString);
  
  InternalDevice getInternalDeviceFromDeviceId(String paramString);
  
  void putDevice(String paramString, InternalDevice paramInternalDevice);
  
  void clear();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\CacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */