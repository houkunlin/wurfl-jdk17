package com.scientiamobile.wurfl.core.matchers;

import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 过滤后的设备索引，维护匹配器能处理的 User-Agent 与设备 ID 的映射。
 * <p>使用 TreeMap 确保有序性，同时维护独立的 List 支持高效遍历和排序。</p>
 */

final class FilteredDeviceIndex {
    private final MatcherFilter ownerFilter;
    private SortedMap<String, String> userAgentToDeviceId;
    private List<String> userAgents;

    public FilteredDeviceIndex(MatcherFilter ownerFilter) {
        LoggerFactory.getLogger(this.getClass());
        this.userAgentToDeviceId = new TreeMap<>();
        this.userAgents = new ArrayList<>();
        this.ownerFilter = ownerFilter;
    }

    /**
     * 获取所有 User-Agent 的集合视图。
     *
     * @return User-Agent 列表集合
     */

    public final Collection<String> getUserAgents() {
        return this.userAgents;
    }

    /**
     * 对所有 User-Agent 列表按字典序排序。
     * <p>排序后的列表可用于高效的二分查找和 RIS（最长公共前缀）匹配算法。</p>
     */
    public final void sortUserAgents() {
        Collections.sort(this.userAgents);
    }

    /**
     * 根据 User-Agent 从索引中获取对应的设备 ID。
     *
     * @param userAgent User-Agent 字符串
     * @return 对应的设备 ID，如果不存在则返回 {@code null}
     * @throws NullPointerException 如果 userAgent 为 {@code null}
     */

    public final String getDeviceIdByUserAgent(String userAgent) {
        Validate.notNull(userAgent, "The userAgent is empty");
        return this.userAgentToDeviceId.get(userAgent);
    }

    /**
     * Put.
     */

    public final void put(String userAgent, String deviceId) {
        Validate.notNull(userAgent, "user-agent cannot be null");
        Validate.notEmpty(deviceId, "The deviceId is empty");
        this.userAgentToDeviceId.put(userAgent, deviceId);
        this.userAgents.add(userAgent);
    }

    /**
     * 返回该对象的字符串表示，包含所属过滤器的匹配器名称和所有设备 ID。
     *
     * @return 匹配器名称和设备 ID 集合的字符串表示
     */
    @Override
    public String toString() {
        return this.ownerFilter.getMatcherName() + this.userAgentToDeviceId.values();
    }
}
