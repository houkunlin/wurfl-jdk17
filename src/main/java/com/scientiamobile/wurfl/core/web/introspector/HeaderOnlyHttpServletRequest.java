package com.scientiamobile.wurfl.core.web.introspector;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import jakarta.servlet.AsyncContext;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConnection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpUpgradeHandler;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

final class HeaderOnlyHttpServletRequest implements HttpServletRequest {
   private final Map<String, String> headers = new HashMap<>();

   public final Object getAttribute(String var1) {
      return null;
   }

   public final Enumeration<String> getAttributeNames() {
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

   public final long getContentLengthLong() {
      return 0L;
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

   public final Enumeration<String> getParameterNames() {
      return null;
   }

   public final String[] getParameterValues(String var1) {
      return null;
   }

   public final Map<String, String[]> getParameterMap() {
      return null;
   }

   public final String getProtocol() {
      return null;
   }

   public final String getProtocolRequestId() {
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

   public final Enumeration<Locale> getLocales() {
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

   public final ServletContext getServletContext() {
      return null;
   }

   public final AsyncContext startAsync() throws IllegalStateException {
      return null;
   }

   public final AsyncContext startAsync(ServletRequest var1, ServletResponse var2) throws IllegalStateException {
      return null;
   }

   public final boolean isAsyncStarted() {
      return false;
   }

   public final boolean isAsyncSupported() {
      return false;
   }

   public final AsyncContext getAsyncContext() {
      return null;
   }

   public final DispatcherType getDispatcherType() {
      return null;
   }

   public final ServletConnection getServletConnection() {
      return null;
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

   public final void addHeaders(Map<String, String> headers) {
      for(Map.Entry<String, String> entry : headers.entrySet()) {
         this.addHeader(entry.getKey(), entry.getValue());
      }

   }

   public final Enumeration<String> getHeaders(String var1) {
      return null;
   }

   public final Enumeration<String> getHeaderNames() {
      Vector<String> var1 = new Vector<>();

      for(String headerName : this.headers.keySet()) {
         var1.add(headerName);
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

   public final String getRequestId() {
      return null;
   }

   public final boolean isUserInRole(String var1) {
      return false;
   }

   public final String changeSessionId() {
      return null;
   }

   public final boolean authenticate(HttpServletResponse var1) throws IOException, ServletException {
      return false;
   }

   public final void login(String var1, String var2) throws ServletException {
   }

   public final void logout() throws ServletException {
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

   public final <T extends HttpUpgradeHandler> T upgrade(Class<T> var1) {
      return null;
   }

   public final Part getPart(String var1) {
      return null;
   }

   public final java.util.Collection<Part> getParts() {
      return null;
   }
}
