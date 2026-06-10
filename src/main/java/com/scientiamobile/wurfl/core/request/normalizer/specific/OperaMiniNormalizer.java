package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Opera Mini 浏览器的 User-Agent 特定规范化器。
 * <p>Opera Mini 的 UA 中包含屏幕分辨率信息（如 {@code 240X320}），
 * 该规范化器提取分辨率之后的 UA 部分，将其与原始 UA 组合，
 * 实现分辨率信息前置以便 WURFL 引擎优先以分辨率作为匹配依据。</p>
 */
public class OperaMiniNormalizer implements UserAgentNormalizer {
    /**
     * 匹配 Opera Mini UA 中分辨率标记之后的内容。
     */
    private static final Pattern OPERA_MINI_RES_PATTERN = Pattern.compile("^Opera/[\\d\\.]+ .+?\\d{3}X\\d{3} (.+)$");

    @Override
    public String normalize(String userAgent) {
        Matcher operaMiniMatcher;
        operaMiniMatcher = OPERA_MINI_RES_PATTERN.matcher(userAgent);
        return operaMiniMatcher.matches() ? operaMiniMatcher.group(1) + "---" + userAgent : userAgent;
    }
}
