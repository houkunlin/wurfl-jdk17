package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.ehcache.Cache;

public class EhCacheProvider implements CacheProvider {
  private Cache a;

  public EhCacheProvider() {}

  public EhCacheProvider(Cache paramCache) {
    this.a = paramCache;
  }

  public EhCacheProvider(EhCacheManager paramEhCacheManager) {
    this(paramEhCacheManager.getDefaultCache());
  }

  public Cache getCache() {
    return this.a;
  }

  public void setCache(Cache paramCache) {
    this.a = paramCache;
  }

  public void clear() {
    // logger.info("Cache size: " + this.a.getSize());
    this.a.removeAll();
    // logger.info("Cache erased. size: " + this.a.getSize());
  }

  public InternalDevice getDevice(String paramString) {
    return this.a.get(paramString);
  }

  public void putDevice(String paramString, InternalDevice paramInternalDevice) {
    this.a.put(paramString, paramInternalDevice);
  }

  public InternalDevice getInternalDeviceFromDeviceId(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\EhCacheProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
