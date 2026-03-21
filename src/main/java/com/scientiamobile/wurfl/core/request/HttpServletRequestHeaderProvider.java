package com.scientiamobile.wurfl.core.request;

import java.util.Enumeration;
import jakarta.servlet.http.HttpServletRequest;

public class HttpServletRequestHeaderProvider implements WURFLHeaderProvider {
  private final HttpServletRequest a;
  
  public HttpServletRequestHeaderProvider(HttpServletRequest paramHttpServletRequest) {
    this.a = paramHttpServletRequest;
  }
  
  public String getHeader(String paramString) {
    return this.a.getHeader(paramString);
  }
  
  public Enumeration getHeaderNames() {
    return this.a.getHeaderNames();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\HttpServletRequestHeaderProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
