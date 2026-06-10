package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Factory for creating WURFL Request instances.
 */

public interface WURFLRequestFactory {
    /**
     * 从 HttpServletRequest 创建 WURFL 请求对象。
     * <p>自动提取请求中的 User-Agent 和 UAProfile 等信息，适用于 Web 应用场景。</p>
     *
     * @param request      Servlet HTTP 请求对象，不能为 null
     * @param engineTarget 引擎匹配目标
     * @return 封装后的 WURFL 请求对象
     */
    WURFLRequest createRequest(HttpServletRequest request, EngineTarget engineTarget);

    /**
     * 从 User-Agent 字符串创建 WURFL 请求对象。
     * <p>适用于纯 UA 字符串分析的场景，不依赖 Servlet 容器。</p>
     *
     * @param userAgent    User-Agent 字符串
     * @param engineTarget 引擎匹配目标
     * @return 封装后的 WURFL 请求对象
     */
    WURFLRequest createRequest(String userAgent, EngineTarget engineTarget);

    /**
     * 从自定义的 Header 提供者创建 WURFL 请求对象。
     * <p>适用于非 Servlet 环境或需要自定义请求头的场景。</p>
     *
     * @param headerProvider 请求头提供者
     * @param engineTarget   引擎匹配目标
     * @return 封装后的 WURFL 请求对象
     */
    WURFLRequest createRequest(WURFLHeaderProvider headerProvider, EngineTarget engineTarget);
}
