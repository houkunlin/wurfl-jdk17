package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.jsr107cache.Cache;

public class JSR107CacheProvider implements CacheProvider {
  private Cache a;
  
  public JSR107CacheProvider() {}
  
  public JSR107CacheProvider(Cache paramCache) {
    this.a = paramCache;
  }
  
  public void setCache(Cache paramCache) {
    this.a = paramCache;
  }
  
  public InternalDevice getDevice(String paramString) {
    return (InternalDevice)this.a.get(paramString);
  }
  
  public void putDevice(String paramString, InternalDevice paramInternalDevice) {
    this.a.put(paramString, paramInternalDevice);
  }
  
  public void clear() {
    this.a.clear();
  }
  
  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\JSR107CacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */