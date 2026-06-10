package com.scientiamobile.wurfl.core;

/**
 * 增强设备接口，在 {@link Device} 的基础上增加了匹配元数据信息。
 * <p>提供了获取匹配器名称、桶匹配器名称和归一化 User-Agent 的方法，
 * 用于在设备检测过程中记录详细的匹配过程信息，方便调试和分析。</p>
 */

public interface EnrichedDevice extends Device {
    /**
     * 获取最终匹配器的名称。
     *
     * @return 匹配器名称
     */
    String getMatcherName();

    /**
     * 获取桶匹配器的名称。
     *
     * @return 桶匹配器名称
     */
    String getBucketMatcherName();

    /**
     * 获取归一化后的 User-Agent 字符串。
     *
     * @return 归一化后的 User-Agent
     */
    String getNormalizedUserAgent();
}
