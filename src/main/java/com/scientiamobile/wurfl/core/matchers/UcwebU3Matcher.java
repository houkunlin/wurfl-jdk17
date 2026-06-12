package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UCWeb U3 内核浏览器匹配器。
 * <p>UCWeb U3 是 UC 浏览器基于 WebKit 的新版内核，运行在 Android、iOS 和 Windows Phone 平台上。通过检查 User-Agent 是否以 Mozilla 开头且包含 UCBrowser 来识别。</p>
 */

final class UcwebU3Matcher extends MatcherBase {
    private static final String GENERIC_MS_PHONE_OS8_SUBUAWCWEB = "generic_ms_phone_os8_subuaucweb";
    private static final String GENERIC_UCWEB_ANDROID_VER1 = "generic_ucweb_android_ver1";
    private static final String APPLE_IPHONE_VER1_SUBUAWCWEB = "apple_iphone_ver1_subuaucweb";
    private static final String APPLE_IPAD_VER1_SUBUAWCWEB = "apple_ipad_ver1_subuaucweb";
    private static final Pattern IPHONE_IOS_VERSION = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
    private static final Pattern IPAD_IOS_VERSION = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
    private static final List<String> SUPPORTED_DEVICE_IDS;

    static {
        SUPPORTED_DEVICE_IDS = new ArrayList<>();
        SUPPORTED_DEVICE_IDS.add("generic_ucweb");
        SUPPORTED_DEVICE_IDS.add(GENERIC_UCWEB_ANDROID_VER1);
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver2");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver3");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver4");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver5");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver6");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver7");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver8");
        SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver9");
        SUPPORTED_DEVICE_IDS.add(APPLE_IPHONE_VER1_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver2_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver3_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver4_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver5_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver6_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver7_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver8_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver9_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver10_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_iphone_ver11_subuaucweb");
        SUPPORTED_DEVICE_IDS.add(APPLE_IPAD_VER1_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub4_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub5_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub6_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub7_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub8_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub9_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub10_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub11_subuaucweb");
        SUPPORTED_DEVICE_IDS.add(GENERIC_MS_PHONE_OS8_SUBUAWCWEB);
        SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os8_1_subuaucweb");
        SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os10_subuaucweb");
    }

    public UcwebU3Matcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        return new HashSet<>(SUPPORTED_DEVICE_IDS);
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("Mozilla") && cleanedDeviceUserAgent.contains("UCBrowser");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        if (UserAgentUtils.getUcBrowserVersion(userAgent, false) == null) {
            return null;
        }

        if (!isValidUcwebPlatform(userAgent)) {
            return null;
        }

        int matchLength = userAgent.indexOf("---") + 3;
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 验证 UCWeb U3 请求是否来自已知平台且设备信息完整。
     *
     * @return 平台信息有效返回 {@code true}
     */
    private static boolean isValidUcwebPlatform(String userAgent) {
        if (userAgent.contains("Windows Phone")) {
            return UserAgentUtils.getWindowsPhoneModel(userAgent) != null
                    && UserAgentUtils.getWindowsPhoneVersion(userAgent) != null;
        }
        if (userAgent.contains("Android")) {
            return UserAgentUtils.getAndroidModel(userAgent) != null
                    && UserAgentUtils.getAndroidVersion(userAgent, false) != null;
        }
        if (userAgent.contains("iPhone;")) {
            return UcwebU3Normalizer.IPHONE.matcher(userAgent).find();
        }
        if (userAgent.contains("iPad")) {
            return UcwebU3Normalizer.IPAD.matcher(userAgent).find();
        }
        return false;
    }

    /**
     * 恢复匹配策略：根据 UC 浏览器运行的平台（Windows Phone、Android、iPhone、iPad）
     * 和操作系统版本构造对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String ua = request.getNormalizedDeviceUserAgent();

        if (ua.contains("Windows Phone")) {
            return resolveDeviceId(buildWindowsPhoneDeviceId(ua), GENERIC_MS_PHONE_OS8_SUBUAWCWEB);
        }
        if (ua.contains("Android")) {
            return resolveDeviceId(buildAndroidDeviceId(ua), GENERIC_UCWEB_ANDROID_VER1);
        }
        if (ua.contains("iPhone")) {
            return resolveDeviceId(buildIphoneDeviceId(ua), APPLE_IPHONE_VER1_SUBUAWCWEB);
        }
        if (ua.contains("iPad")) {
            return resolveDeviceId(buildIpadDeviceId(ua), APPLE_IPAD_VER1_SUBUAWCWEB);
        }
        return "generic_ucweb";
    }

    private static String buildWindowsPhoneDeviceId(String ua) {
        String version = UserAgentUtils.getWindowsPhoneVersion(ua);
        if (StringUtils.isEmpty(version)) {
            LOG.debug("user agent \"{}\" has no version information", ua.replaceAll("[\\r\\n]", "_"));
            return null;
        }
        String[] parts = version.split("\\.");
        if (parts.length < 2) {
            return null;
        }
        String major = parts[0];
        String minor = parts[1];
        return StringUtils.isEmpty(minor)
                ? "generic_ms_phone_os" + major + "_subuaucweb"
                : "generic_ms_phone_os" + major + "_" + minor + "_subuaucweb";
    }

    private static String buildAndroidDeviceId(String ua) {
        String version = UserAgentUtils.getAndroidVersion(ua, false);
        if (StringUtils.isEmpty(version)) {
            return null;
        }
        String[] parts = version.split("\\.");
        return parts.length > 0 ? "generic_ucweb_android_ver" + parts[0] : null;
    }

    private static String buildIphoneDeviceId(String ua) {
        Matcher matcher = IPHONE_IOS_VERSION.matcher(ua);
        return matcher.find() ? "apple_iphone_ver" + matcher.group(1) + "_subuaucweb" : null;
    }

    private static String buildIpadDeviceId(String ua) {
        Matcher matcher = IPAD_IOS_VERSION.matcher(ua);
        return matcher.find() ? "apple_ipad_ver1_sub" + matcher.group(1) + "_subuaucweb" : null;
    }

    private static String resolveDeviceId(String deviceId, String fallback) {
        return SUPPORTED_DEVICE_IDS.contains(deviceId) ? deviceId : fallback;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "UcwebU3Matcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "UcwebU3";
    }
}
