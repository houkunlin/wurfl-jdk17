package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

/**
 * 基于 {@link HttpServletRequest} 的请求头提供者实现。
 * <p>将 Servlet 容器的请求头 API 适配为 {@link WURFLHeaderProvider} 接口，
 * 使得 WURFL 框架可以在 Web 应用环境中无缝集成。</p>
 */
public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
    /**
     * 底层的 Servlet HTTP 请求对象。
     */
    private final HttpServletRequest request;

    /**
     * 使用给定的 Servlet 请求对象构造实例。
     *
     * @param request Servlet HTTP 请求对象
     */
    public HttpServletRequestHeaderProvider(HttpServletRequest request) {
        this.request = request;
    }
    /**
     * 从 Servlet 请求中获取指定请求头的值。
     *
     * @param headerName 请求头名称
     * @return 请求头的值，若不存在则返回 null
     */
    @Override
    public String getHeader(String headerName) {
        return this.request.getHeader(headerName);
    }
    /**
     * 获取 Servlet 请求中所有请求头名称的枚举。
     *
     * @return 请求头名称的枚举
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return this.request.getHeaderNames();
    }
}
