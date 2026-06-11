package com.scientiamobile.wurfl.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.scientiamobile.wurfl.core.InternalDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 基于 Caffeine 缓存库实现的缓存提供者。
 * <p>Caffeine 是高性能的本地缓存库，支持基于大小的淘汰策略和写入后过期策略。
 * 本实现使用两个独立的 Caffeine 缓存分别存储设备数据（设备 ID → 设备对象）
 * 和 User-Agent 映射关系（User-Agent → 设备 ID），并支持统计缓存命中率等指标。</p>
 * <p>适用于对缓存性能要求较高的生产环境。</p>
 */

public class CaffeineCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(CaffeineCacheProvider.class);

    /**
     * 缓存设备 ID 到设备对象的映射，支持基于大小的淘汰和过期策略
     */
    private final Cache<String, InternalDevice> deviceIdCache;
    /**
     * 缓存 User-Agent 到设备 ID 的映射，用于快速从 User-Agent 定位设备
     */
    private final Cache<String, String> userAgentToDeviceIdCache;

    /**
     * 使用默认参数创建 Caffeine 缓存提供者：
     * 设备缓存 2000 条，User-Agent 缓存 10000 条，无过期时间。
     */
    public CaffeineCacheProvider() {
        this(2000, 10000, -1, TimeUnit.MINUTES);
    }

    /**
     * 使用指定参数创建 Caffeine 缓存提供者，时间单位默认为分钟。
     *
     * @param deviceCacheSize    设备缓存的最大条目数
     * @param userAgentCacheSize User-Agent 缓存的最大条目数
     * @param expireAfterMinutes 写入后过期时间（分钟），小于等于 0 表示不过期
     */
    public CaffeineCacheProvider(int deviceCacheSize, int userAgentCacheSize, int expireAfterMinutes) {
        this(deviceCacheSize, userAgentCacheSize, expireAfterMinutes, TimeUnit.MINUTES);
    }

    /**
     * 使用完整参数创建 Caffeine 缓存提供者。
     * <p>两个缓存均启用统计功能（recordStats），可通过 {@link #estimatedDeviceCacheSize()} 等方法
     * 获取缓存大小估算值。</p>
     *
     * @param deviceCacheSize    设备缓存的最大条目数
     * @param userAgentCacheSize User-Agent 缓存的最大条目数
     * @param expireAfterMinutes 写入后过期时间，小于等于 0 表示不过期
     * @param timeUnit           过期时间的时间单位
     */
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
    public InternalDevice getDevice(String userAgent) {
        // 先通过 User-Agent 查找设备 ID，再通过设备 ID 获取设备对象
        String deviceId = this.userAgentToDeviceIdCache.getIfPresent(userAgent);
        if (deviceId == null) {
            return null;
        }
        return this.deviceIdCache.getIfPresent(deviceId);
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // 支持直接通过设备 ID 查询设备对象
        return this.deviceIdCache.getIfPresent(deviceId);
    }

    @Override
    public void putDevice(String userAgent, InternalDevice device) {
        if (device == null) return;
        String deviceId = device.getId();
        try {
            // 同时更新设备缓存和 User-Agent 映射缓存
            this.deviceIdCache.put(deviceId, device);
            this.userAgentToDeviceIdCache.put(userAgent, deviceId);
        } catch (RuntimeException e) {
            log.error("Could not cache " + userAgent, e);
        }
    }

    @Override
    public void clear() {
        log.info("Clearing Caffeine cache...");
        this.deviceIdCache.invalidateAll();
        this.userAgentToDeviceIdCache.invalidateAll();
        log.info("Caffeine cache cleared");
    }

    /**
     * 估算当前设备缓存中的条目数量。
     * <p>注意：Caffeine 返回的是近似值，而非精确计数。</p>
     *
     * @return 设备缓存的估算大小
     */
    public long estimatedDeviceCacheSize() {
        return this.deviceIdCache.estimatedSize();
    }

    /**
     * 估算当前 User-Agent 映射缓存中的条目数量。
     *
     * @return User-Agent 缓存的估算大小
     */
    public long estimatedUaCacheSize() {
        return this.userAgentToDeviceIdCache.estimatedSize();
    }
}
