package com.scientiamobile.wurfl.core.request;

import jakarta.servlet.http.HttpServletRequest;

public final class FastUserAgentResolver implements UserAgentResolver {
  public final String resolve(HttpServletRequest paramHttpServletRequest) {
    return paramHttpServletRequest.getHeader("User-Agent");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\FastUserAgentResolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
