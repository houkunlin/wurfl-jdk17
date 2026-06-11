package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * LG（LG 电子）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 "lg"（不区分大小写）开头来识别 LG 品牌的移动设备。
 * RIS 匹配找到 "LG" 关键字后的斜杠位置截断，恢复匹配使用固定长度 7 截断。</p>
 */

final class LGMatcher extends MatcherBase {
    public LGMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "lg", 0, 2);
    }

    /**
     * 执行 RIS 匹配：找到 "LG" 关键字后的斜杠位置作为截断点。
     *
     * @param normalizedUserAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", normalizedUserAgent.indexOf("LG"));
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        FilteredDeviceIndex deviceIndex = this.getFilter().getIndex();
        String matchedUserAgent;
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        matchedUserAgent = StringMatchUtils.risMatch(deviceIndex.getUserAgents(), normalizedDeviceUserAgent, 7);
        return matchedUserAgent != null
                ? deviceIndex.getDeviceIdByUserAgent(matchedUserAgent)
                : "generic";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "LGMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "LG";
    }
}
