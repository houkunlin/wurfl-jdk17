package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Provides LRU Map Cache functionality.
 */

public class LRUMapCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(LRUMapCacheProvider.class);
    private final Map<String, InternalDevice> cache;

    public LRUMapCacheProvider(int cacheSize, boolean scanUntilRemovable) {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize, scanUntilRemovable));
    }

    public LRUMapCacheProvider(int cacheSize) {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize));
    }

    public LRUMapCacheProvider() {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>());
    }

    @Override
/**
 * Clears all cached data.
 */

    public void clear() {
        log.info("cache: size {}", this.cache.size());
        this.cache.clear();
        log.info("cache cleared: size {}", this.cache.size());
    }

    @Override
/**
 * Returns the device.
 */

    public InternalDevice getDevice(String key) {
        return this.cache.get(key);
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String key, InternalDevice device) {
        this.cache.put(key, device);
    }

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
