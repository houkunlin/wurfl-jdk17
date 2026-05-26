package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

public final class FastUserAgentResolver implements UserAgentResolver {
   public final String resolve(HttpServletRequest request) {
      return request.getHeader("User-Agent");
   }
}
