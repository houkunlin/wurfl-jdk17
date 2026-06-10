package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

import java.util.Locale;

/**
 * Alcatel（阿尔卡特）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "alcatel"（不区分大小写）开头来识别 Alcatel 品牌的移动设备。</p>
 */

final class AlcatelMatcher extends MatcherBase {
    @Override
    /**
     * 判断 User-Agent 是否以 "alcatel" 开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Alcatel 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "AlcatelMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Alcatel";
    }
}
