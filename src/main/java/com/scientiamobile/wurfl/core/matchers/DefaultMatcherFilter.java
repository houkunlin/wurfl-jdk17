package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.LoggerFactory;

/**
 * 默认匹配器过滤器实现，将匹配器与设备索引关联起来。
 * <p>每个匹配器创建一个 DefaultMatcherFilter，持有 FilteredDeviceIndex 存储该匹配器能处理的 User-Agent 映射。</p>
 */

final class DefaultMatcherFilter implements MatcherFilter {
    private Matcher matcher;
    /**
     * 过滤后的设备索引，存储该匹配器能处理的 User-Agent 与设备 ID 的映射
     */
    private FilteredDeviceIndex index;

    public DefaultMatcherFilter(Matcher matcher) {
        LoggerFactory.getLogger(this.getClass());
        this.matcher = matcher;
        this.index = new FilteredDeviceIndex(this);
    }

    /**
     * 判断当前匹配器能否处理该请求，委托给内部的匹配器实例判断。
     *
     * @param request WURFL 请求对象
     * @return 如果内部匹配器能处理该请求则返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return this.matcher.canHandle(request);
    }

    /**
     * 在设备索引中记录请求的 User-Agent 与设备 ID 的映射关系。
     * <p>使用匹配器的规范化方法对 User-Agent 进行预处理后存入索引。</p>
     *
     * @param request  WURFL 请求对象
     * @param deviceId 设备 ID
     * @return 始终返回 {@code true}
     */
    public final boolean recordMatch(WURFLRequest request, String deviceId) {
        this.index.put(this.matcher.normalize(request.getCleanedDeviceUserAgent()), deviceId);
        return true;
    }

    /**
     * 获取设备索引.
     */

    public final FilteredDeviceIndex getIndex() {
        return this.index;
    }
    /**
     * 获取关联的匹配器名称。
     *
     * @return 匹配器名称
     */
    @Override
    public String getMatcherName() {
        return this.matcher.getMatcherName();
    }
}

