package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTC（宏达电）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 HTC 或 XV6875 来识别 HTC 品牌的移动设备。RIS 匹配使用正则表达式提取 HTC 前缀作为截断点。</p>
 */

final class HTCMatcher extends MatcherBase {
    private static final Pattern HTC_PREFIX = Pattern.compile("^.*?HTC[^/ ;]+[/ ;]");

    public HTCMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        requiredDeviceIds.add("generic_ms_mobile");
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "HTC", "XV6875");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.length();
        Matcher prefixMatcher;
        prefixMatcher = HTC_PREFIX.matcher(normalizedUserAgent);
        if (prefixMatcher.find()) {
            matchLength = prefixMatcher.group(0).length();
        }

        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    /**
     * 恢复匹配策略：如果 User-Agent 包含 Windows CE 平台信息，返回 Windows Mobile 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "HTCMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "HTC";
    }
}
