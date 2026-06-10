package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

/**
 * Kyocera（京瓷）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "kyocera"、"KWC-" 或 "QC-" 前缀开头来识别 Kyocera 品牌的移动设备。</p>
 */

final class KyoceraMatcher extends MatcherBase {
    @Override
    /**
     * 判断 User-Agent 是否以 Kyocera 相关前缀开头且非桌面浏览器。
     *
     * @param request WURFL 请求对象
     * @return 如果是 Kyocera 设备则返回 {@code true}
     */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "kyocera", "KWC-", "QC-");
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "KyoceraMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Kyocera";
    }
}
