package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Konqueror 浏览器匹配器。
 * <p>Konqueror 是 KDE 桌面环境下的原生网页浏览器。
 * 该匹配器通过检查 User-Agent 是否包含 "Konqueror" 来识别，且仅用于非移动浏览器场景。</p>
 */

final class KonquerorMatcher extends MatcherBase {
    /**
     * 判断 User-Agent 是否包含 "Konqueror" 且非移动浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Konqueror 浏览器则返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsMobileBrowser() && request.getCleanedDeviceUserAgent().contains("Konqueror");
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "KonquerorMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Konqueror";
    }
}
