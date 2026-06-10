package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

/**
 * Normalizes User-Agent strings for Safari.
 */

public class SafariNormalizer implements UserAgentNormalizer {
    @Override
    /**
     * 提取 Safari 浏览器的主版本号，构建规范化前缀。
     * <p>如果无法找到 "Version/" 标记或无法解析主版本号，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        int versionTokenStart;
        versionTokenStart = userAgent.indexOf("Version/");
        if (versionTokenStart != -1) {
            versionTokenStart += 8;
            int majorVersionDotIndex;
            majorVersionDotIndex = userAgent.indexOf(46, versionTokenStart);
            if (majorVersionDotIndex != -1) {
                return "Safari " + userAgent.substring(versionTokenStart, majorVersionDotIndex) + "---" + userAgent;
            }
        }

        return userAgent;
    }
}
