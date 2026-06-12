package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UC 浏览器 U3 内核的 User-Agent 特定规范化器。
 * <p>UC 浏览器 U3 内核的 UA 中包含 WebKit 信息，不同平台（Windows Phone、Android、iPhone、iPad）
 * 的 UA 结构各不相同。该规范化器提取各平台的版本号和设备信息，
 * 构建统一格式的前缀，提高 UC U3 设备的匹配准确性。</p>
 */
public class UcwebU3Normalizer implements UserAgentNormalizer {
    /**
     * 匹配 iPhone 平台 UC U3 UA 中的 iOS 版本号。
     */
    public static final Pattern IPHONE = Pattern.compile("iPhone OS (\\d+)_(\\d+)(?:_\\d+)* like");
    /**
     * 匹配 iPad 平台 UC U3 UA 中的 iOS 版本号和设备型号。
     */
    public static final Pattern IPAD = Pattern.compile("CPU OS (\\d+)_(\\d+)?.+like Mac.+; iPad([0-9,]+)\\) AppleWebKit");

    /**
     * 规范化 UC 浏览器 U3 内核的 User-Agent。
     * <p>根据平台类型分别处理：</p>
     * <ul>
     *   <li>Windows Phone → 提取 WP 版本、型号和 UC 版本</li>
     *   <li>Android → 提取 Android 版本、型号和 UC 版本</li>
     *   <li>iPhone → 提取 iOS 版本和 UC 版本</li>
     *   <li>iPad → 提取 iOS 版本、iPad 型号和 UC 版本</li>
     * </ul>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        String ucBrowserVersion = UserAgentUtils.getUcBrowserVersion(userAgent, false);
        if (ucBrowserVersion == null) {
            return userAgent;
        }

        String normalizedPrefix = buildUcwebPrefix(userAgent, ucBrowserVersion);
        return normalizedPrefix == null ? userAgent : normalizedPrefix + userAgent;
    }

    /**
     * 根据 UA 中的平台标识构建 UC U3 规范化前缀。
     */
    private static String buildUcwebPrefix(String userAgent, String ucBrowserVersion) {
        if (userAgent.contains("Windows Phone")) {
            return buildWindowsPhonePrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("Android")) {
            return buildAndroidPrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("iPhone;")) {
            return buildIphonePrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("iPad")) {
            return buildIpadPrefix(userAgent, ucBrowserVersion);
        }
        return null;
    }

    private static String buildWindowsPhonePrefix(String userAgent, String ucBrowserVersion) {
        String version = UserAgentUtils.getWindowsPhoneVersion(userAgent);
        String model = UserAgentUtils.getWindowsPhoneModel(userAgent);
        if (version == null || model == null) {
            return null;
        }
        return version + " U3WP " + ucBrowserVersion + " " + model + "---";
    }

    private static String buildAndroidPrefix(String userAgent, String ucBrowserVersion) {
        String model = UserAgentUtils.getAndroidModel(userAgent);
        String version = UserAgentUtils.getAndroidVersion(userAgent, false);
        if (model == null || version == null) {
            return null;
        }
        return version + " U3Android " + ucBrowserVersion + " " + model + "---";
    }

    private static String buildIphonePrefix(String userAgent, String ucBrowserVersion) {
        Matcher matcher = IPHONE.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        }
        String iosVersion = matcher.group(1) + "." + (matcher.group(2) == null ? "" : matcher.group(2));
        return iosVersion + " U3iPhone " + ucBrowserVersion + "---";
    }

    private static String buildIpadPrefix(String userAgent, String ucBrowserVersion) {
        Matcher matcher = IPAD.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        }
        String iosVersion = matcher.group(1) + "." + (matcher.group(2) == null ? "" : matcher.group(2));
        String ipadModel = matcher.group(3);
        return iosVersion + " U3iPad " + ucBrowserVersion + " " + ipadModel + "---";
    }
}
