package com.scientiamobile.wurfl.core.request;

import javax.servlet.http.HttpServletRequest;

public final class FastUserAgentResolver implements UserAgentResolver {
   public final String resolve(HttpServletRequest var1) {
      return var1.getHeader("User-Agent");
   }
}
