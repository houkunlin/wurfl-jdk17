package com.scientiamobile.wurfl.core.request;

import org.apache.commons.collections4.iterators.IteratorEnumeration;

import java.util.Enumeration;
import java.util.Map;

/**
 * Provides Map Header functionality.
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
     * Returns the heade rames.
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return new IteratorEnumeration<>(this.headers.keySet().iterator());
    }
}
