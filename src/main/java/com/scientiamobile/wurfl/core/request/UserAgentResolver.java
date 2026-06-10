package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Implementation of User Agent Resolver.
 */

public interface UserAgentResolver {
    String resolve(HttpServletRequest request);
}
