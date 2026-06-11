package com.scientiamobile.wurfl.core.request;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

import java.util.Enumeration;
import java.util.Map;

/**
 * 基于 {@link java.util.Map} 的请求头提供者实现。
 * <p>将 Map 数据结构适配为 {@link WURFLHeaderProvider} 接口，
 * 适用于非 Servlet 环境（如单元测试、消息队列处理等）中需要模拟请求头的场景。</p>
 */

public class MapHeaderProvider implements WURFLHeaderProvider {
    private final Map<String, String> headers;

    public MapHeaderProvider(Map<String, String> headers) {
        this.headers = headers;
    }

    /**
     * 获取指定请求头的值。
     *
     * @param headerName 请求头名称
     * @return 请求头的值，若不存在则返回 null
     */
    @Override
    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    /**
     * 获取所有请求头名称的枚举。
     *
     * @return 请求头名称的枚举
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return new IteratorEnumeration<>(this.headers.keySet().iterator());
    }
}
