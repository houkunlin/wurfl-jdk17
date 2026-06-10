package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.jsr107cache.Cache;

/**
 * 基于 JSR 107（JCache）规范的缓存提供者实现。
 * <p>通过包装标准的 JCache {@link Cache} 对象，提供 WURFL 设备数据的缓存能力。
 * 适用于已经集成 JCache 兼容缓存实现（如 Hazelcast、Infinispan 等）的项目，
 * 便于统一缓存管理。</p>
 * <p>注意：此实现仅支持单层缓存结构（设备 ID → 设备对象），
 * 不支持通过 User-Agent 直接查询设备对象。</p>
 */

public class JSR107CacheProvider implements CacheProvider {
    /**
     * 底层的 JCache 缓存实例
     */
    private Cache cache;

    public JSR107CacheProvider() {
    }

    /**
     * 使用指定的 JCache 缓存实例创建提供者。
     *
     * @param cache JCache 缓存实例
     */
    public JSR107CacheProvider(Cache cache) {
        this.cache = cache;
    }

    /**
     * 设置 JCache 缓存实例。
     *
     * @param cache JCache 缓存实例
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public InternalDevice getDevice(String deviceId) {
        return (InternalDevice) this.cache.get(deviceId);
    }

    @Override
    public void putDevice(String deviceId, InternalDevice device) {
        this.cache.put(deviceId, device);
    }

    @Override
    public void clear() {
        this.cache.clear();
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // JSR107CacheProvider 不支持通过设备 ID 直接查询，始终返回 null
        return null;
    }
}
