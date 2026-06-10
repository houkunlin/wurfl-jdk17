package com.scientiamobile.wurfl.core.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * EhCache 缓存管理器的简单实现。
 * <p>使用 EhCache 2.x 的默认配置创建 CacheManager，并约定默认缓存名称为
 * {@value #DEFAULT_CACHE_NAME}（即 InternalDevice 的完全限定类名）。</p>
 * <p>适用于 EhCache 2.x 的简单部署场景，无需自定义缓存配置。</p>
 */

public class SimpleEhCacheManager implements EhCacheManager {
    /**
     * 默认缓存名称，使用 InternalDevice 的完全限定类名作为缓存标识
     */
    public static final String DEFAULT_CACHE_NAME = "com.scientiamobile.wurfl.core.InternalDevice";
    private static final Logger logger = LoggerFactory.getLogger(SimpleEhCacheManager.class);
    /**
     * 底层的 EhCache CacheManager 实例，通过默认构造函数加载 ehcache.xml 配置文件
     */
    private final CacheManager cacheManager = new CacheManager();

    @Override
    public Cache getDefaultCache() {
        return this.getCache("com.scientiamobile.wurfl.core.InternalDevice");
    }

    @Override
    public Cache getCache(String cacheName) {
        return this.cacheManager.getCache(cacheName);
    }

    @Override
    public void shutdown() {
        logger.info("shutting down cache manager");
        this.cacheManager.shutdown();
    }
}
