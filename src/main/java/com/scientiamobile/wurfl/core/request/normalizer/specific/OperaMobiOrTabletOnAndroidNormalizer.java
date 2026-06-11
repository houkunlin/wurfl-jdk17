package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

/**
 * Android 平台上的 Opera Mobile 或 Opera Tablet 的 User-Agent 特定规范化器。
 * <p>提取 Opera 类型（Mobile/Tablet）、Opera 版本号和 Android 版本号，
 * 组合为规范化的前缀，使同一 Android 设备的 Opera UA 在不同版本下具有一致的可匹配格式。</p>
 */
public class OperaMobiOrTabletOnAndroidNormalizer implements UserAgentNormalizer {
    /**
     * 构建 Opera Mobile/Tablet 的规范化前缀：
     * <ol>
     *   <li>判断是 "Opera Mobi" 还是 "Opera Tablet"</li>
     *   <li>提取 Opera for Android 版本号和 Android 版本号</li>
     *   <li>组合为 "{OperaType} {OperaVersion} Android {AndroidVersion}---{originalUA}"</li>
     * </ol>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        StringBuilder normalizedUaBuilder;
        normalizedUaBuilder = new StringBuilder();
        normalizedUaBuilder.append(userAgent.contains("Opera Mobi") ? "Opera Mobi" : "Opera Tablet").append(" ");
        String operaOrAndroidVersion;
        operaOrAndroidVersion = UserAgentUtils.getOperaOnAndroidVersion(userAgent, false);
        if (operaOrAndroidVersion != null) {
            normalizedUaBuilder.append(operaOrAndroidVersion).append(" ");
        }

        normalizedUaBuilder.append("Android");
        operaOrAndroidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
        if (operaOrAndroidVersion != null) {
            normalizedUaBuilder.append(" ").append(operaOrAndroidVersion);
        }

        normalizedUaBuilder.append("---").append(userAgent);
        return normalizedUaBuilder.toString();
    }
}
