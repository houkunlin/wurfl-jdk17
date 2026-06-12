package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 匹配器链，实现了责任链（Chain of Responsibility）模式。
 * <p>持有一个有序的匹配器列表，依次尝试每个匹配器直到找到第一个能处理该请求的匹配器。</p>
 */

class MatcherChain implements Matcher, MatcherFilter {
    private static final Logger logger = LoggerFactory.getLogger(MatcherChain.class);
    private List<Matcher> matchers = new LinkedList<>();
    private List<MatcherFilter> filters = new LinkedList<>();

    /**
     * 向匹配器链中添加一个匹配器及其对应的过滤器。
     *
     * @param matcher 待添加的匹配器实例
     */

    public final void addMatcher(Matcher matcher) {
        this.matchers.add(matcher);
        this.filters.add(matcher.getFilter());
    }

    /**
     * 沿匹配器链依次尝试匹配请求，直到找到能处理该请求的匹配器。
     * <p>遍历所有注册的匹配器，对能处理当前请求的第一个匹配器执行匹配并返回结果。
     * 如果没有匹配器能处理该请求，则返回 Generic 设备。</p>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备信息
     */
    @Override
    public DeviceInfo match(WURFLRequest request) {

        for (Matcher value : this.matchers) {
            Matcher matcher = value;
            if (matcher.canHandle(request)) {
                return matcher.match(request);
            }
        }

        if (logger.isWarnEnabled()) {
            logger.warn("No any matcher can handle the request: " + request + ", returning generic device.");
        }

        return new DeviceInfo("generic", MatchType.none, this.getMatcherName(), "MatcherChain", request.getOriginalUserAgent(), "");
    }

    /**
     * 判断当前匹配器链能否处理该请求（总是返回 true）。
     * <p>匹配器链作为顶层入口始终可以处理请求，实际的分发由链中的各个匹配器决定。</p>
     *
     * @param request WURFL 请求对象
     * @return 始终返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return true;
    }

    /**
     * 匹配器链的规范化方法直接返回原始 User-Agent，不做任何处理。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 未经修改的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return userAgent;
    }

    /**
     * 获取匹配器链自身的过滤器引用。
     * <p>MatcherChain 本身也实现了 MatcherFilter 接口，因此返回自身。</p>
     *
     * @return 当前匹配器链实例作为过滤器
     */
    public MatcherFilter getFilter() {
        return this;
    }

    /**
     * 获取匹配器名称。
     *
     * @return 固定返回 "MatcherChain"
     */
    @Override
    public String getMatcherName() {
        return "MatcherChain";
    }

    /**
     * 在所有过滤器中记录请求与设备 ID 的匹配关系。
     * <p>遍历过滤器列表，找到第一个能处理该请求的过滤器并记录匹配关系。</p>
     *
     * @param request  WURFL 请求对象
     * @param deviceId 匹配的设备 ID
     * @return 如果成功记录了匹配关系则返回 {@code true}
     */
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
     * 获取设备索引，合并所有过滤器的 User-Agent 映射。
     * <p>遍历所有过滤器的索引，将它们的 User-Agent 到设备 ID 的映射合并到一个新的索引中。</p>
     *
     * @return 合并后的设备索引
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
