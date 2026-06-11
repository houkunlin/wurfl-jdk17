package com.scientiamobile.wurfl.core.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 集合工厂工具类，提供创建常用集合实例的便捷静态方法。
 * <p>当前主要用于创建配置了合理初始容量的 {@link ConcurrentHashMap} 实例，
 * 以满足 WURFL 引擎在并发场景下高效存储缓存数据的需求。</p>
 */

public abstract class CollectionFactory {

    /**
     * 使用默认参数创建一个 {@link ConcurrentHashMap} 实例。
     * <p>默认初始容量为 6000，加载因子为 0.75，并发级别为 16，
     * 适用于大多数 WURFL 缓存场景。</p>
     *
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 新创建的 ConcurrentHashMap 实例
     */
    public static <K, V> Map<K, V> createConcurrentHashMap() {
        return createConcurrentHashMap(6000, 0.75F, 16);
    }

    /**
     * 使用自定义参数创建一个 {@link ConcurrentHashMap} 实例。
     * <p>允许调用方根据预期的数据量和并发访问量精确控制集合的性能特征，
     * 避免在高并发场景下因扩容或锁竞争导致性能下降。</p>
     *
     * @param initialCapacity  初始容量，决定 Map 的初始大小
     * @param loadFactor       加载因子，控制扩容阈值
     * @param concurrencyLevel 并发级别，预估同时更新的线程数
     * @param <K>              键类型
     * @param <V>              值类型
     * @return 新创建的 ConcurrentHashMap 实例
     */
    public static <K, V> Map<K, V> createConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
        return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }
}
