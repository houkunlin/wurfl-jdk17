package com.scientiamobile.wurfl.core.web.introspector;

import java.util.Map;

/**
 * Implementation of Introspector Request Response.
 */

final class IntrospectorRequestResponse {
    String deviceId;
    String userAgent;
    String requestType;
    Map<String, String> capabilities;
}

