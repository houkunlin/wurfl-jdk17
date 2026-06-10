package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * 能力提供者接口，定义从设备模型获取能力值的策略。
 * <p>提供了获取全部能力映射和按名称获取单个能力的接口。
 * {@link #getCapability(Map, String)} 方法允许传入外部缓存映射，
 * 在查找能力时先从缓存中读取，未命中时再回源查询。</p>
 */

interface CapabilitiesProvider {
    /**
     * 获取设备的所有能力名称到值的映射。
     *
     * @return 全部能力映射
     */
    Map<String, String> getAllCapabilities();

    /**
     * 从给定的缓存映射中获取指定能力的值，如果不存在则回源查询。
     *
     * @param capabilities   能力缓存映射
     * @param capabilityName 能力名称
     * @return 能力值，如果未定义则返回 {@code null}
     */
    String getCapability(Map<String, String> capabilities, String capabilityName);
}

