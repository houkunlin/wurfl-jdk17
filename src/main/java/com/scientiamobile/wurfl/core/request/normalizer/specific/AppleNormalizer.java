package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Apple iOS 设备的 User-Agent 特定规范化器。
 * <p>识别多种 iOS 原生应用的 UA 格式（如 iOS 应用 UA、server-bag、iOS 客户端 SDK 等），
 * 将其转换为标准 Mobile Safari UA 格式，并修正 "CPU iOS" 为 "CPU iPhone OS" 或 "CPU OS"，
 * 使 iOS 设备的 UA 在系统版本和设备信息部分保持一致。</p>
 */

public class AppleNormalizer implements UserAgentNormalizer {
    private static final Pattern IOS_APP_UA_PATTERN = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
    private static final Pattern SERVER_BAG_PATTERN = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
    private static final Pattern IOS_DEVICE_MODEL_VERSION_PATTERN = Pattern.compile("^i(?:Phone|Pad|Pod)\\d+?,\\d+?/([\\d\\.]+)");
    private static final Pattern IOS_DEVICE_MODEL_IOS_VERSION_PATTERN = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
    private static final Pattern IOS_CLIENT_SDK_PATTERN = Pattern.compile("^iOSClientSDK/\\d+\\.+[0-9\\.]+ +?\\((Mozilla.+)\\)$");
    private static final Pattern CPU_IOS_PATTERN = Pattern.compile("CPU iOS \\d+?\\.\\d+?");
    private static final Pattern CPU_OS_LIKE_PATTERN = Pattern.compile("(CPU(?: iPhone)? OS [\\d\\.]+ like)");

    private static final Pattern[] IOS_VERSION_PATTERNS = {
            IOS_APP_UA_PATTERN,
            SERVER_BAG_PATTERN,
            IOS_DEVICE_MODEL_VERSION_PATTERN,
            IOS_DEVICE_MODEL_IOS_VERSION_PATTERN,
    };

    /**
     * 在 User-Agent 中查找匹配指定正则模式的第一个结果。
     *
     * @param userAgent User-Agent 字符串
     * @param pattern   正则表达式模式
     * @return 匹配的 Matcher 对象，如果未匹配则返回 null
     */

    private static Matcher findMatcher(String userAgent, Pattern pattern) {
        if (userAgent != null && pattern != null) {
            Matcher matcher = pattern.matcher(userAgent);
            return matcher.find() ? matcher : null;
        } else {
            return null;
        }
    }

    /**
     * 从 User-Agent 中匹配 iOS 版本号。
     * <p>依次尝试多个预定义的 iOS UA 模式，返回第一个匹配到的版本号，
     * 并将点号替换为下划线（标准 Mobile Safari UA 中的格式）。</p>
     *
     * @param userAgent User-Agent 字符串
     * @return iOS 版本号（使用下划线分隔），如果无法匹配则返回 null
     */
    private static String matchIOSVersion(String userAgent) {
        for (Pattern pattern : IOS_VERSION_PATTERNS) {
            Matcher matcher = findMatcher(userAgent, pattern);
            if (matcher != null) {
                return matcher.group(1).replace(".", "_");
            }
        }
        return null;
    }

    /**
     * 根据 iOS 版本号和设备类型构建标准 Mobile Safari UA 字符串。
     * <p>根据 UA 中包含的设备标识（iPad、iPod touch、iPod、iPhone）选择对应的模板。</p>
     *
     * @param userAgent  User-Agent 字符串
     * @param iosVersion iOS 版本号（使用下划线分隔）
     * @return 标准化的 Mobile Safari UA 字符串
     */

    private static String buildIOSUA(String userAgent, String iosVersion) {
        if (userAgent.contains("iPad")) {
            return "Mozilla/5.0 (iPad; CPU OS " + iosVersion + " like Mac OS X) AppleWebKit/538.39.2 (KHTML, like Gecko) Version/7.0 Mobile/12A4297e Safari/9537.53 " + userAgent;
        }
        if (userAgent.contains("iPod touch")) {
            return "Mozilla/5.0 (iPod touch; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 " + userAgent;
        }
        if (userAgent.contains("iPod")) {
            return "Mozilla/5.0 (iPod; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 " + userAgent;
        }
        return "Mozilla/5.0 (iPhone; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/601.1.10 (KHTML, like Gecko) Version/8.0 Mobile/12E155 Safari/600.1.4 " + userAgent;
    }

    /**
     * 规范化 Apple iOS 设备的 User-Agent。
     * <p>处理流程：</p>
     * <ol>
     *   <li>尝试匹配多种 iOS 原生应用 UA 格式并构建标准 Safari UA</li>
     *   <li>匹配 iOS 客户端 SDK UA 格式，提取内部的浏览器 UA</li>
     *   <li>修正 "CPU iOS" 为 "CPU iPhone OS" 或 "CPU OS"（iPad），标准化操作系统标记</li>
     * </ol>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        String iosVersion = matchIOSVersion(userAgent);
        if (iosVersion != null) {
            return buildIOSUA(userAgent, iosVersion);
        }
        if (userAgent != null) {
            Matcher iosClientSdkMatcher = IOS_CLIENT_SDK_PATTERN.matcher(userAgent);
            if (iosClientSdkMatcher.matches()) {
                return iosClientSdkMatcher.group(1);
            }
            if (findMatcher(userAgent, CPU_IOS_PATTERN) != null) {
                String rewrittenUserAgent = userAgent.contains("iPad")
                        ? userAgent.replace("CPU iOS", "CPU OS")
                        : userAgent.replace("CPU iOS", "CPU iPhone OS");
                Matcher versionMatcher = findMatcher(rewrittenUserAgent, CPU_OS_LIKE_PATTERN);
                if (versionMatcher != null) {
                    String cpuOsLike = versionMatcher.group(1).replace(".", "_");
                    return rewrittenUserAgent.replace(" U;", "").replaceAll("CPU(?: iPhone)? OS ([\\d\\.]+) like", cpuOsLike);
                }
            }
        }
        return userAgent;
    }
}
