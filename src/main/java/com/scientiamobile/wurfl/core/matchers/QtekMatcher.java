package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Qtek 品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "Qtek" 开头来识别 Qtek 品牌的移动设备。</p>
 */

final class QtekMatcher extends MatcherBase {
    /**
     * 判断 User-Agent 是否以 "Qtek" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Qtek 设备则返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Qtek");
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "QtekMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Qtek";
    }
}
