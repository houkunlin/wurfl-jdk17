package com.scientiamobile.wurfl.core.web.introspector;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

   public final Object getAttribute(String attributeName) {
      return null;
   }

   public final Enumeration<String> getAttributeNames() {
      return null;
   }

   public final String getCharacterEncoding() {
      return null;
   }

   public final void setCharacterEncoding(String encoding) {
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

   public final String getParameter(String parameterName) {
      return null;
   }

   public final Enumeration<String> getParameterNames() {
      return null;
   }

   public final String[] getParameterValues(String parameterName) {
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

   public final void setAttribute(String attributeName, Object value) {
   }

   public final void removeAttribute(String attributeName) {
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

   public final RequestDispatcher getRequestDispatcher(String path) {
      return null;
   }

   public final String getRealPath(String path) {
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

   public final AsyncContext startAsync(ServletRequest request, ServletResponse response) throws IllegalStateException {
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

   public final long getDateHeader(String name) {
      return 0L;
   }

   @Override
   public String getHeader(String name) {
      return this.headers.get(name);
   }

   public final void addHeader(String headerName, String headerValue) {
      this.headers.put(headerName, headerValue);
   }

   public final void addHeaders(Map<String, String> headers) {
      for(Map.Entry<String, String> entry : headers.entrySet()) {
         this.addHeader(entry.getKey(), entry.getValue());
      }

   }

   @Override
   public Enumeration<String> getHeaders(String name) {
      return null;
   }

   @Override
   public Enumeration<String> getHeaderNames() {
      ArrayList<String> headerNames = new ArrayList<>();

      for(String headerName : this.headers.keySet()) {
         headerNames.add(headerName);
      }

      return Collections.enumeration(headerNames);
   }

   public final int getIntHeader(String name) {
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

   public final boolean isUserInRole(String role) {
      return false;
   }

   public final String changeSessionId() {
      return null;
   }

   public final boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
      return false;
   }

   public final void login(String username, String password) throws ServletException {
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

   public final HttpSession getSession(boolean create) {
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

   public final <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) {
      return null;
   }

   public final Part getPart(String name) {
      return null;
   }

   public final java.util.Collection<Part> getParts() {
      return null;
   }
}
