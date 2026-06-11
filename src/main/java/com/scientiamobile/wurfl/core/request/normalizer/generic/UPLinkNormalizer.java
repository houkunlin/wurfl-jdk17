package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.Validate;

/**
 * UP.Link 代理 User-Agent 规范化器。
 * <p>部分运营商使用 UP.Link 代理服务器，会在原始 UA 后追加代理相关信息，
 * 该规范化器移除 "UP.Link" 标记及其之后的所有内容，还原真实的设备 UA。</p>
 */

public class UPLinkNormalizer implements UserAgentNormalizer {
    /**
     * 移除 User-Agent 中 "UP.Link" 标记及其之后的所有内容。
     *
     * @param userAgent 原始 User-Agent 字符串，不能为 null
     * @return 移除 UP.Link 标记后的 User-Agent 字符串
     * @throws NullPointerException 如果 userAgent 为 null
     */
    @Override
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
