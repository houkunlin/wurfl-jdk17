package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * Android 通用 User-Agent 规范化器。
 * <p>将 Android 版本号精简为统一的 "主版本.次版本" 格式，
 * 消除同一 Android 版本因不同子版本号（如 4.4.2 vs 4.4）导致的匹配差异。</p>
 */

public class GenericAndroidNormalizer implements UserAgentNormalizer {
    private static final Pattern ANDROID_VERSION_PATTERN = Pattern.compile("Android[ \\-\\/](\\d\\.\\d)[^; \\/\\)]+");
    /**
     * 将 Android 版本号精简为 "主版本.次版本" 格式。
     * <p>例如：{@code Android 4.4.2} → {@code Android 4.4}，{@code Android 5.0-lollipop} → {@code Android 5.0}。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 版本号精简后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return ANDROID_VERSION_PATTERN.matcher(userAgent).replaceAll("Android $1");
    }
}
