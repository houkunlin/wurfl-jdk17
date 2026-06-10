package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;

/**
 * WURFL 请求头提供者接口，抽象了请求头的来源。
 * <p>该接口允许 WURFL 框架不依赖于具体的 Servlet 容器，
 * 可以从任何数据源（如 HttpServletRequest、Map 等）获取请求头信息，
 * 增强了框架在不同运行环境下的可移植性。</p>
 */
public interface WURFLHeaderProvider {
    /**
     * 根据请求头名称获取对应的值。
     *
     * @param headerName 请求头名称
     * @return 请求头的值，若不存在则返回 null
     */
    String getHeader(String headerName);

    /**
     * 获取所有请求头名称的枚举。
     *
     * @return 请求头名称的枚举
     */
    Enumeration<String> getHeaderNames();
}
