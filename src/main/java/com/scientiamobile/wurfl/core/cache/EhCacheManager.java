package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;

public interface EhCacheManager {
   Cache getDefaultCache();

   Cache getCache(String var1);

   void shutdown();
}
