package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Chrome 浏览器的 User-Agent 特定规范化器。
 * <p>截取 UA 字符串中从 "Chrome" 关键字开始的部分，移除浏览器标识之前
 * 的操作系统和设备描述信息，使同一 Chrome 版本号的 UA 具有更高的一致性。</p>
 */

public class ChromeNormalizer implements UserAgentNormalizer {
    /**
     * 截取 User-Agent 中从 "Chrome" 关键字开始的部分。
     * <p>如果原始 UA 中不包含 "Chrome"，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 截取后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        int chromeIndex;
        chromeIndex = userAgent.indexOf("Chrome");
        if (chromeIndex >= 0) {
            userAgent = userAgent.substring(chromeIndex);
        }

        return userAgent;
    }
}
