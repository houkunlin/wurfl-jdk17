package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;

/**
 * Implementation of Eh Cache Manager.
 */

public interface EhCacheManager {
    Cache getDefaultCache();

    Cache getCache(String cacheName);

    void shutdown();
}
