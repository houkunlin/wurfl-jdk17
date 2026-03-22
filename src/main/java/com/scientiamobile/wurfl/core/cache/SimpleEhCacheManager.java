package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleEhCacheManager implements EhCacheManager {
  public static final String DEFAULT_CACHE_NAME = "com.scientiamobile.wurfl.core.InternalDevice";

  private final Logger a = LoggerFactory.getLogger(getClass());

  private final CacheManager b = new CacheManager();

  public Cache<String, InternalDevice> getDefaultCache() {
    return getCache("com.scientiamobile.wurfl.core.InternalDevice");
  }

  public Cache<String, InternalDevice> getCache(String paramString) {
    return this.b.getCache(paramString);
  }

  public void shutdown() {
    this.a.info("shutting down cache manager");
    this.b.shutdown();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\SimpleEhCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
