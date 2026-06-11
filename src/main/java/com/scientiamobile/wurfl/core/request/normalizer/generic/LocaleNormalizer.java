package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * User-Agent 语言区域信息规范化器。
 * <p>将 UA 中的语言区域标记（如 "en-US"、"zh-CN"、"fr" 等）统一替换为 "{@code ; xx-xx}"，
 * 消除因设备语言设置不同导致的 UA 差异，提高匹配一致性。</p>
 */

public class LocaleNormalizer implements UserAgentNormalizer {
    private static final Pattern LOCALE_PATTERN = Pattern.compile("; ?[a-z]{2}(?:-r?[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b-?(?!:)");

    /**
     * 将 User-Agent 中的语言区域标记统一替换为 "{@code ; xx-xx}"。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 语言区域标准化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return LOCALE_PATTERN.matcher(userAgent).replaceAll("; xx-xx");
    }
}
