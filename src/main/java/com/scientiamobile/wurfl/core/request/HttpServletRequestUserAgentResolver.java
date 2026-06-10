package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Validate;

/**
 * Implementation of HTTP Servlet Request User Agent Resolver.
 */

final class HttpServletRequestUserAgentResolver implements UserAgentResolver {
    public final String resolve(HttpServletRequest request) {
        Validate.notNull(request, "The HttpServletRequest is null");
        return UserAgentUtils.getUserAgent(request);
    }
}

