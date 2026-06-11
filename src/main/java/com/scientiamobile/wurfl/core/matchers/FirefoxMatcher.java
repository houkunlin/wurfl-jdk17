package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Mozilla Firefox 桌面浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 Firefox（排除移动浏览器等场景）来识别 Firefox 桌面浏览器。</p>
 */

final class FirefoxMatcher extends MatcherBase {
    private static final String FIREFOX_DEVICE_ID = "firefox";

    public FirefoxMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(FIREFOX_DEVICE_ID);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser()
                && cleanedDeviceUserAgent.contains("Firefox")
                && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Tablet", "Sony", "Novarra", "Opera");
    }
    /**
     * 执行 RIS 匹配：截取从 "Firefox" 关键字开始到第一个点号后一位的子串进行匹配。
     *
     * @param normalizedUserAgent 规范化后的 User-Agent
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        String firefoxUserAgent = normalizedUserAgent.substring(normalizedUserAgent.indexOf("Firefox"));
        int matchLength = StringMatchUtils.indexOfOrLength(firefoxUserAgent, ".");
        return matchLength == -1
                ? null
                : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), firefoxUserAgent, matchLength + 1);
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return FIREFOX_DEVICE_ID;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "FirefoxMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Firefox";
    }
}
