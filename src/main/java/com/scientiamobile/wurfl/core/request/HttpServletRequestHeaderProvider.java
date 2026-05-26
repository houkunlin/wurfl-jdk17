package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
   private final HttpServletRequest request;

   public HttpServletRequestHeaderProvider(HttpServletRequest request) {
      this.request = request;
   }

   public String getHeader(String headerName) {
      return this.request.getHeader(headerName);
   }

   public Enumeration<String> getHeaderNames() {
      return this.request.getHeaderNames();
   }
}
