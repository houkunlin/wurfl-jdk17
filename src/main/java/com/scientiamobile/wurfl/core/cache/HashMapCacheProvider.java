package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Provides Hash Map Cache functionality.
 */

public class HashMapCacheProvider implements CacheProvider {
    private static final Logger logger = LoggerFactory.getLogger(HashMapCacheProvider.class);
    private final Map<String, InternalDevice> cache;
    private int initialCapacity;
    private float loadFactor;
    private int concurrentWrites;

    public HashMapCacheProvider() {
        this(60000);
    }

    public HashMapCacheProvider(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    public HashMapCacheProvider(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 16);
    }

    public HashMapCacheProvider(int initialCapacity, float loadFactor, int concurrentWrites) {
        this.initialCapacity = 6000;
        this.loadFactor = 0.75F;
        this.concurrentWrites = 16;

        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.concurrentWrites = concurrentWrites;
        this.cache = CollectionFactory.createConcurrentHashMap(initialCapacity, loadFactor, concurrentWrites);
        if (logger.isInfoEnabled()) {
            logger.info("Created HashMapCacheProvider with initial capacity: {} load factor: {} concurrent writes: {}", initialCapacity, loadFactor, concurrentWrites);
        }

    }

    /**
     * Returns the initia lapacity.
     */

    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    public float getLoadFactor() {
        return this.loadFactor;
    }

    /**
     * Returns the concurren trites.
 */

    public int getConcurrentWrites() {
        return this.concurrentWrites;
    }

    @Override
/**
 * Clears all cached data.
 */

    public void clear() {
        logger.info("Cache size: {}", this.cache.size());
        this.cache.clear();
        logger.info("Cache erased");
    }

    @Override
/**
 * Returns the device.
 */

    public InternalDevice getDevice(String key) {
        Validate.notNull(key, "The key is null");
        return this.cache.get(key);
    }

    @Override
/**
 * Pu tevice.
 */

    public void putDevice(String key, InternalDevice device) {
        Validate.notNull(key, "The key is null");
        this.cache.put(key, device);
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        return (new ToStringBuilder(this))
                .append("initialCapacity", this.initialCapacity)
                .append("loadFactor", this.loadFactor)
                .append("concurrentWrites", this.concurrentWrites)
                .toString();
    }

    /**
     * Returns the interna levic ero mevic ed.
 */

    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
