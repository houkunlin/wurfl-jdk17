package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CFNetwork 库的 User-Agent 规范化器。
 * <p>CFNetwork 是苹果操作系统中的底层网络库，其版本号在不同请求中精度不一致。
 * 该规范化器统一将 CFNetwork 版本号格式化为保留两位小数的标准形式，并根据
 * 架构信息添加 "CFNetworkDesktop/" 或 "CFNetwork/" 前缀，使同一 iOS/macOS
 * 设备的 UA 在版本号部分保持一致，提高匹配准确率。</p>
 */
public class CFNetworkNormalizer implements UserAgentNormalizer {
    /**
     * 匹配 CFNetwork 版本号的正则表达式。
     */
    private static final Pattern CFNETWORK_VERSION_PATTERN = Pattern.compile("CFNetwork/(\\d+\\.?[0-9]*)");

    /**
     * 规范化 CFNetwork 标识：
     * <ol>
     *   <li>提取 CFNetwork 版本号，统一格式化为两位小数</li>
     *   <li>根据是否包含 x86_64 架构判断是否为桌面版，添加相应前缀</li>
     *   <li>将格式化的前缀附加到原始 UA 之前</li>
     * </ol>
     *
     * @param userAgent 原始 User-Agent 字符串
     * @return 规范化后的 User-Agent 字符串
     */
    @Override
    public String normalize(String userAgent) {
        Matcher cfNetworkMatcher;
        cfNetworkMatcher = CFNETWORK_VERSION_PATTERN.matcher(userAgent);
        if (cfNetworkMatcher.find()) {
            String cfNetworkVersionNormalized = (new BigDecimal(cfNetworkMatcher.group(1))).setScale(2, RoundingMode.HALF_DOWN).toString();
            StringBuilder normalizedUaBuilder = new StringBuilder();
            if (userAgent.contains("x86_64")) {
                normalizedUaBuilder.append("CFNetworkDesktop/").append(cfNetworkVersionNormalized).append(" ").append(userAgent);
            } else {
                normalizedUaBuilder.append("CFNetwork/").append(cfNetworkVersionNormalized).append(" ").append(userAgent);
            }

            return normalizedUaBuilder.toString();
        } else {
            return userAgent;
        }
    }
}
