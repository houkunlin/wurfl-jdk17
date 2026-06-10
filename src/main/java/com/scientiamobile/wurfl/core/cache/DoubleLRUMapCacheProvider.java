package com.scientiamobile.wurfl.core.cache;

import com.scientiamobile.wurfl.core.InternalDevice;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.LRUMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 双层 LRU 映射缓存提供者，使用两个 LRU 缓存分别存储 User-Agent 到设备 ID 的映射
 * 以及设备 ID 到设备对象的映射。
 * <p>设计目的：在实际的 WURFL 查询流程中，通常以 User-Agent 作为输入参数，
 * 经过匹配算法得到设备 ID 后再获取设备详情。双层缓存结构能够同时缓存
 * User-Agent 到设备 ID 的映射关系和设备 ID 到设备对象的映射关系，
 * 从而在后续相同 User-Agent 的请求中直接命中缓存，跳过匹配算法。</p>
 * <p>两个缓存独立设置大小，可以根据实际场景分别调优。
 * 通常 User-Agent 缓存容量应大于设备缓存容量，因为多个 User-Agent 可能映射到同一设备。</p>
 */

public class DoubleLRUMapCacheProvider implements CacheProvider {
    private static final Logger log = LoggerFactory.getLogger(DoubleLRUMapCacheProvider.class);
    /**
     * 缓存 User-Agent 到设备 ID 的映射关系
     */
    private final Map<String, String> deviceIdByUserAgent;
    /**
     * 缓存设备 ID 到设备对象的映射关系
     */
    private final Map<String, InternalDevice> deviceById;

    /**
     * 使用完整的参数创建双层 LRU 缓存提供者。
     *
     * @param userAgentCacheSize User-Agent 缓存的最大条目数
     * @param deviceCacheSize    设备缓存的最大条目数
     * @param scanUntilRemovable 是否在淘汰时扫描整个映射，详见 {@link LRUMap}
     */
    public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize, boolean scanUntilRemovable) {
        this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize, scanUntilRemovable));
        this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize, scanUntilRemovable));
    }

    /**
     * 使用指定的缓存大小创建双层 LRU 缓存提供者，采用默认淘汰行为。
     *
     * @param userAgentCacheSize User-Agent 缓存的最大条目数
     * @param deviceCacheSize    设备缓存的最大条目数
     */
    public DoubleLRUMapCacheProvider(int userAgentCacheSize, int deviceCacheSize) {
        this.deviceIdByUserAgent = MapUtils.synchronizedMap(new LRUMap<>(userAgentCacheSize));
        this.deviceById = MapUtils.synchronizedMap(new LRUMap<>(deviceCacheSize));
    }

    /**
     * 使用默认大小创建双层 LRU 缓存提供者：
     * User-Agent 缓存容量为 10000，设备缓存容量为 2000。
     */
    public DoubleLRUMapCacheProvider() {
        this(10000, 2000);
    }

    @Override
    public void clear() {
        log.info("UA cache: size {}", this.deviceIdByUserAgent.size());
        this.deviceIdByUserAgent.clear();
        log.info("UA cache cleared: size {}", this.deviceIdByUserAgent.size());
        log.info("device cache: size {}", this.deviceById.size());
        this.deviceById.clear();
        log.info("device cache cleared: size {}", this.deviceById.size());
    }

    @Override
    public InternalDevice getDevice(String userAgent) {
        // 先通过 User-Agent 查找设备 ID
        String deviceId = this.deviceIdByUserAgent.get(userAgent);
        if (deviceId == null) {
            return null;
        }
        // 再通过设备 ID 获取设备对象
        InternalDevice device = this.deviceById.get(deviceId);
        return device;
    }

    @Override
    public void putDevice(String userAgent, InternalDevice device) {
        try {
            // 同时更新两个缓存层
            this.deviceById.put(device.getId(), device);
            this.deviceIdByUserAgent.put(userAgent, device.getId());
        } catch (RuntimeException e) {
            log.error("Could not cache {}", userAgent, e);
        }
    }

    @Override
    public InternalDevice getInternalDeviceFromDeviceId(String deviceId) {
        // 支持直接通过设备 ID 从设备缓存中查询
        return this.deviceById.get(deviceId);
    }
}
