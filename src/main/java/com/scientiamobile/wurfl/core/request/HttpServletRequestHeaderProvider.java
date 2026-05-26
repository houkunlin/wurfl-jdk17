package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
   private final HttpServletRequest request;

   public HttpServletRequestHeaderProvider(HttpServletRequest request) {
      this.request = request;
   }

   @Override
   public String getHeader(String headerName) {
      return this.request.getHeader(headerName);
   }

   @Override
   public Enumeration<String> getHeaderNames() {
      return this.request.getHeaderNames();
   }
}
