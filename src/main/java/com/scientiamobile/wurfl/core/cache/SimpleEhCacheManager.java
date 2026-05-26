package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleEhCacheManager implements EhCacheManager {
   public static final String DEFAULT_CACHE_NAME = "com.scientiamobile.wurfl.core.InternalDevice";
   private final Logger logger = LoggerFactory.getLogger(this.getClass());
   private final CacheManager cacheManager = new CacheManager();

   public Cache getDefaultCache() {
      return this.getCache("com.scientiamobile.wurfl.core.InternalDevice");
   }

   public Cache getCache(String cacheName) {
      return this.cacheManager.getCache(cacheName);
   }

   public void shutdown() {
      this.logger.info("shutting down cache manager");
      this.cacheManager.shutdown();
   }
}
