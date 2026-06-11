package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Windows Phone 移动操作系统匹配器。
 * <p>通过检查 User-Agent 是否包含 Windows Phone、WindowsPhone、ZuneWP7、WPDesktop、NativeHost 或同时包含 Windows NT、ARM、Edge/ 来识别 Windows Phone 设备。</p>
 */

final class WindowsPhoneMatcher extends AbstractMatcher {
    private static final String GENERIC_MS_PHONE_OS7 = "generic_ms_phone_os7";
    private static final String GENERIC_MS_PHONE_OS7_DESKTOPMODE = "generic_ms_phone_os7_desktopmode";
    private static final String GENERIC_MS_PHONE_OS7_5_DESKTOPMODE = "generic_ms_phone_os7_5_desktopmode";
    private static final String GENERIC_MS_PHONE_OS8_DESKTOPMODE = "generic_ms_phone_os8_desktopmode";
    private static final String GENERIC_MS_PHONE_OS10_DESKTOPMODE = "generic_ms_phone_os10_desktopmode";
    private static final String WP_DESKTOP = "WPDesktop";
    private static final Map<String, String> VERSION_TO_DEVICE_ID = Map.ofEntries(
            Map.entry("10.0", "generic_ms_phone_os10"),
            Map.entry("8.1", "generic_ms_phone_os8_1"),
            Map.entry("8.0", "generic_ms_phone_os8"),
            Map.entry("7.8", "generic_ms_phone_os7_8"),
            Map.entry("7.5", "generic_ms_phone_os7_5"),
            Map.entry("7.0", GENERIC_MS_PHONE_OS7),
            Map.entry("6.5", "generic_ms_winmo6_5")
    );

    public WindowsPhoneMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> ids = new HashSet<>(VERSION_TO_DEVICE_ID.values());
        ids.add(GENERIC_MS_PHONE_OS7_DESKTOPMODE);
        ids.add(GENERIC_MS_PHONE_OS7_5_DESKTOPMODE);
        ids.add(GENERIC_MS_PHONE_OS8_DESKTOPMODE);
        ids.add(GENERIC_MS_PHONE_OS10_DESKTOPMODE);
        ids.add("generic");
        return ids;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && (StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, WP_DESKTOP, "ZuneWP7") || StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") || StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Windows Phone", "WindowsPhone", "NativeHost"));
    }
    /**
     * 确定匹配策略：如果 User-Agent 包含 "---" 分隔符或 "NativeHost" 关键字，
     * 则特殊处理（NativeHost 直接返回 Windows Phone 7 通用设备 ID），否则退回到基类方法。
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备 ID
     */
    @Override
    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("---")) {
            return super.applyConclusiveMatch(request);
        }
        if (normalizedUserAgent.contains("NativeHost")) {
            return GENERIC_MS_PHONE_OS7;
        }
        return super.applyConclusiveMatch(request);
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        int index = userAgent.indexOf("---");
        if (index < 0) {
            return null;
        }
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, index + 3);
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        if (StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            return GENERIC_MS_PHONE_OS10_DESKTOPMODE;
        }
        if (StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, WP_DESKTOP, "ZuneWP7")) {
            if (cleanedDeviceUserAgent.contains(WP_DESKTOP)) {
                return GENERIC_MS_PHONE_OS8_DESKTOPMODE;
            }
            return cleanedDeviceUserAgent.contains("Trident/5.0") ? GENERIC_MS_PHONE_OS7_5_DESKTOPMODE : GENERIC_MS_PHONE_OS7_DESKTOPMODE;
        }
        String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(cleanedDeviceUserAgent);
        String deviceId = VERSION_TO_DEVICE_ID.get(windowsPhoneVersion);
        if (deviceId != null) {
            return deviceId;
        }
        return UserAgentUtils.isWindowsPhoneAdClient(cleanedDeviceUserAgent) ? GENERIC_MS_PHONE_OS7 : "generic";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "WindowsPhoneMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "WindowsPhone";
    }
}
