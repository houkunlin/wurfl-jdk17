package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.matchers.MatchType;

/**
 * 设备匹配结果信息，包含设备 ID 和匹配过程的详细信息。
 * <p>记录匹配到的设备 ID、匹配类型、匹配器名称、桶匹配器名称、
 * 归一化前后的 User-Agent 字符串，用于分析和调试设备检测结果。</p>
 */

public class DeviceInfo {
    /**
     * 匹配到的设备 ID
     */
    private final String id;
    /**
     * 匹配类型
     */
    private final MatchType matchType;
    /**
     * 最终匹配器的名称
     */
    private final String matcherName;
    /**
     * 桶匹配器的名称
     */
    private final String bucketMatcherName;
    /**
     * 归一化后的 User-Agent
     */
    private final String normalizedUserAgent;
    /**
     * 原始的 User-Agent
     */
    private final String originalUserAgent;

    public DeviceInfo(String id, MatchType matchType, String matcherName, String bucketMatcherName, String normalizedUserAgent, String originalUserAgent) {
        this.id = id;
        this.matchType = matchType;
        this.matcherName = matcherName;
        this.bucketMatcherName = bucketMatcherName;
        this.normalizedUserAgent = normalizedUserAgent;
        this.originalUserAgent = originalUserAgent;
    }

    /**
     * 获取匹配到的设备 ID。
     *
     * @return 设备 ID
     */

    public String getId() {
        return this.id;
    }

    /**
     * 获取匹配类型。
     *
     * @return 匹配类型
     */

    public MatchType getMatchType() {
        return this.matchType;
    }

    /**
     * 获取最终匹配器的名称。
     *
     * @return 匹配器名称
     */

    public String getMatcherName() {
        return this.matcherName;
    }

    /**
     * 获取桶匹配器的名称。
     *
     * @return 桶匹配器名称
     */

    public String getBucketMatcherName() {
        return this.bucketMatcherName;
    }

    /**
     * 获取归一化后的 User-Agent 字符串。
     *
     * @return 归一化后的 User-Agent
     */

    public String getNormalizedUserAgent() {
        return this.normalizedUserAgent;
    }

    /**
     * 获取原始 User-Agent 字符串。
     *
     * @return 原始 User-Agent
     */

    public String getOriginalUserAgent() {
        return this.originalUserAgent;
    }

    @Override
/**
 * 返回设备匹配信息的字符串表示，格式为 {@code {id='设备ID', match=匹配类型, matcher=匹配器名称}}。
 *
 * @return 字符串表示
 */

    public String toString() {
        StringBuilder builder;
        builder = new StringBuilder("{id='");
        builder.append(this.id).append('\'');
        builder.append(", match=").append(this.matchType);
        builder.append(", matcher=").append(this.matcherName);
        builder.append('}');
        return builder.toString();
    }
}
