package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

/**
 * Siemens（西门子）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "SIE-" 开头来识别 Siemens 品牌的移动设备。</p>
 */

final class SiemensMatcher extends MatcherBase {
    @Override
    /**
     * 判断 User-Agent 是否以 "SIE-" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Siemens 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("SIE-");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "SiemensMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Siemens";
    }
}
