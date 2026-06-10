package com.scientiamobile.wurfl.core;

import java.util.Map;

/**
 * 能力持有器抽象基类，定义能力值的获取接口。
 * <p>子类需要实现 {@link #getCapability(String)} 和 {@link #getCapabilities()} 方法，
 * 以提供按名称获取单个能力或批量获取全部能力的能力。
 * 同时提供了将能力值转换为整数的便捷方法 {@link #getCapabilityAsInt(String)}。</p>
 */

abstract class CapabilitiesHolder {
    /**
     * 根据能力名称获取对应的能力值。
     *
     * @param capabilityName 能力名称
     * @return 能力值字符串
     */
    public abstract String getCapability(String capabilityName);

    /**
     * 将指定能力的值解析为整数。
     *
     * @param capabilityName 能力名称
     * @return 整型的能力值
     * @throws NumberFormatException 如果能力值无法解析为整数
     */
    public final int getCapabilityAsInt(String capabilityName) {
        String capabilityValue = this.getCapability(capabilityName);

        try {
            return Integer.parseInt(capabilityValue);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("WURFL invalid capability value: " + capabilityName + " expected an integer, received: \"" + capabilityValue + "\"");
        }
    }

    /**
     * 获取所有能力的名称到值映射。
     *
     * @return 能力映射
     */

    public abstract Map<String, String> getCapabilities();
}

