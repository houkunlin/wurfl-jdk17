package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementation of Fast User Agent Resolver.
 */

public final class FastUserAgentResolver implements UserAgentResolver {
    public final String resolve(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }
}
