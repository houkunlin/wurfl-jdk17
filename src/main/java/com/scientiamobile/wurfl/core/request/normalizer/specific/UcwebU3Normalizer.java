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

    @Override
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
    public String normalize(String userAgent) {
        String ucBrowserVersion;
        ucBrowserVersion = UserAgentUtils.getUcBrowserVersion(userAgent, false);
        if (ucBrowserVersion == null) {
            return userAgent;
        } else {
            String normalizedPrefix = null;
            if (userAgent.contains("Windows Phone")) {
                String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
                String windowsPhoneModel;
                windowsPhoneModel = UserAgentUtils.getWindowsPhoneModel(userAgent);
                if (windowsPhoneModel != null && windowsPhoneVersion != null) {
                    normalizedPrefix = windowsPhoneVersion + " U3WP " + ucBrowserVersion + " " + windowsPhoneModel + "---";
                }
            } else if (userAgent.contains("Android")) {
                String androidModel = UserAgentUtils.getAndroidModel(userAgent);
                String androidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
                if (androidModel != null && androidVersion != null) {
                    normalizedPrefix = androidVersion + " U3Android " + ucBrowserVersion + " " + androidModel + "---";
                }
            } else if (userAgent.contains("iPhone;")) {
                Matcher matcher;
                matcher = IPHONE.matcher(userAgent);
                if (matcher.find()) {
                    String iosVersion = matcher.group(1) + "." + (matcher.group(2) == null ? "" : matcher.group(2));
                    normalizedPrefix = iosVersion + " U3iPhone " + ucBrowserVersion + "---";
                }
            } else {
                Matcher matcher;
                matcher = IPAD.matcher(userAgent);
                if (userAgent.contains("iPad") && matcher.find()) {
                    String iosMajorVersion = matcher.group(1);
                    String iosMinorVersion = matcher.group(2);
                    String iosVersion = iosMajorVersion + "." + (iosMinorVersion == null ? "" : iosMinorVersion);
                    String ipadModel = matcher.group(3);
                    normalizedPrefix = iosVersion + " U3iPad " + ucBrowserVersion + " " + ipadModel + "---";
                }
            }

            return normalizedPrefix == null ? userAgent : normalizedPrefix + userAgent;
        }
    }
}
