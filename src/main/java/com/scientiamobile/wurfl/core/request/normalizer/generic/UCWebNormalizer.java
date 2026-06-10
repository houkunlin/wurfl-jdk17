package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.regex.Pattern;

/**
 * Normalizes User-Agent strings for UC Web.
 */

public class UCWebNormalizer implements UserAgentNormalizer {
    private static final Pattern JUC_ANDROID_VERSION_PATTERN = Pattern.compile("^(JUC \\(Linux; U;)(?= \\d)");
    private static final Pattern MISSING_SPACE_PATTERN = Pattern.compile("(Android|JUC|[;\\)])(?=[\\w|\\(])");

    @Override
    /**
     * 修正 UC 浏览器 UA 的格式问题：
     * <ol>
     *   <li>如果 UA 以 "JUC" 或 "Mozilla/5.0(Linux;U;Android" 开头，执行修正</li>
     *   <li>在 Android 版本号前补充 "Android" 标识（如果缺失）</li>
     *   <li>在关键字和括号后插入缺失的空格</li>
     * </ol>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 格式修正后的 User-Agent 字符串
     */
    public String normalize(String userAgent) {
        if (StringMatchUtils.startsWithAnyOf(userAgent, "JUC", "Mozilla/5.0(Linux;U;Android")) {
            userAgent = JUC_ANDROID_VERSION_PATTERN.matcher(userAgent).replaceFirst("$1 Android");
            userAgent = MISSING_SPACE_PATTERN.matcher(userAgent).replaceAll("$1 ");
        }

        return userAgent;
    }
}
