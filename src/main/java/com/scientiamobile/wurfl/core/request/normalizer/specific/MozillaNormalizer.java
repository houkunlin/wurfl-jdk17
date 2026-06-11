package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * Mozilla 标识的 User-Agent 特定规范化器。
 * <p>移除 "Mozilla/" 前缀后面紧跟着的语言区域标记，
 * 使得相同浏览器但不同语言设置的 UA 在匹配时具有一致性。</p>
 */

public class MozillaNormalizer implements UserAgentNormalizer {
    private static final Pattern LOCALE_PATTERN = Pattern.compile("(; [a-z]{2}(-[a-zA-Z]{0,2})?)");

    /**
     * 如果 UA 以 "Mozilla/" 开头，移除紧随其后的语言区域标记。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 移除语言区域标记后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return userAgent.startsWith("Mozilla/") ? LOCALE_PATTERN.matcher(userAgent).replaceFirst("") : userAgent;
    }
}
