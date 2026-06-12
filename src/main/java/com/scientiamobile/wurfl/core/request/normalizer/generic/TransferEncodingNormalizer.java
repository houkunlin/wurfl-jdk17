package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * 移除传输编码标记的 User-Agent 规范化器。
 * <p>部分代理服务器或网关会在 UA 字符串末尾追加传输编码标记 {@code ,gzip(gfe)}，
 * 该标记对设备识别无意义且会造成匹配差异，因此将其移除。</p>
 */
public class TransferEncodingNormalizer implements UserAgentNormalizer {
    /**
     * 需要移除的传输编码标记。
     */
    private static final CharSequence TRANSFER_ENCODING_TOKEN = ",gzip(gfe)";

    /**
     * 移除 User-Agent 末尾的传输编码标记 "{@code ,gzip(gfe)}"。
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 移除标记后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        return userAgent.replace(TRANSFER_ENCODING_TOKEN, "");
    }
}
