package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Opera 浏览器的 User-Agent 特定规范化器。
 * <p>支持两种 Opera 内核的规范化：</p>
 * <ul>
 *   <li>古典 Opera（Presto 内核）：将 "Opera/9.80 ... Version/N.M" 替换为 "Opera/N.M ..."</li>
 *   <li>Chromium Opera（Blink 内核）：提取 "OPR/N.M" 并格式化为 "Opera/N.M" 前置</li>
 * </ul>
 */

public class OperaNormalizer implements UserAgentNormalizer {
    private static final Pattern OPERA_VERSION_PATTERN = Pattern.compile("Version/(\\d+\\.\\d+)");
    private static final Pattern OPERA_CHROMIUM_VERSION_PATTERN = Pattern.compile("OPR/(\\d+\\.\\d+)");

    /**
     * 规范化 Opera 浏览器的 User-Agent。
     * <p>古典 Opera：将 {@code Opera/9.80 ... Version/N.M} 替换为 {@code Opera/N.M ...}。</p>
     * <p>Chromium Opera：提取 {@code OPR/N.M} 并格式化为 {@code Opera/N.M} 前置。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        if (userAgent.startsWith("Opera/9.80")) {
            Matcher versionMatcher = OPERA_VERSION_PATTERN.matcher(userAgent);
            if (versionMatcher.find()) {
                return userAgent.replace("Opera/9.80", "Opera/" + versionMatcher.group(1));
            }
        }
        Matcher versionMatcher = OPERA_CHROMIUM_VERSION_PATTERN.matcher(userAgent);
        return versionMatcher.find() ? "Opera/" + versionMatcher.group(1) + " " + userAgent : userAgent;
    }
}
