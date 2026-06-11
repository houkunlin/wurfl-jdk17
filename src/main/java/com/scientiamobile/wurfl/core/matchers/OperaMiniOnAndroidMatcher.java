package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Android 平台上的 Opera Mini 浏览器匹配器。
 * <p>通过检查 User-Agent 同时包含 "Android" 和 "Opera Mini" 来识别。
 * RIS 匹配以 " Build/" 分隔或预定义的前缀长度截断。</p>
 */

final class OperaMiniOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_OPERA_MINI_ANDROID_VERSION5 = "generic_opera_mini_android_version5";
    private static final String[] OPERA_MINI_ANDROID_PREFIXES = new String[]{"Opera/9.80 (J2ME/MIDP; Opera Mini/5", "Opera/9.80 (Android; Opera Mini/5.0", "Opera/9.80 (Android; Opera Mini/5.1"};

    public OperaMiniOnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_OPERA_MINI_ANDROID_VERSION5);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(request.getCleanedDeviceUserAgent(), "Android", "Opera Mini");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = userAgent.indexOf(" Build/");
        if (matchLength < 0) {
            for (String prefix : OPERA_MINI_ANDROID_PREFIXES) {
                if (userAgent.startsWith(prefix)) {
                    matchLength = prefix.length();
                    break;
                }
            }
        }

        return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : null;
    }
    /**
     * 恢复匹配策略：统一返回 Android Opera Mini v5 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 固定返回 {@code "generic_opera_mini_android_version5"}
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return GENERIC_OPERA_MINI_ANDROID_VERSION5;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "OperaMiniOnAndroidMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "OperaMiniOnAndroid";
    }
}
