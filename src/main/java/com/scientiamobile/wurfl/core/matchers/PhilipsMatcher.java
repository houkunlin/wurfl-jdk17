package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Philips（飞利浦）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "philips"（不区分大小写）开头来识别 Philips 品牌的移动设备。</p>
 */

final class PhilipsMatcher extends MatcherBase {
    /**
     * 判断 User-Agent 是否以 "philips" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Philips 设备则返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "philips", 0, 7);
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "PhilipsMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Philips";
    }
}
