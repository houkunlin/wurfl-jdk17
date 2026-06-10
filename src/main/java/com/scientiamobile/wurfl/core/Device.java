package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

import java.util.Map;

/**
 * 设备接口，表示一次设备检测返回的最终设备信息。
 * <p>继承自 {@link InternalDevice}，扩展了匹配类型、虚拟能力和标记语言信息的获取方法。
 * 这是 WURFL 引擎返回给用户的设备表示层。</p>
 */

public interface Device extends InternalDevice {
    /**
     * 获取匹配类型（如精确匹配、缓存匹配等）。
     *
     * @return 匹配类型
     */
    MatchType getMatchType();

    /**
     * 获取指定名称的虚拟能力值。
     *
     * @param capabilityName 虚拟能力名称
     * @return 虚拟能力值
     */
    String getVirtualCapability(String capabilityName);

    /**
     * 获取虚拟能力值并转换为整数。
     *
     * @param capabilityName 虚拟能力名称
     * @return 整型的能力值
     */
    int getVirtualCapabilityAsInt(String capabilityName);

    /**
     * 获取虚拟能力值并转换为布尔值。
     *
     * @param capabilityName 虚拟能力名称
     * @return 布尔型的能力值
     */
    boolean getVirtualCapabilityAsBool(String capabilityName);

    /**
     * 获取所有虚拟能力的名称到值映射。
     *
     * @return 虚拟能力映射
     */
    Map<String, String> getVirtualCapabilities();

    /**
     * 获取设备支持的标记语言类型。
     *
     * @return 标记语言枚举
     */
    MarkUp getMarkUp();
}
