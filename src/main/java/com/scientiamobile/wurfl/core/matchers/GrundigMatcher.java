package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Grundig（根德）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "grundig"（不区分大小写）开头来识别 Grundig 品牌的移动设备。</p>
 */

final class GrundigMatcher extends MatcherBase {
    @Override
    /**
     * 判断 User-Agent 是否以 "grundig" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Grundig 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && cleanedDeviceUserAgent != null
                && cleanedDeviceUserAgent.regionMatches(true, 0, "grundig", 0, 7);
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "GrundigMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Grundig";
    }
}
