package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 基于 Apache Commons Collections 的 {@link LRUMap} 实现的缓存提供者。
 * <p>当缓存大小达到上限时，自动淘汰最近最少使用（LRU）的条目，
 * 适用于设备数量较大但内存有限的场景。</p>
 * <p>使用 {@link MapUtils#synchronizedMap} 对底层 LRUMap 进行包装，以确保线程安全。</p>
 */

public class LRUMapCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(LRUMapCacheProvider.class);
    /**
     * 底层 LRU 缓存映射，键为设备 ID 或 User-Agent，值为设备对象
     */
    private final Map<String, InternalDevice> cache;

    /**
     * 使用指定的缓存大小和淘汰行为创建 LRU 缓存提供者。
     *
     * @param cacheSize          缓存的最大条目数
     * @param scanUntilRemovable 是否在淘汰时扫描整个映射查找可移除条目；
     *                           设为 {@code true} 可避免高并发下的并发修改异常，但会降低性能
     */
    public LRUMapCacheProvider(int cacheSize, boolean scanUntilRemovable) {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize, scanUntilRemovable));
    }

    /**
     * 使用指定的缓存大小创建 LRU 缓存提供者，采用默认淘汰行为。
     *
     * @param cacheSize 缓存的最大条目数
     */
    public LRUMapCacheProvider(int cacheSize) {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>(cacheSize));
    }

    /**
     * 使用默认缓存大小创建 LRU 缓存提供者。
     */
    public LRUMapCacheProvider() {
        this.cache = MapUtils.synchronizedMap(new LRUMap<>());
    }

    @Override
    public void clear() {
        log.info("cache: size {}", this.cache.size());
        this.cache.clear();
        log.info("cache cleared: size {}", this.cache.size());
    }

    @Override
    public InternalDevice getDevice(String key) {
        return this.cache.get(key);
    }

    @Override
    public void putDevice(String key, InternalDevice device) {
        this.cache.put(key, device);
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // LRUMapCacheProvider 不支持通过设备 ID 直接查询，始终返回 null
        return null;
    }
}
