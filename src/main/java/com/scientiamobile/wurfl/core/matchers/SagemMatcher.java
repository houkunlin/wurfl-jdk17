package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Sagem（萨基姆）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "sagem"（不区分大小写）开头来识别 Sagem 品牌的移动设备。</p>
 */

final class SagemMatcher extends MatcherBase {
    /**
     * 判断 User-Agent 是否以 "sagem" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Sagem 设备则返回 {@code true}
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "sagem", 0, 5);
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SagemMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Sagem";
    }
}
