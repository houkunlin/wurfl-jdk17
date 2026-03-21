package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.LRUMap;

public class DoubleLRUMapCacheProvider implements CacheProvider {
  private Map a;
  
  private Map b;
  
  public DoubleLRUMapCacheProvider(int paramInt1, int paramInt2, boolean paramBoolean) {
    this.a = MapUtils.synchronizedMap((Map)new LRUMap(paramInt1, paramBoolean));
    this.b = MapUtils.synchronizedMap((Map)new LRUMap(paramInt2, paramBoolean));
  }
  
  public DoubleLRUMapCacheProvider(int paramInt1, int paramInt2) {
    this.a = MapUtils.synchronizedMap((Map)new LRUMap(paramInt1));
    this.b = MapUtils.synchronizedMap((Map)new LRUMap(paramInt2));
  }
  
  public DoubleLRUMapCacheProvider() {
    this(10000, 2000);
  }
  
  public void clear() {
    logger.info("UA cache: size " + this.a.size());
    this.a.clear();
    logger.info("UA cache cleared: size " + this.a.size());
    logger.info("device cache: size " + this.b.size());
    this.b.clear();
    logger.info("device cache cleared: size " + this.b.size());
  }
  
  public InternalDevice getDevice(String paramString) {
    InternalDevice internalDevice;
    return ((paramString = (String)this.a.get(paramString)) != null && (internalDevice = (InternalDevice)this.b.get(paramString)) != null) ? internalDevice : null;
  }
  
  public void putDevice(String paramString, InternalDevice paramInternalDevice) {
    try {
      this.b.put(paramInternalDevice.getId(), paramInternalDevice);
      this.a.put(paramString, paramInternalDevice.getId());
      return;
    } catch (Exception exception) {
      logger.error("Could not cache " + paramString);
      return;
    } 
  }
  
  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return (InternalDevice)this.b.get(paramString);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\DoubleLRUMapCacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */