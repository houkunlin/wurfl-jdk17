package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.utils.CollectionFactory;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 基于 {@link java.util.concurrent.ConcurrentHashMap} 的缓存提供者实现。
 * <p>适用于设备数量可预知且内存充足的场景，提供高并发读写能力。
 * 可通过构造参数调整初始容量、负载因子和并发写线程数，以优化性能。</p>
 * <p>注意：此实现不提供缓存淘汰策略，缓存数据会一直保留直至被显式清空，
 * 因此需要确保初始容量能容纳全部设备数据，避免频繁扩容。</p>
 */

public class HashMapCacheProvider implements CacheProvider {
    private static final Logger logger = LoggerFactory.getLogger(HashMapCacheProvider.class);
    /**
     * 底层缓存存储，键为设备 ID 或 User-Agent，值为设备对象
     */
    private final Map<String, InternalDevice> cache;
    /**
     * 缓存的初始容量
     */
    private int initialCapacity;
    /** 哈希表的负载因子 */
    private float loadFactor;
    /** 并发写入的预估线程数，用于优化 ConcurrentHashMap 的分段锁 */
    private int concurrentWrites;

    /**
     * 使用默认初始容量（60000）创建缓存提供者。
     */
    public HashMapCacheProvider() {
        this(60000);
    }

    /**
     * 使用指定的初始容量和默认负载因子（0.75）创建缓存提供者。
     *
     * @param initialCapacity 缓存的初始容量
     */
    public HashMapCacheProvider(int initialCapacity) {
        this(initialCapacity, 0.75F);
    }

    /**
     * 使用指定的初始容量、负载因子和默认并发写线程数（16）创建缓存提供者。
     *
     * @param initialCapacity 缓存的初始容量
     * @param loadFactor      哈希表的负载因子
     */
    public HashMapCacheProvider(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 16);
    }

    /**
     * 使用完整参数创建缓存提供者。
     *
     * @param initialCapacity 缓存的初始容量
     * @param loadFactor      哈希表的负载因子
     * @param concurrentWrites 并发写入的预估线程数
     */
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
     * 获取缓存的初始容量值。
     *
     * @return 初始容量
     */
    public int getInitialCapacity() {
        return this.initialCapacity;
    }

    /**
     * 获取哈希表的负载因子。
     *
     * @return 负载因子
     */
    public float getLoadFactor() {
        return this.loadFactor;
    }

    /**
     * 获取并发写入的预估线程数。
     *
     * @return 并发写入线程数
     */
    public int getConcurrentWrites() {
        return this.concurrentWrites;
    }

    @Override
    public void clear() {
        logger.info("Cache size: {}", this.cache.size());
        this.cache.clear();
        logger.info("Cache erased");
    }

    @Override
    public InternalDevice getDevice(String key) {
        Validate.notNull(key, "The key is null");
        return this.cache.get(key);
    }

    @Override
    public void putDevice(String key, InternalDevice device) {
        Validate.notNull(key, "The key is null");
        this.cache.put(key, device);
    }

    @Override
    public String toString() {
        return (new ToStringBuilder(this))
                .append("initialCapacity", this.initialCapacity)
                .append("loadFactor", this.loadFactor)
                .append("concurrentWrites", this.concurrentWrites)
                .toString();
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // HashMapCacheProvider 不支持通过设备 ID 直接查询，始终返回 null
        return null;
    }
}
