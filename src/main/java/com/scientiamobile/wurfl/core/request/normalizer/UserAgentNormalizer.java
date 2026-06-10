package com.scientiamobile.wurfl.core.request.normalizer;

/**
 * User-Agent 规范化器接口，定义将原始 User-Agent 字符串转换为标准化格式的规范。
 * <p>规范化的目的是消除同一设备不同请求中 UA 字符串的细微差异（如版本号格式、编码方式、
 * 不必要的标识等），使得 WURFL 引擎能够准确匹配到对应的设备描述。
 * 不同的实现类专注于处理特定类型或特定浏览器的 UA 规范化。</p>
 */
public interface UserAgentNormalizer {
    String normalize(String userAgent);
}
