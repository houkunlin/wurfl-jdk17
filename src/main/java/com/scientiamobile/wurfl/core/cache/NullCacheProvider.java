package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

public class NullCacheProvider implements CacheProvider {
  public void clear() {}
  
  public void putDevice(String paramString, InternalDevice paramInternalDevice) {}
  
  public InternalDevice getDevice(String paramString) {
    return null;
  }
  
  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\NullCacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */