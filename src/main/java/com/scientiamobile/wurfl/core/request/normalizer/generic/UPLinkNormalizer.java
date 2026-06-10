package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.Validate;

/**
 * Normalizes User-Agent strings for UP Link.
 */

public class UPLinkNormalizer implements UserAgentNormalizer {
    @Override
    /**
     * 移除 User-Agent 中 "UP.Link" 标记及其之后的所有内容。
     *
     * @param userAgent 原始 User-Agent 字符串，不能为 null
     * @return 移除 UP.Link 标记后的 User-Agent 字符串
     * @throws NullPointerException 如果 userAgent 为 null
     */
    public String normalize(String userAgent) {
        Validate.notNull(userAgent, "The userAgent is null");
        int upLinkIndex;
        upLinkIndex = userAgent.indexOf("UP.Link");
        if (upLinkIndex >= 0) {
            userAgent = userAgent.substring(0, upLinkIndex);
        }

        return userAgent;
    }
}
