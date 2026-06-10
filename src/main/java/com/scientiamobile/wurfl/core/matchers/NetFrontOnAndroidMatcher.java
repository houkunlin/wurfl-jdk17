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
 * NetFront Life Browser（Android 平台）匹配器。
 * <p>NetFront Life Browser 是 ACCESS 公司开发的 Android 平台浏览器。
 * 通过检查 User-Agent 同时包含 "Android" 和 "NetFrontLifeBrowser/2.2" 来识别。
 * 支持 Android 2.1、2.2、2.3 的版本映射。</p>
 */

final class NetFrontOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER = "generic_android_ver2_0_netfrontlifebrowser";
    private static final Map<String, String> ANDROID_VERSION_TO_DEVICE_ID;

    static {
        ANDROID_VERSION_TO_DEVICE_ID = new HashMap<>();
        ANDROID_VERSION_TO_DEVICE_ID.put("2.1", "generic_android_ver2_1_netfrontlifebrowser");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.2", "generic_android_ver2_2_netfrontlifebrowser");
        ANDROID_VERSION_TO_DEVICE_ID.put("2.3", "generic_android_ver2_3_netfrontlifebrowser");
    }

    public NetFrontOnAndroidMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Android", "NetFrontLifeBrowser/2.2");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, StringMatchUtils.indexOfOrLength(userAgent, "NetFrontLifeBrowser/2.2"));
    }

    @Override
/**
 * 执行恢复匹配.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String androidVersion = UserAgentUtils.getAndroidVersion(request.getNormalizedDeviceUserAgent(), true);
        String deviceId = ANDROID_VERSION_TO_DEVICE_ID.get(androidVersion);
        return deviceId != null ? deviceId : GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "NetFrontOnAndroidMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "NetFrontOnAndroid";
    }
}
