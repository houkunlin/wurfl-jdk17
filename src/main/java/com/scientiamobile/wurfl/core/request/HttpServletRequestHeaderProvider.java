package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
    private final HttpServletRequest request;

    public HttpServletRequestHeaderProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getHeader(String headerName) {
        return this.request.getHeader(headerName);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return this.request.getHeaderNames();
    }
}
