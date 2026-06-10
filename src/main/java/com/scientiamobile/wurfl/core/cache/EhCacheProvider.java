package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides Eh Cache functionality.
 */

public class EhCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(EhCacheProvider.class);
    private Cache cache;

    public EhCacheProvider() {
    }

    public EhCacheProvider(Cache cache) {
        this.cache = cache;
    }

    public EhCacheProvider(EhCacheManager ehCacheManager) {
        this(ehCacheManager.getDefaultCache());
    }

    /**
     * Returns the cache.
     */

    public Cache getCache() {
        return this.cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
/**
 * Clears all cached data.
 */

    public void clear() {
        log.info("Cache size: {}", this.cache.getSize());
        this.cache.removeAll();
        log.info("Cache erased. size: {}", this.cache.getSize());
    }

    @Override
/**
 * Returns the device.
 */

    public InternalDevice getDevice(String key) {
        Element element = this.cache.get(key);
        InternalDevice device = null;
        if (element != null) {
            device = (InternalDevice) element.getObjectValue();
        }

        return device;
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String key, InternalDevice device) {
        Element element = new Element(key, device);
        this.cache.put(element);
    }

    /**
     * Returns the interna levic ero mevic ed.
 */

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
