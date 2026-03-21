package com.scientiamobile.wurfl.core.web.introspector;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

final class e implements HttpServletRequest {
  private Map a = new HashMap<Object, Object>();
  
  public final Object getAttribute(String paramString) {
    return null;
  }
  
  public final Enumeration getAttributeNames() {
    return null;
  }
  
  public final String getCharacterEncoding() {
    return null;
  }
  
  public final void setCharacterEncoding(String paramString) {}
  
  public final int getContentLength() {
    return 0;
  }
  
  public final String getContentType() {
    return null;
  }
  
  public final ServletInputStream getInputStream() {
    return null;
  }
  
  public final String getParameter(String paramString) {
    return null;
  }
  
  public final Enumeration getParameterNames() {
    return null;
  }
  
  public final String[] getParameterValues(String paramString) {
    return null;
  }
  
  public final Map getParameterMap() {
    return null;
  }
  
  public final String getProtocol() {
    return null;
  }
  
  public final String getScheme() {
    return null;
  }
  
  public final String getServerName() {
    return null;
  }
  
  public final int getServerPort() {
    return 0;
  }
  
  public final BufferedReader getReader() {
    return null;
  }
  
  public final String getRemoteAddr() {
    return null;
  }
  
  public final String getRemoteHost() {
    return null;
  }
  
  public final void setAttribute(String paramString, Object paramObject) {}
  
  public final void removeAttribute(String paramString) {}
  
  public final Locale getLocale() {
    return null;
  }
  
  public final Enumeration getLocales() {
    return null;
  }
  
  public final boolean isSecure() {
    return false;
  }
  
  public final RequestDispatcher getRequestDispatcher(String paramString) {
    return null;
  }
  
  public final String getRealPath(String paramString) {
    return null;
  }
  
  public final int getRemotePort() {
    return 0;
  }
  
  public final String getLocalName() {
    return null;
  }
  
  public final String getLocalAddr() {
    return null;
  }
  
  public final int getLocalPort() {
    return 0;
  }
  
  public final String getAuthType() {
    return null;
  }
  
  public final Cookie[] getCookies() {
    return null;
  }
  
  public final long getDateHeader(String paramString) {
    return 0L;
  }
  
  public final String getHeader(String paramString) {
    return (String)this.a.get(paramString);
  }
  
  public final void a(String paramString1, String paramString2) {
    this.a.put(paramString1, paramString2);
  }
  
  public final void a(Map paramMap) {
    for (Map.Entry entry : paramMap.entrySet())
      a((String)entry.getKey(), (String)entry.getValue()); 
  }
  
  public final Enumeration getHeaders(String paramString) {
    return null;
  }
  
  public final Enumeration getHeaderNames() {
    Vector<String> vector = new Vector();
    for (String str : this.a.keySet())
      vector.add(str); 
    return vector.elements();
  }
  
  public final int getIntHeader(String paramString) {
    return 0;
  }
  
  public final String getMethod() {
    return null;
  }
  
  public final String getPathInfo() {
    return null;
  }
  
  public final String getPathTranslated() {
    return null;
  }
  
  public final String getContextPath() {
    return null;
  }
  
  public final String getQueryString() {
    return null;
  }
  
  public final String getRemoteUser() {
    return null;
  }
  
  public final boolean isUserInRole(String paramString) {
    return false;
  }
  
  public final Principal getUserPrincipal() {
    return null;
  }
  
  public final String getRequestedSessionId() {
    return null;
  }
  
  public final String getRequestURI() {
    return null;
  }
  
  public final StringBuffer getRequestURL() {
    return null;
  }
  
  public final String getServletPath() {
    return null;
  }
  
  public final HttpSession getSession(boolean paramBoolean) {
    return null;
  }
  
  public final HttpSession getSession() {
    return null;
  }
  
  public final boolean isRequestedSessionIdValid() {
    return false;
  }
  
  public final boolean isRequestedSessionIdFromCookie() {
    return false;
  }
  
  public final boolean isRequestedSessionIdFromURL() {
    return false;
  }
  
  public final boolean isRequestedSessionIdFromUrl() {
    return false;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\web\introspector\e.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
