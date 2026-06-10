package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Normalizes User-Agent strings for Chrome.
 */

public class ChromeNormalizer implements UserAgentNormalizer {
    @Override
    /**
     * 截取 User-Agent 中从 "Chrome" 关键字开始的部分。
     * <p>如果原始 UA 中不包含 "Chrome"，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 截取后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        int chromeIndex;
        chromeIndex = userAgent.indexOf("Chrome");
        if (chromeIndex >= 0) {
            userAgent = userAgent.substring(chromeIndex);
        }

        return userAgent;
    }
}
