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

/**
 * 基于 Ehcache 3.x 的缓存提供者实现。
 * <p>使用 Ehcache 3.x 的缓存管理器创建两个独立的缓存，分别存储设备数据
 * （设备 ID → 设备对象）和 User-Agent 映射关系（User-Agent → 设备 ID）。
 * 支持基于堆内存大小的淘汰策略，并实现了 {@link AutoCloseable} 接口以释放底层资源。</p>
 * <p>适用于需要明确控制堆内存使用量的生产环境。</p>
 */

public class Ehcache3CacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(Ehcache3CacheProvider.class);
    /**
     * 设备缓存在 Ehcache 中的名称
     */
    private static final String DEVICE_CACHE = "wurfl-device-cache";
    /**
     * User-Agent 映射缓存在 Ehcache 中的名称
     */
    private static final String UA_CACHE = "wurfl-ua-cache";
    /**
     * 默认的堆内存大小（MB）
     */
    private static final long HEAP_SIZE_MB = 20;

    /**
     * Ehcache 3.x 缓存管理器，负责管理所有缓存的声明周期
     */
    private final CacheManager cacheManager;
    /**
     * 缓存设备 ID 到设备对象的映射
     */
    private final Cache<String, InternalDevice> deviceIdCache;
    /**
     * 缓存 User-Agent 到设备 ID 的映射
     */
    private final Cache<String, String> userAgentToIdCache;
    /**
     * 确保 close 方法只执行一次，避免重复释放资源
     */
    private final AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * 使用默认堆内存大小（20MB）创建 Ehcache3 缓存提供者。
     */
    public Ehcache3CacheProvider() {
        this(HEAP_SIZE_MB);
    }

    /**
     * 使用指定的堆内存大小创建 Ehcache3 缓存提供者。
     * <p>两个缓存共享相同的堆内存配额，各自独立配置。</p>
     *
     * @param heapSizeMb 每个缓存的堆内存大小（MB）
     */
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
        // 先通过 User-Agent 查找设备 ID，再通过设备 ID 获取设备对象
        String deviceId = this.userAgentToIdCache.get(userAgent);
        if (deviceId == null) {
            return null;
        }
        return this.deviceIdCache.get(deviceId);
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // 支持直接通过设备 ID 查询设备对象
        return this.deviceIdCache.get(deviceId);
    }

    @Override
    public void putDevice(String userAgent, InternalDevice device) {
        if (device == null) return;
        String deviceId = device.getId();
        try {
            // 同时更新设备缓存和 User-Agent 映射缓存
            this.deviceIdCache.put(deviceId, device);
            this.userAgentToIdCache.put(userAgent, deviceId);
        } catch (RuntimeException e) {
            log.error("Could not cache {}: {}", com.scientiamobile.wurfl.core.utils.StringMatchUtils.sanitizeForLog(userAgent), e.getMessage());
        }
    }

    @Override
    public void clear() {
        log.info("Clearing Ehcache3 cache...");
        this.deviceIdCache.clear();
        this.userAgentToIdCache.clear();
        log.info("Ehcache3 cache cleared");
    }

    /**
     * 关闭缓存管理器，释放所有底层资源。
     * <p>使用 CAS 机制确保只会执行一次关闭操作，防止重复关闭导致异常。</p>
     */
    public void close() {
        if (this.closed.compareAndSet(false, true)) {
            this.cacheManager.close();
            log.info("Ehcache3CacheProvider closed");
        }
    }
}
