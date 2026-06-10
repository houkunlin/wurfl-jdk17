package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Provides Double LRU Map Cache functionality.
 */

public class DoubleLRUMapCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(DoubleLRUMapCacheProvider.class);
    private final Map<String, String> deviceIdByUserAgent;
    private final Map<String, InternalDevice> deviceById;

    public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize, boolean scanUntilRemovable) {
        this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize, scanUntilRemovable));
        this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize, scanUntilRemovable));
    }

    public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize) {
        this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize));
        this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize));
    }

    public DoubleLRUMapCacheProvider() {
        this(10000, 2000);
    }

    @Override
/**
 * Clears all cached data.
 */

    public void clear() {
        log.info("UA cache: size {}", this.deviceIdByUserAgent.size());
        this.deviceIdByUserAgent.clear();
        log.info("UA cache cleared: size {}", this.deviceIdByUserAgent.size());
        log.info("device cache: size {}", this.deviceById.size());
        this.deviceById.clear();
        log.info("device cache cleared: size {}", this.deviceById.size());
    }

    @Override
/**
 * Returns the device.
 */

    public InternalDevice getDevice(String userAgent) {
        String deviceId = this.deviceIdByUserAgent.get(userAgent);
        if (deviceId == null) {
            return null;
        }
        InternalDevice device = this.deviceById.get(deviceId);
        return device;
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String userAgent, InternalDevice device) {
        try {
            this.deviceById.put(device.getId(), device);
            this.deviceIdByUserAgent.put(userAgent, device.getId());
        } catch (RuntimeException e) {
            log.error("Could not cache {}", userAgent, e);
        }
    }

    /**
     * Returns the interna levic ero mevic ed.
     */

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return this.deviceById.get(deviceId);
    }
}
