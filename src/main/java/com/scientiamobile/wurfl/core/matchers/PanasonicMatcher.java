package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Panasonic（松下）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "Panasonic" 开头来识别 Panasonic 品牌的移动设备。</p>
 */

final class PanasonicMatcher extends MatcherBase {
    @Override
    /**
     * 判断 User-Agent 是否以 "Panasonic" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Panasonic 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Panasonic");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "PanasonicMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Panasonic";
    }
}
