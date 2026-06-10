package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;

/**
 * Provides HTTP Servlet Request Header functionality.
 */

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
    private final HttpServletRequest request;

    public HttpServletRequestHeaderProvider(HttpServletRequest request) {
        this.request = request;
    }

    @Override
/**
 * Returns the header.
 */

    public String getHeader(String headerName) {
        return this.request.getHeader(headerName);
    }

    @Override
/**
 * Returns the heade rames.
 */

    public Enumeration<String> getHeaderNames() {
        return this.request.getHeaderNames();
    }
}
