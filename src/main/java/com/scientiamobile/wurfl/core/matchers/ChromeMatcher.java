package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Google Chrome 桌面浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 "Chrome"（排除移动浏览器场景）来识别 Chrome 桌面浏览器。
 * RIS 匹配以第一个点号位置截断，恢复匹配统一返回 {@code "google_chrome"}。</p>
 */

final class ChromeMatcher extends MatcherBase {
    private static final String CHROME_DEVICE_ID = "google_chrome";

    public ChromeMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(CHROME_DEVICE_ID);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsMobileBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.contains("Chrome");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        return StringMatchUtils.risMatch(
                this.getFilter().getIndex().getUserAgents(),
                normalizedUserAgent,
                StringMatchUtils.indexOfOrLength(normalizedUserAgent, ".")
        );
    }

    @Override
/**
 * 执行恢复匹配.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return CHROME_DEVICE_ID;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "ChromeMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Chrome";
    }
}
