package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * 匹配器过滤器接口，定义了匹配器与设备索引之间的交互契约。
 * <p>过滤器负责以下职责：</p>
 * <ul>
 *   <li>判断匹配器能否处理某个请求（{@link #canHandle}）</li>
 *   <li>记录某个 User-Agent 与设备 ID 的匹配关系到索引中（{@link #recordMatch}）</li>
 *   <li>获取管理当前匹配器设备子集的索引（{@link #getIndex}）</li>
 *   <li>获取关联匹配器的名称（{@link #getMatcherName}）</li>
 * </ul>
 * <p>每个匹配器都有一个对应的过滤器实例，用于隔离和管理与该匹配器相关的设备数据。</p>
 */

interface MatcherFilter {
    boolean canHandle(WURFLRequest request);

    boolean recordMatch(WURFLRequest request, String deviceId);

    FilteredDeviceIndex getIndex();

    String getMatcherName();
}
