package com.scientiamobile.wurfl.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.scientiamobile.wurfl.core.InternalDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Provides Caffeine Cache functionality.
 */

public class CaffeineCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(CaffeineCacheProvider.class);

    private final Cache<String, InternalDevice> deviceIdCache;
    private final Cache<String, String> userAgentToDeviceIdCache;

    public CaffeineCacheProvider() {
        this(2000, 10000, -1, TimeUnit.MINUTES);
    }

    public CaffeineCacheProvider(int deviceCacheSize, int userAgentCacheSize, int expireAfterMinutes) {
        this(deviceCacheSize, userAgentCacheSize, expireAfterMinutes, TimeUnit.MINUTES);
    }

    public CaffeineCacheProvider(int deviceCacheSize, int userAgentCacheSize, int expireAfterMinutes, TimeUnit timeUnit) {
        Caffeine<Object, Object> deviceBuilder = Caffeine.newBuilder()
                .maximumSize(deviceCacheSize)
                .recordStats();

        Caffeine<Object, Object> uaBuilder = Caffeine.newBuilder()
                .maximumSize(userAgentCacheSize)
                .recordStats();

        if (expireAfterMinutes > 0) {
            deviceBuilder.expireAfterWrite(expireAfterMinutes, timeUnit);
            uaBuilder.expireAfterWrite(expireAfterMinutes, timeUnit);
        }

        this.deviceIdCache = deviceBuilder.build();
        this.userAgentToDeviceIdCache = uaBuilder.build();

        log.info("CaffeineCacheProvider initialized: deviceCache={}, uaCache={}, expireAfterWrite={}min",
                deviceCacheSize, userAgentCacheSize, expireAfterMinutes > 0 ? expireAfterMinutes : "none");
    }

    @Override
/**
 * Returns the device.
 */

    public InternalDevice getDevice(String userAgent) {
        String deviceId = this.userAgentToDeviceIdCache.getIfPresent(userAgent);
        if (deviceId == null) {
            return null;
        }
        return this.deviceIdCache.getIfPresent(deviceId);
    }

    @Override
/**
 * Returns the interna levic ero mevic ed.
 */

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return this.deviceIdCache.getIfPresent(deviceId);
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String userAgent, InternalDevice device) {
        if (device == null) return;
        String deviceId = device.getId();
        try {
            this.deviceIdCache.put(deviceId, device);
            this.userAgentToDeviceIdCache.put(userAgent, deviceId);
        } catch (RuntimeException e) {
            log.error("Could not cache " + userAgent, e);
        }
    }

    @Override
/**
 * Clears all cached data.
 */

    public void clear() {
        log.info("Clearing Caffeine cache...");
        this.deviceIdCache.invalidateAll();
        this.userAgentToDeviceIdCache.invalidateAll();
        log.info("Caffeine cache cleared");
    }

    /**
     * Estimate devic each eize.
     */

    public long estimatedDeviceCacheSize() {
        return this.deviceIdCache.estimatedSize();
    }

    public long estimatedUaCacheSize() {
        return this.userAgentToDeviceIdCache.estimatedSize();
    }
}
