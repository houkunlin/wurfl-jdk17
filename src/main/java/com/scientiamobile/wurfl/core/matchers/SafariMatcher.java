package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Apple Safari 桌面浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 "Safari" 且以 "Mozilla/5.0 (Macintosh" 或
 * "Mozilla/5.0 (Windows" 开头来识别 Safari 桌面浏览器（排除移动浏览器场景）。
 * RIS 匹配以 "---" 分隔符位置截断。</p>
 */

final class SafariMatcher extends MatcherBase {
    public SafariMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic_web_browser");
        requiredDeviceIds.add("generic_xhtml");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Safari") && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Macintosh", "Mozilla/5.0 (Windows");
    }

    /**
     * 执行 RIS 匹配：以 "---" 分隔符位置加 3 作为截断点。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("---");
        return matchLength != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 3) : null;
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        return !normalizedUserAgent.contains("Macintosh") && !normalizedUserAgent.contains("Windows") ? "generic_xhtml" : "generic_web_browser";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SafariMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Safari";
    }
}
