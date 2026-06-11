package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断请求是否来自原生 App（而非浏览器）的虚拟能力评估器。
 * <p>通过分析 User-Agent 字符串的特征来判断请求源自原生移动应用而非浏览器。
 * 检测策略包括：排除已知的浏览器关键词、检查 Android WebView 标识（{@code ; wv) }）、
 * 检测 iOS/macOS 非 Safari 请求、检查 {@code X-Requested-With} 头中的 App 包名、
 * 以及匹配 App 特征关键词列表。</p>
 */

public class IsApp extends AbstractVirtualCapabilityEvaluator {

    /**
     * 匹配 Android 应用 User-Agent 中 "Mozilla/5.0 (Linux; Android " 前缀模式
     */
    static final Pattern ANDROID_UA_PREFIX_PATTERN = Pattern.compile("^.+Mozilla/5.0 \\(Linux; Android ");

    /**
     * 匹配 Android Safari 应用的末尾后缀模式
     */
    static final Pattern ANDROID_SAFARI_SUFFIX_PATTERN = Pattern.compile(" (?:Mobile )?Safari/[\\d\\.+]+[^\\d\\.+]+");

    @Serial
    private static final long serialVersionUID = -2020126634302389944L;

    /**
     * 支持哈希前缀的 App 特征模式映射（将 "#" 开头的指示符映射为对应的 Pattern）
     */
    private static final Map<String, Pattern> HASH_APP_INDICATOR_PATTERNS = new HashMap<>(3);

    /**
     * 匹配 iOS 设备标识模式，如 "iPhone3,1"、"iPad2,5"
     */
    private static final Pattern IOS_DEVICE_SIGNATURE_PATTERN = Pattern.compile("iP(hone|od|ad)[\\d],[\\d]");

    /**
     * 匹配 Java 包名模式，如 "com.example.app"
     */
    private static final Pattern JAVA_PACKAGE_SIGNATURE_PATTERN = Pattern.compile("com(?:\\.[a-z]+){2,}");

    /**
     * 匹配 .NET 包名模式，如 "net.example.app"
     */
    private static final Pattern DOT_NET_PACKAGE_SIGNATURE_PATTERN = Pattern.compile("net(?:\\.[a-z]+){2,}");

    static {
        HASH_APP_INDICATOR_PATTERNS.put("#iP(hone|od|ad)[\\d],[\\d]", IOS_DEVICE_SIGNATURE_PATTERN);
        HASH_APP_INDICATOR_PATTERNS.put("#com(?:\\.[a-z]+){2,}", JAVA_PACKAGE_SIGNATURE_PATTERN);
        HASH_APP_INDICATOR_PATTERNS.put("#net(?:\\.[a-z]+){2,}", DOT_NET_PACKAGE_SIGNATURE_PATTERN);
    }

    /**
     * 尝试将字符串解析为整数，解析失败时返回 -1。
     *
     * @param value 待解析的字符串
     * @return 整数值，失败时返回 -1
     */
    private static int parseIntOrMinusOne(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public String eval(Device device, WURFLRequest request) {
        String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();
        if (StringMatchUtils.containsAnyOf(userAgent, NON_APP_BROWSER_KEYWORDS.toArray(new String[0]))) {
            return "false";
        } else {
            String deviceOs = device.getCapability("device_os");
            if ("Android".equals(deviceOs) && userAgent.contains("; wv) ")) {
                return "true";
            } else if (!isIosNonSafari(deviceOs, userAgent) && !isMacOsNonSafari(device, userAgent, request)) {
                if (isRequestedWithAppPackage("Android", deviceOs, request)) {
                    return "true";
                } else {
                    if (ANDROID_WEBKIT_KHTML_PATTERN.matcher(userAgent).find()) {
                        Matcher androidUaPrefixMatcher = ANDROID_UA_PREFIX_PATTERN.matcher(userAgent);
                        Matcher androidSafariSuffixMatcher = ANDROID_SAFARI_SUFFIX_PATTERN.matcher(userAgent);
                        if (androidUaPrefixMatcher.find() || androidSafariSuffixMatcher.find()) {
                            Matcher chromeVersionMatcher;
                            chromeVersionMatcher = CHROME_MAJOR_VERSION_PATTERN.matcher(userAgent);
                            return chromeVersionMatcher.find() && parseIntOrMinusOne(chromeVersionMatcher.group(1)) < 30 ? "false" : "true";
                        }
                    }

                    for (String indicator : APP_INDICATOR_PATTERNS) {
                        if (indicator.startsWith("#")) {
                            if (HASH_APP_INDICATOR_PATTERNS.get(indicator).matcher(userAgent).find()) {
                                return "true";
                            }
                        } else {
                            int indicatorLength = indicator.length();
                            if (indicator.startsWith("^")) {
                                if (userAgent.startsWith(indicator.substring(1))) {
                                    return "true";
                                }
                            } else if (indicator.charAt(indicatorLength - 1) == '$') {
                                --indicatorLength;
                                if (userAgent.indexOf(indicator.substring(0, indicatorLength)) == userAgent.length() - indicatorLength) {
                                    return "true";
                                }
                            } else if (userAgent.contains(indicator)) {
                                return "true";
                            }
                        }
                    }

                    return "false";
                }
            } else {
                return "true";
            }
        }
    }

    @Override
    public String getHandledVirtualCapabilityName() {
        return "is_app";
    }
}
