package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Maemo 设备的 User-Agent 特定规范化器。
 * <p>Maemo 是诺基亚 N900 等设备使用的 Linux 操作系统平台，
 * 该规范化器从 UA 中提取设备型号信息，构建统一格式的前缀以提高匹配准确性。</p>
 */

public class MaemoNormalizer implements UserAgentNormalizer {
    private static final Pattern MAEMO_BROWSER_MODEL_PATTERN = Pattern.compile("Maemo [bB]rowser [\\d\\.]+ (.+)");
    /**
     * 提取 Maemo 设备型号并格式化为 "{@code Maemo {model}---{originalUA}}"。
     * <p>如果无法匹配 Maemo 浏览器 UA 格式，则原样返回。</p>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        Matcher maemoMatcher;
        maemoMatcher = MAEMO_BROWSER_MODEL_PATTERN.matcher(userAgent);
        if (maemoMatcher.find()) {
            int modelEndIndex;
            String deviceModel;
            deviceModel = maemoMatcher.group(1);
            modelEndIndex = deviceModel.indexOf(" GTB");
            if (modelEndIndex == -1) {
                modelEndIndex = deviceModel.length();
            }

            return "Maemo " + deviceModel.substring(0, modelEndIndex) + "---" + userAgent;
        } else {
            return userAgent;
        }
    }
}
