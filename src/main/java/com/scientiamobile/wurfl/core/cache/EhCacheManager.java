package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;

/**
 * EhCache 缓存管理器接口，定义使用 EhCache 2.x 时的缓存管理操作。
 * <p>提供获取默认缓存、按名称获取缓存以及关闭缓存管理器的能力，
 * 抽象了底层 CacheManager 的实现细节，便于切换不同的 EhCache 管理策略。</p>
 */

public interface EhCacheManager {
    /**
     * 获取默认的 EhCache 缓存实例。
     *
     * @return 默认的 EhCache 缓存
     */
    Cache getDefaultCache();

    /**
     * 根据名称获取指定的 EhCache 缓存实例。
     *
     * @param cacheName 缓存名称
     * @return 指定名称的 EhCache 缓存
     */
    Cache getCache(String cacheName);

    /**
     * 关闭缓存管理器，释放所有缓存资源。
     */
    void shutdown();
}
