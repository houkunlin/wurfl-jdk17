package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

/**
 * User-Agent 解析器的快速实现，直接从请求头中读取 "User-Agent" 值。
 * <p>不做任何额外处理或编码转换，适用于对性能要求较高且不需要复杂解析逻辑的场景。</p>
 */

public final class FastUserAgentResolver implements UserAgentResolver {
    /**
     * 直接从请求中读取 "User-Agent" 请求头的值，不做任何额外处理。
     *
     * @param request Servlet HTTP 请求对象
     * @return User-Agent 字符串，如果请求头不存在则返回 null
     */
    public final String resolve(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
