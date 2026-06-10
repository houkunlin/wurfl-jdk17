package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * 移除 User-Agent 字符串中所有非 ASCII 可打印字符的规范化器。
 * <p>WURFL 引擎的设备匹配基于 ASCII 字符集，非 ASCII 字符可能导致匹配失败。
 * 该规范化器使用正则表达式 {@code [^ -~]+} 匹配并移除所有非可打印 ASCII 字符
 *（即可打印 ASCII 范围 0x20-0x7E 之外的字符），确保 UA 字符串符合 WURFL 匹配要求。</p>
 */
public class AsciiNormalizer implements UserAgentNormalizer {
    /**
     * 匹配非 ASCII 可打印字符的正则表达式（排除空格到波浪号范围外的所有字符）。
     */
    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^ -~]+");

    @Override
    public String normalize(String userAgent) {
        return NON_ASCII_PATTERN.matcher(userAgent).replaceAll("");
    }
}
