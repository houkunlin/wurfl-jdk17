package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Android 平台上的 Opera Mobile 或 Opera Tablet 浏览器匹配器。
 * <p>通过检查 User-Agent 同时包含 Android 和 Opera Tablet 或 Opera Mobi 来识别。恢复匹配根据 Android 版本号构造对应的 Opera 设备 ID，区分手机和平板形态。</p>
 */

final class OperaMobiOrTabletOnAndroidMatcher extends MatcherBase {
    private static final String GENERIC_ANDROID_VER2_0_OPERA_MOBI = "generic_android_ver2_0_opera_mobi";
    private static final String GENERIC_ANDROID_VER2_1_OPERA_TABLET = "generic_android_ver2_1_opera_tablet";
    private static final Set<String> SUPPORTED_ANDROID_OPERA_DEVICE_IDS;

    static {
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS = new HashSet<>();
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver1_5_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver1_6_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_0_OPERA_MOBI);
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_1_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_mobi");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_1_OPERA_TABLET);
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_0_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_1_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_2_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_tablet");
        SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_tablet");
    }

    public OperaMobiOrTabletOnAndroidMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(SUPPORTED_ANDROID_OPERA_DEVICE_IDS);
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Opera Tablet", "Opera Mobi");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        int matchLength;
        matchLength = (matchLength = userAgent.indexOf("---")) == -1 ? userAgent.length() : matchLength + 3;
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
    /**
     * 恢复匹配策略：根据 Android 版本号和 Opera 类型（Tablet/Mobi）拼接设备 ID。
     * 如果拼接的设备 ID 不在支持的列表中，则返回对应类型的默认值。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        boolean isOperaTablet = normalizedUserAgent.contains("Opera Tablet");
        String androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent, true);
        String deviceId = "generic_android_ver" + androidVersion.replaceAll("\\.", "_") + "_opera_" + (isOperaTablet ? "tablet" : "mobi");
        if (SUPPORTED_ANDROID_OPERA_DEVICE_IDS.contains(deviceId)) {
            return deviceId;
        } else {
            return isOperaTablet ? GENERIC_ANDROID_VER2_1_OPERA_TABLET : GENERIC_ANDROID_VER2_0_OPERA_MOBI;
        }
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "OperaMobiOrTabletOnAndroidMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "OperaMobiOrTabletOnAndroid";
    }
}
