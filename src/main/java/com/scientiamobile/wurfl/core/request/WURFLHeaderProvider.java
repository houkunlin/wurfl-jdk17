package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;

/**
 * Provides WURFL Header functionality.
 */

public interface WURFLHeaderProvider {
    String getHeader(String headerName);

    Enumeration<String> getHeaderNames();
}
