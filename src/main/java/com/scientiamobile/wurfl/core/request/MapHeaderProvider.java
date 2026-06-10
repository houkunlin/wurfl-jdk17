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

    @Override
/**
 * Returns the header.
 */

    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    @Override
/**
 * Returns the heade rames.
 */

    public Enumeration<String> getHeaderNames() {
        return new IteratorEnumeration<>(this.headers.keySet().iterator());
    }
}
