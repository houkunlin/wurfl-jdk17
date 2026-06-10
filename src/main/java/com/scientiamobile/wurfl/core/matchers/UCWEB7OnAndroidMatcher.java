package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * UCWEB 7 浏览器（Android 平台）匹配器。
 * <p>通过检查 User-Agent 同时包含 "Android" 和 "UCWEB7" 来识别。
 * 支持 Android 1.6、2.1、2.2、2.3 四个版本的 UCWEB7 浏览器映射。</p>
 */

final class UCWEB7OnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_ANDROID_VER2_0_UCWEB = "generic_android_ver2_0_ucweb";
    private static final Map<String, String> ANDROID_VERSION_TO_DEVICE_ID;

    static {
        ANDROID_VERSION_TO_DEVICE_ID = new HashMap<>();
        ANDROID_VERSION_TO_DEVICE_ID.put("1.6", "generic_android_ver1_6_ucweb");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.1", "generic_android_ver2_1_ucweb");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.2", "generic_android_ver2_2_ucweb");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.3", "generic_android_ver2_3_ucweb");
    }

    public UCWEB7OnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(ANDROID_VERSION_TO_DEVICE_ID.values());
        requiredDeviceIds.add(GENERIC_ANDROID_VER2_0_UCWEB);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Android", "UCWEB7");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, StringMatchUtils.indexOfOrLength(userAgent, "UCWEB7"));
    }

    @Override
/**
 * 执行恢复匹配.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String androidVersion = UserAgentUtils.getAndroidVersion(request.getNormalizedDeviceUserAgent(), true);
        String deviceId = ANDROID_VERSION_TO_DEVICE_ID.get(androidVersion);
        return deviceId != null ? deviceId : GENERIC_ANDROID_VER2_0_UCWEB;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "UCWEB7OnAndroidMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Ucweb7OnAndroid";
    }
}
