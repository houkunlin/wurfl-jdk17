package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * 匹配器接口，定义了 User-Agent 匹配的核心契约。
 * <p>所有具体的设备或浏览器匹配器都需要实现此接口，以提供以下能力：</p>
 * <ul>
 *   <li>判断能否处理某个请求（{@link #canHandle}）</li>
 *   <li>执行匹配并返回设备信息（{@link #match}）</li>
 *   <li>规范化 User-Agent 字符串（{@link #normalize}）</li>
 *   <li>获取过滤器及匹配器名称（{@link #getFilter}、{@link #getMatcherName}）</li>
 * </ul>
 */

interface Matcher {
    boolean canHandle(WURFLRequest request);

    DeviceInfo match(WURFLRequest request);

    String normalize(String userAgent);

    MatcherFilter getFilter();

    String getMatcherName();
}
