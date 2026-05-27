package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class Ehcache3CacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(Ehcache3CacheProvider.class);
    private static final String DEVICE_CACHE = "wurfl-device-cache";
    private static final String UA_CACHE = "wurfl-ua-cache";
    private static final long HEAP_SIZE_MB = 20;

    private final CacheManager cacheManager;
    private final Cache<String, InternalDevice> deviceIdCache;
    private final Cache<String, String> userAgentToIdCache;
    private final AtomicBoolean closed = new AtomicBoolean(false);

    public Ehcache3CacheProvider() {
        this(HEAP_SIZE_MB);
    }

    public Ehcache3CacheProvider(long heapSizeMb) {
        this.cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

        this.deviceIdCache = this.cacheManager.createCache(DEVICE_CACHE,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, InternalDevice.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(heapSizeMb, MemoryUnit.MB))
                        .build());

        this.userAgentToIdCache = this.cacheManager.createCache(UA_CACHE,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder().heap(heapSizeMb, MemoryUnit.MB))
                        .build());

        log.info("Ehcache3CacheProvider initialized with {} MB heap", heapSizeMb);
    }

    @Override
    public InternalDevice getDevice(String userAgent) {
        String deviceId = this.userAgentToIdCache.get(userAgent);
        if (deviceId == null) {
            return null;
        }
        return this.deviceIdCache.get(deviceId);
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return this.deviceIdCache.get(deviceId);
    }

    @Override
    public void putDevice(String userAgent, InternalDevice device) {
        if (device == null) return;
        String deviceId = device.getId();
        try {
            this.deviceIdCache.put(deviceId, device);
            this.userAgentToIdCache.put(userAgent, deviceId);
        } catch (RuntimeException e) {
            log.error("Could not cache {}: {}", userAgent, e.getMessage());
        }
    }

    @Override
    public void clear() {
        log.info("Clearing Ehcache3 cache...");
        this.deviceIdCache.clear();
        this.userAgentToIdCache.clear();
        log.info("Ehcache3 cache cleared");
    }

    public void close() {
        if (this.closed.compareAndSet(false, true)) {
            this.cacheManager.close();
            log.info("Ehcache3CacheProvider closed");
        }
    }
}
