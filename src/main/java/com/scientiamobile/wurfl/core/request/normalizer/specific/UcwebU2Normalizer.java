package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * UC 浏览器 U2 内核的 User-Agent 特定规范化器。
 * <p>UC 浏览器 U2 内核在不同平台（Android、iOS、Windows Phone、Symbian、Java）
 * 上的 UA 结构各不相同。该规范化器识别各平台的 UA 格式，提取版本号和设备信息，
 * 构建统一格式的前缀，提高 UC U2 设备的匹配准确性。</p>
 */

public class UcwebU2Normalizer implements UserAgentNormalizer {
    public static final Pattern IPHONE = Pattern.compile("iPh OS (\\d)_?(\\d)?[ _\\d]?.+; iPh(\\d), ?(\\d)\\) U2");
    public static final Pattern WINDOWS_PHONE = Pattern.compile("^UCWEB.+; wds (\\d+)\\.([\\d]+);.+; ([ A-Za-z0-9_-]+); ([ A-Za-z0-9_-]+)\\) U2");
    public static final Pattern SYMBIAN = Pattern.compile("^UCWEB.+; S60 V(\\d); .+; (.+)\\) U2");
    public static final Pattern JAVA = Pattern.compile("^UCWEB[^\\(]+\\(Java; .+; (.+)\\) U2");
    private static final Pattern SEMICOLON_WITHOUT_SPACE_PATTERN = Pattern.compile(";(?! )");
    private static final Pattern NOKIA_RM_MODEL_PATTERN = Pattern.compile("(NOKIA.RM-.+?)_.*");

    /**
     * 规范化 UC 浏览器 U2 内核的 User-Agent。
     * <p>根据平台类型分别处理：</p>
     * <ul>
     *   <li>Android（"Adr"）→ 提取 Android 版本、型号和 UC 版本</li>
     *   <li>iOS（"iPh OS"）→ 提取 iOS 版本、设备型号和 UC 版本</li>
     *   <li>Windows Phone（"wds"）→ 提取 WP 版本、设备型号和 UC 版本</li>
     *   <li>Symbian → 提取 S60 版本、设备型号和 UC 版本</li>
     *   <li>Java → 提取设备型号和 UC 版本</li>
     * </ul>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        String ucBrowserVersion = UserAgentUtils.getUcBrowserVersion(userAgent, true);
        if (ucBrowserVersion == null) {
            return userAgent;
        }

        String normalizedPrefix = buildUcwebPrefix(userAgent, ucBrowserVersion);
        return normalizedPrefix == null ? userAgent : normalizedPrefix + userAgent;
    }

    /**
     * 根据 UA 中的平台标识构建 UC U2 规范化前缀。
     *
     * @return 规范化前缀，无法识别平台时返回 {@code null}
     */
    private static String buildUcwebPrefix(String userAgent, String ucBrowserVersion) {
        if (userAgent.contains("Adr")) {
            return buildAndroidPrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("iPh OS")) {
            return buildIosPrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("wds")) {
            return buildWindowsPhonePrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("Symbian")) {
            return buildSymbianPrefix(userAgent, ucBrowserVersion);
        }
        if (userAgent.contains("Java")) {
            return buildJavaPrefix(userAgent, ucBrowserVersion);
        }
        return null;
    }

    private static String buildAndroidPrefix(String userAgent, String ucBrowserVersion) {
        String model = UserAgentUtils.getUcAndroidModel(userAgent, false);
        String androidVersion = UserAgentUtils.getUcAndroidVersion(userAgent, false);
        if (model == null || androidVersion == null) {
            return null;
        }
        return androidVersion + " U2Android " + ucBrowserVersion + " " + model + "---";
    }

    private static String buildIosPrefix(String userAgent, String ucBrowserVersion) {
        Matcher matcher = IPHONE.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        }
        String iosVersion = matcher.group(1) + "." + matcher.group(2);
        String iphoneDeviceVersion = matcher.group(3) + "." + matcher.group(4);
        return iosVersion + " U2iPhone " + ucBrowserVersion + " " + iphoneDeviceVersion + "---";
    }

    private static String buildWindowsPhonePrefix(String userAgent, String ucBrowserVersion) {
        String fixedUserAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
        Matcher matcher = WINDOWS_PHONE.matcher(fixedUserAgent);
        if (!matcher.find()) {
            return null;
        }
        String windowsPhoneVersion = matcher.group(1) + "." + matcher.group(2);
        String modelName = (matcher.group(3) + "." + matcher.group(4)).replace("_blocked", "");
        modelName = NOKIA_RM_MODEL_PATTERN.matcher(modelName).replaceFirst("$1");
        return windowsPhoneVersion + " U2WindowsPhone " + ucBrowserVersion + " " + modelName + "---";
    }

    private static String buildSymbianPrefix(String userAgent, String ucBrowserVersion) {
        Matcher matcher = SYMBIAN.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        }
        String symbianVersion = "S60 V" + matcher.group(1);
        String modelName = matcher.group(2);
        return symbianVersion + " U2Symbian " + ucBrowserVersion + " " + modelName + "---";
    }

    private static String buildJavaPrefix(String userAgent, String ucBrowserVersion) {
        Matcher matcher = JAVA.matcher(userAgent);
        if (!matcher.find()) {
            return null;
        }
        String modelName = matcher.group(1);
        return "Java U2JavaApp " + ucBrowserVersion + " " + modelName + "---";
    }
}
