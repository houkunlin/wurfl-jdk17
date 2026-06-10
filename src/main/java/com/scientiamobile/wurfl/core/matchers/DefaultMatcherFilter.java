package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.LoggerFactory;

/*/**
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

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return this.matcher.canHandle(request);
    }

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

    @Override
    /**
     * 获取关联的匹配器名称。
     *
     * @return 匹配器名称
     */

    public String getMatcherName() {
        return this.matcher.getMatcherName();
    }
}

