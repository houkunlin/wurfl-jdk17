package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

/**
 * User-Agent 解析器接口，负责从 HTTP 请求中提取 User-Agent 字符串。
 * <p>不同的实现类可以采取不同的提取策略：简单的直接读取请求头，
 * 复杂的可能需要处理多种回退逻辑或编码转换。</p>
 */
public interface UserAgentResolver {
    String resolve(HttpServletRequest request);
}
