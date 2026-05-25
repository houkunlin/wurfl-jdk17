package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
   private final HttpServletRequest a;

   public HttpServletRequestHeaderProvider(HttpServletRequest var1) {
      this.a = var1;
   }

   public String getHeader(String var1) {
      return this.a.getHeader(var1);
   }

   public Enumeration getHeaderNames() {
      return this.a.getHeaderNames();
   }
}
