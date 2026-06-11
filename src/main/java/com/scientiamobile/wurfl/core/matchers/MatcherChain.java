package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/*/**
 * 匹配器链，实现了责任链（Chain of Responsibility）模式。
 * <p>持有一个有序的匹配器列表，依次尝试每个匹配器直到找到第一个能处理该请求的匹配器。</p>
 */

class MatcherChain implements Matcher, MatcherFilter {
    private static final Logger logger = LoggerFactory.getLogger(MatcherChain.class);
    private List<Matcher> matchers = new LinkedList<>();
    private List<MatcherFilter> filters = new LinkedList<>();

    /**
     * 添加匹配器.
     */

    public final void addMatcher(Matcher matcher) {
        this.matchers.add(matcher);
        this.filters.add(matcher.getFilter());
    }

    @Override
/**
 * Attempts to match the given request to a device.
 * @param request the WURFL request
 * @return device info for the matched device
 */

    public DeviceInfo match(WURFLRequest request) {

        for (Matcher value : this.matchers) {
            Matcher matcher;
            matcher = value;
            matcher.getMatcherName();
            if (matcher.canHandle(request)) {
                return matcher.match(request);
            }
        }

        if (logger.isWarnEnabled()) {
            logger.warn("No any matcher can handle the request: " + request + ", returning generic device.");
        }

        return new DeviceInfo("generic", MatchType.none, this.getMatcherName(), "MatcherChain", request.getOriginalUserAgent(), "");
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return true;
    }

    @Override
    /**
     * 匹配器链的归一化方法直接返回原始 User-Agent，不做任何处理。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 未经修改的 User-Agent 字符串
     */

    public String normalize(String userAgent) {
        return userAgent;
    }

    public MatcherFilter getFilter() {
        return this;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "MatcherChain";
    }

    public final boolean recordMatch(WURFLRequest request, String deviceId) {

        for (MatcherFilter matcherFilter : this.filters) {
            MatcherFilter filter;
            filter = matcherFilter;
            if (filter.canHandle(request)) {
                filter.recordMatch(request, deviceId);
                return true;
            }
        }

        return false;
    }

    /**
     * 获取设备索引.
     */

    public final FilteredDeviceIndex getIndex() {
        logger.warn("A Filter of type MatcherChain should never be asked for its FilteredDevices set.");
        FilteredDeviceIndex filteredDeviceIndex = new FilteredDeviceIndex(this);

        for (MatcherFilter filter : this.filters) {
            for (String userAgent : filter.getIndex().getUserAgents()) {
                filteredDeviceIndex.put(userAgent, filter.getIndex().getDeviceIdByUserAgent(userAgent));
            }
        }

        return filteredDeviceIndex;
    }

    /**
     * 对所有过滤器中的 User-Agent 索引进行排序。
     * <p>如果某个过滤器是 {@link MatcherChain} 类型，则递归调用其 {@code sortAll()} 方法。</p>
     */

    public final void sortAll() {
        for (MatcherFilter matcherFilter : this.filters) {
            MatcherFilter filter;
            filter = matcherFilter;
            if (filter instanceof MatcherChain chain) {
                chain.sortAll();
            } else {
                filter.getIndex().sortUserAgents();
            }
        }

    }
}
