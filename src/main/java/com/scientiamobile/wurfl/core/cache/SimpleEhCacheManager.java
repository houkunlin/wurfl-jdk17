package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleEhCacheManager implements EhCacheManager {
   public static final String DEFAULT_CACHE_NAME = "com.scientiamobile.wurfl.core.InternalDevice";
   private final Logger a = LoggerFactory.getLogger(this.getClass());
   private final CacheManager b = new CacheManager();

   public Cache getDefaultCache() {
      return this.getCache("com.scientiamobile.wurfl.core.InternalDevice");
   }

   public Cache getCache(String var1) {
      return this.b.getCache(var1);
   }

   public void shutdown() {
      this.a.info("shutting down cache manager");
      this.b.shutdown();
   }
}
