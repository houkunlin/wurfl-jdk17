package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.LRUMap;

public class LRUMapCacheProvider implements CacheProvider {
  private Map a;
  
  public LRUMapCacheProvider(int paramInt, boolean paramBoolean) {
    this.a = MapUtils.synchronizedMap((Map)new LRUMap(paramInt, paramBoolean));
  }
  
  public LRUMapCacheProvider(int paramInt) {
    this.a = MapUtils.synchronizedMap((Map)new LRUMap(paramInt));
  }
  
  public LRUMapCacheProvider() {
    this.a = MapUtils.synchronizedMap((Map)new LRUMap());
  }
  
  public void clear() {
    logger.info("cache: size " + this.a.size());
    this.a.clear();
    logger.info("cache cleared: size " + this.a.size());
  }
  
  public InternalDevice getDevice(String paramString) {
    return (InternalDevice)this.a.get(paramString);
  }
  
  public void putDevice(String paramString, InternalDevice paramInternalDevice) {
    this.a.put(paramString, paramInternalDevice);
  }
  
  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\LRUMapCacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */