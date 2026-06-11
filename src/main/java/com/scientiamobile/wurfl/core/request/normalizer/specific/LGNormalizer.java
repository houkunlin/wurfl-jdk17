package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * LG 设备的 User-Agent 特定规范化器。
 * <p>LG 手机的 UA 中通常以 "LG" 开头包含设备型号信息，
 * 该规范化器截取从 "LG" 关键字开始的部分，确保 LG 设备的型号信息
 * 在 UA 中占据主导地位，提高匹配准确性。</p>
 */
public class LGNormalizer implements UserAgentNormalizer {
    /**
     * 截取 User-Agent 中从 "LG" 关键字开始的部分。
     * <p>如果 "LG" 出现在开头或不存在于 UA 中，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 截取后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        int lgIndex;
        lgIndex = userAgent.indexOf("LG");
        return lgIndex > 0 ? userAgent.substring(lgIndex) : userAgent;
    }
}
