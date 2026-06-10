package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Normalizes User-Agent strings for Encription Level.
 */

public class EncriptionLevelNormalizer implements UserAgentNormalizer {
    private static final CharSequence ENCRIPTION_LEVEL_TOKEN = new String(" U;");

    @Override
    /**
     * 移除 User-Agent 中的加密级别标记 "{@code  U;}"。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 移除加密级别标记后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        return userAgent.replace(ENCRIPTION_LEVEL_TOKEN, "");
    }
}
