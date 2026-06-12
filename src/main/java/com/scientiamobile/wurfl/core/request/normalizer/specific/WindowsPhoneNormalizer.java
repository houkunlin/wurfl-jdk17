package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Windows Phone 设备的 User-Agent 特定规范化器。
 * <p>Windows Phone 设备的 UA 格式多样，包括原生应用 UA、广告客户端 UA 和桌面版（ Continuum）UA 等。
 * 该规范化器识别多种 Windows Phone UA 格式，提取系统版本和设备型号信息，
 * 构建统一格式的前缀，提高 Windows Phone 设备的匹配准确性。</p>
 */
public class WindowsPhoneNormalizer implements UserAgentNormalizer {
    /**
     * 匹配 Windows Phone 广告客户端的 UA 格式，提取设备型号。
     */
    private static final Pattern WINDOWS_PHONE_AD_CLIENT_MODEL_PATTERN = Pattern.compile("Windows ?Phone ?Ad ?Client/[0-9\\.]+? ?\\([^;]+; ?Windows ?Phone(?: ?OS)? ?[0-9\\.]+; ?([^;\\)]+(?:; ?[^;\\)]+)?)");
    /**
     * 匹配 Windows Phone 原生应用 UA 格式，提取系统版本和设备型号。
     */
    private static final Pattern WINDOWS_PHONE_APP_UA_PATTERN = Pattern.compile("^[^/]+/[0-9\\.-_]+ Windows Phone/([\\d\\.]+) (.+)$");

    /**
     * 规范化给定的 User-Agent 字符串。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        String windowsPhoneModel = null;
        String windowsPhoneVersion = null;
        Matcher windowsPhoneAppUaMatcher;
        windowsPhoneAppUaMatcher = WINDOWS_PHONE_APP_UA_PATTERN.matcher(userAgent);
        if (windowsPhoneAppUaMatcher.find()) {
            userAgent = "Mozilla/5.0 (Mobile; Windows Phone " + windowsPhoneAppUaMatcher.group(1) + "; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; " + windowsPhoneAppUaMatcher.group(2) + ") like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537 " + userAgent;
        }

        if (!StringMatchUtils.containsAnyOf(userAgent, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(userAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            if (UserAgentUtils.isWindowsPhoneAdClient(userAgent)) {
                windowsPhoneModel = UserAgentUtils.cleanAndReplaceWindowsPhoneModel(userAgent, WINDOWS_PHONE_AD_CLIENT_MODEL_PATTERN);
                windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
            } else if (!userAgent.contains("NativeHost")) {
                windowsPhoneModel = UserAgentUtils.getWindowsPhoneModel(userAgent);
                windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
            }
        } else {
            windowsPhoneModel = UserAgentUtils.getWindowsPhoneDesktopModel(userAgent);
            windowsPhoneVersion = UserAgentUtils.getWindowsPhoneDesktopVersion(userAgent);
        }

        return windowsPhoneModel != null && windowsPhoneVersion != null ? "WP" + windowsPhoneVersion + " " + windowsPhoneModel + "---" + userAgent : userAgent;
    }
}
