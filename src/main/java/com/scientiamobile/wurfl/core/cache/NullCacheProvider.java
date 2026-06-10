package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;

/**
 * 空缓存提供者，采用空对象（Null Object）设计模式。
 * <p>当不需要缓存功能时使用此实现，所有操作均为空操作（no-op）：</p>
 * <ul>
 *   <li>{@code getDevice()} 始终返回 {@code null}</li>
 *   <li>{@code putDevice()} 不做任何存储</li>
 *   <li>{@code clear()} 不执行任何清理</li>
 * </ul>
 * <p>适用于测试场景或对实时性要求极高、不需要缓存的部署环境。</p>
 */

public class NullCacheProvider implements CacheProvider {
    @Override
    public void clear() {
        // 空实现：无缓存需要清理
    }

    @Override
    public void putDevice(String deviceId, InternalDevice device) {
        // 空实现：不缓存任何设备数据
    }

    @Override
    public InternalDevice getDevice(String deviceId) {
        return null;
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        return null;
    }
}
