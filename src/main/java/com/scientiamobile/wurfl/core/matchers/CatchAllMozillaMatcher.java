package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Catch-All Mozilla 匹配器，作为所有 Mozilla 系浏览器的最终兜底。
 * <p>当所有更专业的匹配器都未能处理时，此匹配器处理以 Mozilla/3、Mozilla/4 或 Mozilla/5 开头的请求。</p>
 */

final class CatchAllMozillaMatcher extends AbstractMatcher {
    public CatchAllMozillaMatcher(WURFLModel wurflModel) {
        super(wurflModel);
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
        return StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "Mozilla/3", "Mozilla/4", "Mozilla/5");
    }

    /**
     * 确定匹配策略：以第一个右括号位置截断执行 RIS 匹配，如果匹配失败则返回 "generic"。
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        int matchLength = StringMatchUtils.firstCloseParenthesis(normalizedUserAgent);
        String matchedUserAgent = matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
        String deviceId = "generic";
        if (matchedUserAgent != null) {
            deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent);
        }

        if (deviceId == null) {
            deviceId = "generic";
        }

        return deviceId;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "CatchAllMozillaMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "CatchAllMozilla";
    }
}
