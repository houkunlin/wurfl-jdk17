package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.exc.CapabilityNotDefinedException;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 带缓存的能力持有器，继承自 {@link CapabilitiesHolder}。
 * <p>使用 {@link CapabilitiesProvider} 按需获取能力值，并对已获取的能力进行缓存。
 * 当缓存大小达到最小阈值时，直接返回缓存的全部能力映射，避免重复查询。
 * 支持序列化，在序列化前会确保缓存已填充。</p>
 */

class CachingCapabilitiesHolder extends CapabilitiesHolder implements Serializable {
    @Serial
    private static final long serialVersionUID = 100L;
    private static final boolean assertionsDisabled = !CachingCapabilitiesHolder.class.desiredAssertionStatus();
    /**
     * 触发全部能力加载的最小缓存大小阈值
     */
    private int minCacheSize;
    /**
     * 能力提供者，用于按需获取能力值
     */
    private transient CapabilitiesProvider capabilitiesProvider;
    /** 已获取的能力缓存映射 */
    private Map<String, String> capabilitiesCache;

    public CachingCapabilitiesHolder(CapabilitiesProvider capabilitiesProvider, int minCacheSize) {
        LoggerFactory.getLogger(CachingCapabilitiesHolder.class);
        this.minCacheSize = 39;
        this.capabilitiesProvider = capabilitiesProvider;
        if (minCacheSize > this.minCacheSize) {
            this.minCacheSize = minCacheSize;
        }

        this.capabilitiesCache = new HashMap<>(50);
    }

    @Override
/**
 * 获取指定能力的值。
 * <p>优先从缓存中查找，如果缓存中不存在则通过 {@link CapabilitiesProvider} 获取。</p>
 *
 * @param capabilityName 能力名称
 * @return 能力值
 * @throws CapabilityNotDefinedException 如果能力未定义
 */

    public String getCapability(String capabilityName) {
        String capabilityValue = this.capabilitiesProvider.getCapability(this.capabilitiesCache, capabilityName);
        if (capabilityValue == null) {
            throw new CapabilityNotDefinedException(capabilityName);
        } else {
            return capabilityValue;
        }
    }

    @Override
/**
 * 获取所有能力的映射。
 * <p>当缓存为空或大小不足时，通过 {@link CapabilitiesProvider} 加载全部能力并填充缓存。</p>
 *
 * @return 能力名称到值的映射
 * @throws IllegalStateException 如果设备在序列化前未初始化
 */

    public Map<String, String> getCapabilities() {
        if (this.capabilitiesCache == null || this.capabilitiesCache.size() < this.minCacheSize) {
            if (this.capabilitiesProvider == null) {
                throw new IllegalStateException("The device must be initialized before serialization");
            }

            this.capabilitiesCache = this.capabilitiesProvider.getAllCapabilities();
        }

        if (!assertionsDisabled && this.capabilitiesCache == null) {
            throw new AssertionError();
        } else {
            return this.capabilitiesCache;
        }
    }

    /**
     * 自定义序列化方法，在写入对象前确保能力缓存已加载。
     *
     * @param oos 对象输出流
     * @throws IOException 序列化异常
     */

    private void writeObject(ObjectOutputStream oos) throws IOException {
        if (this.capabilitiesCache == null) {
            this.capabilitiesCache = this.capabilitiesProvider.getAllCapabilities();
        }

        oos.defaultWriteObject();
    }
}
