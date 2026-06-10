package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

/**
 * 缓存提供者接口，定义了 WURFL 设备数据缓存的核心操作契约。
 * <p>实现类可根据具体场景选用不同的缓存策略，如内存缓存、分布式缓存或空缓存（无缓存）。</p>
 * <p>缓存操作均以设备 ID 或 User-Agent 为键，以 {@link InternalDevice} 为值进行存取。</p>
 */

public interface CacheProvider {

    /**
     * 根据设备 ID 获取缓存的设备对象。
     *
     * @param deviceId 设备唯一标识符
     * @return 缓存的设备对象，若未命中缓存则返回 {@code null}
     */
    InternalDevice getDevice(String deviceId);

    /**
     * 根据设备 ID 获取内部的设备对象。
     * <p>与 {@link #getDevice(String)} 的区别在于，此方法返回的是未经外部封装的原生设备对象，
     * 通常用于框架内部调用以绕过装饰逻辑。</p>
     *
     * @param deviceId 设备唯一标识符
     * @return 内部设备对象，若未命中缓存则返回 {@code null}
     */
    InternalDevice getInternalDeviceFromDeviceId(String deviceId);

    /**
     * 将设备对象存入缓存。
     *
     * @param deviceId 设备唯一标识符，作为缓存键
     * @param device   待缓存的设备对象
     */
    void putDevice(String deviceId, InternalDevice device);

    /**
     * 清空缓存中的所有数据。
     * <p>通常在重新加载 WURFL 数据时调用，以确保缓存与最新数据一致。</p>
     */
    void clear();
}
