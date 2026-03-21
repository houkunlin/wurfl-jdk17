package com.scientiamobile.wurfl.core.cache;


import org.ehcache.Cache;

public interface EhCacheManager {
  Cache getDefaultCache();

  Cache getCache(String paramString);

  void shutdown();
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\cache\EhCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
