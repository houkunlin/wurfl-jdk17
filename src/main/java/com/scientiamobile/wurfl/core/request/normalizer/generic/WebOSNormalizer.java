package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * webOS 设备的 User-Agent 规范化器。
 * <p>webOS（包括 HP webOS 和 LG webOS）设备的 UA 中包含设备型号和应用信息。
 * 该规范化器提取主版本号和应用名称版本号，组合成前缀附加到原始 UA 之前，
 * 使得同一 webOS 设备的 UA 能够被准确匹配。</p>
 */
public class WebOSNormalizer implements UserAgentNormalizer {
    /**
     * 匹配 UA 末尾的应用名称和版本号模式，如 " Chrome/12.0"。
     */
    private static final Pattern TRAILING_APP_NAME_AND_VERSION_PATTERN = Pattern.compile(" ([^/]+)/([\\d\\.]+)$");
    /**
     * 匹配 webOS 主版本号模式，如 "hpwOS/3." 或 "webOS/1."。
     */
    private static final Pattern WEBOS_MAJOR_VERSION_PATTERN = Pattern.compile("(?:hpw|web)OS.(\\d)\\.");

    @Override
    /**
     * 规范化 webOS 设备的 User-Agent：
     * <ol>
     *   <li>提取 webOS 主版本号</li>
     *   <li>提取末尾的应用名称和版本号</li>
     *   <li>将应用名称版本号和 webOS 版本号组合为前缀</li>
     * </ol>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        Matcher matcher;
        matcher = WEBOS_MAJOR_VERSION_PATTERN.matcher(userAgent);
        String webOsToken = matcher.find() ? "webOS".concat(matcher.group(1)) : null;
        matcher = TRAILING_APP_NAME_AND_VERSION_PATTERN.matcher(userAgent);
        String appNameAndVersion = matcher.find() ? matcher.group(1) + " " + matcher.group(2) : null;
        return webOsToken != null && appNameAndVersion != null ? appNameAndVersion + " " + webOsToken + "---" + userAgent : userAgent;
    }
}
