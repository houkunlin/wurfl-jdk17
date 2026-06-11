package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTC 设备的 User-Agent 规范化器。
 * <p>HTC 设备在 UA 中通常会携带其设备型号信息（如 {@code HTC One_M8}），
 * 该规范化器提取型号信息并将其中的分隔符统一替换为波浪号，前置到 UA 字符串之前，
 * 使得同一 HTC 设备型号的 UA 能够被准确匹配。同时标准化语言区域信息。</p>
 */
public class HTCMacNormalizer implements UserAgentNormalizer {
    /**
     * 匹配并替换语言区域信息的模式，统一替换为 "; xx-xx"。
     */
    private static final Pattern LOCALE_SUBSTITUTION_PATTERN = Pattern.compile("; [a-z]{2}(?:-[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b ");
    /**
     * 匹配 HTC 设备型号的模式，匹配以 "HTC" 开头直到分号或右括号之前的内容。
     */
    private static final Pattern HTC_MODEL_PATTERN = Pattern.compile("(HTC[^;\\)]+)");

    /**
     * 规范化给定的 User-Agent 字符串。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        userAgent = LOCALE_SUBSTITUTION_PATTERN.matcher(userAgent).replaceFirst("; xx-xx");
        Matcher htcModelMatcher;
        htcModelMatcher = HTC_MODEL_PATTERN.matcher(userAgent);
        if (htcModelMatcher.find()) {
            String htcModel = htcModelMatcher.group();
            return htcModel.replaceAll("[ _\\-/]", "~") + "---" + userAgent;
        } else {
            return userAgent;
        }
    }
}
