package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Thunderbird 邮件客户端的 User-Agent 特定规范化器。
 * <p>截取 UA 字符串中从 "Thunderbird" 关键字开始的部分，移除操作系统等无关信息，
 * 使同一 Thunderbird 版本号的 UA 具有更高的一致性。</p>
 */
public class ThunderbirdNormalizer implements UserAgentNormalizer {
    @Override
    /**
     * 截取 User-Agent 中从 "Thunderbird" 关键字开始的部分。
     * <p>如果原始 UA 中不包含 "Thunderbird"，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 截取后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        int thunderbirdIndex = StringMatchUtils.indexOf(userAgent, "Thunderbird");
        return thunderbirdIndex >= 0 ? StringUtils.substring(userAgent, thunderbirdIndex) : userAgent;
    }
}
