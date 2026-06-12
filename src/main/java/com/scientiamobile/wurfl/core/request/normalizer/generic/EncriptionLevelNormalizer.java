package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * User-Agent 加密级别标记规范化器。
 * <p>移除 UA 字符串中的加密级别标记 "{@code  U;}"，该标记对设备识别无意义，
 * 且不同运营商或代理服务器可能附加不同的值，移除后可以提高匹配一致性。</p>
 */

public class EncriptionLevelNormalizer implements UserAgentNormalizer {
    private static final CharSequence ENCRIPTION_LEVEL_TOKEN = " U;";

    /**
     * 移除 User-Agent 中的加密级别标记 "{@code  U;}"。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 移除加密级别标记后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return userAgent.replace(ENCRIPTION_LEVEL_TOKEN, "");
    }
}
