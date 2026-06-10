package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Pattern;

/**
 * BlackBerry 设备的 User-Agent 规范化器。
 * <p>BlackBerry 设备在不同的系统版本或浏览器中，其 UA 字符串中的 "BlackBerry" 标识
 * 大小写不一致（如 "blackberry"、"BlackBerry" 混用）。该规范化器统一修正为
 * "BlackBerry" 标准写法，并截取从 "BlackBerry" 开始的部分，移除无关前缀。</p>
 */
public class BlackBerryNormalizer implements UserAgentNormalizer {
    /**
     * 匹配不区分大小写的 "blackberry" 标识的模式。
     */
    private static final Pattern BLACKBERRY_PATTERN = Pattern.compile("(?i)black(?i)berry");

    @Override
    public String normalize(String userAgent) {
        int blackBerryIndex;
        userAgent = BLACKBERRY_PATTERN.matcher(userAgent).replaceAll("BlackBerry");
        blackBerryIndex = userAgent.indexOf("BlackBerry");
        if (blackBerryIndex > 0) {
            userAgent = userAgent.substring(blackBerryIndex);
        }

        return userAgent;
    }
}
