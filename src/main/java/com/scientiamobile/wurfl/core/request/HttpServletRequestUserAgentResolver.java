package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

/**
 * Implementation of HTTP Servlet Request User Agent Resolver.
 */

final class HttpServletRequestUserAgentResolver implements UserAgentResolver {
    /**
     * 从 HttpServletRequest 中解析 User-Agent 字符串。
     * <p>内部使用 {@link UserAgentUtils#getUserAgent(HttpServletRequest)}，
     * 该方法会依次检查多个候选请求头并处理编码问题。</p>
     *
     * @param request Servlet HTTP 请求对象，不能为 null
     * @return 解析得到的 User-Agent 字符串
     * @throws NullPointerException 如果 request 为 null
     */
    public final String resolve(HttpServletRequest request) {
        Validate.notNull(request, "The HttpServletRequest is null");
        return UserAgentUtils.getUserAgent(request);
    }
}

