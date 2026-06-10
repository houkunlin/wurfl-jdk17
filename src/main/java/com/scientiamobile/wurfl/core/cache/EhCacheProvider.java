package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于 EhCache 2.x 的缓存提供者实现。
 * <p>通过包装 EhCache 2.x 的 {@link Cache} 对象，提供 WURFL 设备数据的缓存能力。
 * 支持通过 {@link EhCacheManager} 间接获取缓存实例，或直接注入 {@link Cache} 对象。</p>
 * <p>注意：此实现仅支持单层缓存结构（设备 ID → 设备对象），
 * 不支持通过 User-Agent 直接查询设备对象。</p>
 */

public class EhCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(EhCacheProvider.class);
    /**
     * 底层的 EhCache 2.x 缓存实例
     */
    private Cache cache;

    public EhCacheProvider() {
    }

    /**
     * 使用指定的 EhCache 缓存实例创建提供者。
     *
     * @param cache EhCache 2.x 缓存实例
     */
    public EhCacheProvider(Cache cache) {
        this.cache = cache;
    }

    /**
     * 通过 EhCache 管理器创建提供者，使用默认缓存实例。
     *
     * @param ehCacheManager EhCache 缓存管理器
     */
    public EhCacheProvider(EhCacheManager ehCacheManager) {
        this(ehCacheManager.getDefaultCache());
    }

    /**
     * 获取当前使用的 EhCache 缓存实例。
     *
     * @return EhCache 缓存实例
     */
    public Cache getCache() {
        return this.cache;
    }

    /**
     * 设置 EhCache 缓存实例。
     *
     * @param cache EhCache 缓存实例
     */
    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public void clear() {
        log.info("Cache size: {}", this.cache.getSize());
        this.cache.removeAll();
        log.info("Cache erased. size: {}", this.cache.getSize());
    }

    @Override
    public InternalDevice getDevice(String key) {
        // EhCache 以 Element 为单位存储数据，需要从中提取对象值
        Element element = this.cache.get(key);
        InternalDevice device = null;
        if (element != null) {
            device = (InternalDevice) element.getObjectValue();
        }
        return device;
    }

    @Override
    public void putDevice(String key, InternalDevice device) {
        // 将设备对象包装为 EhCache 的 Element 后存入缓存
        Element element = new Element(key, device);
        this.cache.put(element);
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // EhCacheProvider 不支持通过设备 ID 直接查询，始终返回 null
        return null;
    }
}
