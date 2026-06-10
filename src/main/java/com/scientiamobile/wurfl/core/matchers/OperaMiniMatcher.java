package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Opera Mini 移动浏览器匹配器。
 * <p>Opera Mini 是 Opera 公司推出的移动端浏览器，通过代理服务器压缩网页内容以节省流量。支持 Opera Mini v1 到 v7 的版本映射。</p>
 */

final class OperaMiniMatcher extends MatcherBase {
    private static final SortedMap<String, String> OPERA_MINI_VERSION_TO_DEVICE_ID;

    static {
        OPERA_MINI_VERSION_TO_DEVICE_ID = new TreeMap<>();
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/1", "generic_opera_mini_version1");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/2", "generic_opera_mini_version2");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/3", "generic_opera_mini_version3");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/4", "generic_opera_mini_version4");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/5", "generic_opera_mini_version5");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/6", "generic_opera_mini_version6");
        OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/7", "generic_opera_mini_version7");
    }

    public OperaMiniMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(OPERA_MINI_VERSION_TO_DEVICE_ID.values());
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "Opera Mini", "OperaMini", "Opera Mobi", "OperaMobi");
    }

    @Override
    /**
     * 执行 RIS 匹配：按优先级尝试以下截断位置：
     * <ul>
     *   <li>"---" 分隔符</li>
     *   <li>"Opera Mini" 关键字后的点号位置</li>
     *   <li>第一个斜杠位置</li>
     * </ul>
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf("---");
        if (matchLength >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 3);
        } else if ((matchLength = StringMatchUtils.indexOf(userAgent, "Opera Mini")) >= 0 && (matchLength = StringMatchUtils.indexOf(userAgent, ".", matchLength)) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength + 1);
        } else {
            matchLength = StringMatchUtils.firstSlash(userAgent);
            return matchLength != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : StringMatchUtils.NULL_STRING;
        }
    }

    @Override
/**
 * 执行恢复匹配.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        for (String versionPrefix : OPERA_MINI_VERSION_TO_DEVICE_ID.keySet()) {
            if (normalizedUserAgent.toLowerCase().contains(versionPrefix.toLowerCase())) {
                return OPERA_MINI_VERSION_TO_DEVICE_ID.get(versionPrefix);
            }
        }

        return normalizedUserAgent.contains("Opera Mobi") ? "generic_opera_mini_version4" : "generic_opera_mini_version1";
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "OperaMiniMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "OperaMini";
    }
}
