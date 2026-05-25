package com.scientiamobile.wurfl.core.web.introspector;

import java.io.BufferedReader;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

final class HeaderOnlyHttpServletRequest implements HttpServletRequest {
   private Map headers = new HashMap();

   public final Object getAttribute(String var1) {
      return null;
   }

   public final Enumeration getAttributeNames() {
      return null;
   }

   public final String getCharacterEncoding() {
      return null;
   }

   public final void setCharacterEncoding(String var1) {
   }

   public final int getContentLength() {
      return 0;
   }

   public final String getContentType() {
      return null;
   }

   public final ServletInputStream getInputStream() {
      return null;
   }

   public final String getParameter(String var1) {
      return null;
   }

   public final Enumeration getParameterNames() {
      return null;
   }

   public final String[] getParameterValues(String var1) {
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

   public final void setAttribute(String var1, Object var2) {
   }

   public final void removeAttribute(String var1) {
   }

   public final Locale getLocale() {
      return null;
   }

   public final Enumeration getLocales() {
      return null;
   }

   public final boolean isSecure() {
      return false;
   }

   public final RequestDispatcher getRequestDispatcher(String var1) {
      return null;
   }

   public final String getRealPath(String var1) {
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

   public final long getDateHeader(String var1) {
      return 0L;
   }

   public final String getHeader(String var1) {
      return (String)this.headers.get(var1);
   }

   public final void addHeader(String headerName, String headerValue) {
      this.headers.put(headerName, headerValue);
   }

   public final void addHeaders(Map headers) {
      for(Map.Entry var2 : headers.entrySet()) {
         this.addHeader((String)var2.getKey(), (String)var2.getValue());
      }

   }

   public final Enumeration getHeaders(String var1) {
      return null;
   }

   public final Enumeration getHeaderNames() {
      Vector var1 = new Vector();

      for(String var3 : this.headers.keySet()) {
         var1.add(var3);
      }

      return var1.elements();
   }

   public final int getIntHeader(String var1) {
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

   public final boolean isUserInRole(String var1) {
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

   public final HttpSession getSession(boolean var1) {
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

