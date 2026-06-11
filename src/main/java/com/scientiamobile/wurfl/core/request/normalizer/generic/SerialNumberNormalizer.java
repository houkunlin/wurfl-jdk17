package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * User-Agent 序列号掩码规范化器。
 * <p>将 UA 中可能包含的设备序列号（SN、ST、TF、NT 等格式）替换为固定长度的掩码字符串，
 * 避免因序列号不同导致的同一型号设备匹配失败，同时也保护设备隐私。</p>
 */

public class SerialNumberNormalizer implements UserAgentNormalizer {
    private static final Pattern SN_PATTERN = Pattern.compile("/SN[\\dX]+");
    private static final Pattern ST_TF_NT_PATTERN = Pattern.compile("\\[(ST|TF|NT)[\\dX]+\\]");

    /**
     * 将 User-Agent 中的序列号替换为固定掩码字符串：
     * <ul>
     *   <li>{@code /SN1234567890X} → {@code /SNXXXXXXXXXXXXXXX}</li>
     *   <li>{@code [TF1234567890X]} → {@code TFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX}</li>
     * </ul>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 序列号被掩码后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        userAgent = SN_PATTERN.matcher(userAgent).replaceAll("/SNXXXXXXXXXXXXXXX");
        return ST_TF_NT_PATTERN.matcher(userAgent).replaceAll("TFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
