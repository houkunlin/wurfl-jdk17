package com.scientiamobile.wurfl.core.web.introspector;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * Implementation of Header Only HTTP Servlet Request.
 */

final class HeaderOnlyHttpServletRequest implements HttpServletRequest {
    private final Map<String, String> headers = new HashMap<>();

    public final Object getAttribute(String attributeName) {
        return null;
    }

    /**
     * Returns the attribut eames.
     */

    public final Enumeration<String> getAttributeNames() {
        return null;
    }

    public final String getCharacterEncoding() {
        return null;
    }

    /**
     * Sets the characte rncoding.
 */

    public final void setCharacterEncoding(String encoding) {
    }

    public final int getContentLength() {
        return 0;
    }

    /**
     * Returns the conten tengt hong.
 */

    public final long getContentLengthLong() {
        return 0L;
    }

    public final String getContentType() {
        return null;
    }

    /**
     * Returns the inpu ttream.
 */

    public final ServletInputStream getInputStream() {
        return null;
    }

    public final String getParameter(String parameterName) {
        return null;
    }

    /**
     * Returns the paramete rames.
 */

    public final Enumeration<String> getParameterNames() {
        return null;
    }

    public final String[] getParameterValues(String parameterName) {
        return null;
    }

    /**
     * Returns the paramete rap.
 */

    public final Map<String, String[]> getParameterMap() {
        return null;
    }

    public final String getProtocol() {
        return null;
    }

    /**
     * Returns the protoco leques td.
 */

    public final String getProtocolRequestId() {
        return null;
    }

    public final String getScheme() {
        return null;
    }

    /**
     * Returns the serve rame.
 */

    public final String getServerName() {
        return null;
    }

    public final int getServerPort() {
        return 0;
    }

    /**
     * Returns the reader.
 */

    public final BufferedReader getReader() {
        return null;
    }

    public final String getRemoteAddr() {
        return null;
    }

    /**
     * Returns the remot eost.
 */

    public final String getRemoteHost() {
        return null;
    }

    public final void setAttribute(String attributeName, Object value) {
    }

    /**
     * Remov ettribute.
 */

    public final void removeAttribute(String attributeName) {
    }

    public final Locale getLocale() {
        return null;
    }

    /**
     * Returns the locales.
 */

    public final Enumeration<Locale> getLocales() {
        return null;
    }

    public final boolean isSecure() {
        return false;
    }

    /**
     * Returns the reques tispatcher.
 */

    public final RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public final String getRealPath(String path) {
        return null;
    }

    /**
     * Returns the remot eort.
 */

    public final int getRemotePort() {
        return 0;
    }

    public final String getLocalName() {
        return null;
    }

    /**
     * Returns the loca lddr.
 */

    public final String getLocalAddr() {
        return null;
    }

    public final int getLocalPort() {
        return 0;
    }

    /**
     * Returns the servle tontext.
 */

    public final ServletContext getServletContext() {
        return null;
    }

    public final AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    /**
     * Star tsync.
 */

    public final AsyncContext startAsync(ServletRequest request, ServletResponse response) throws IllegalStateException {
        return null;
    }

    public final boolean isAsyncStarted() {
        return false;
    }

    /**
     * Returns whether this i ssyn cupported.
 */

    public final boolean isAsyncSupported() {
        return false;
    }

    public final AsyncContext getAsyncContext() {
        return null;
    }

    /**
     * Returns the dispatche rype.
 */

    public final DispatcherType getDispatcherType() {
        return null;
    }

    public final ServletConnection getServletConnection() {
        return null;
    }

    /**
     * Returns the aut hype.
 */

    public final String getAuthType() {
        return null;
    }

    public final Cookie[] getCookies() {
        return null;
    }

    /**
     * Returns the dat eeader.
 */

    public final long getDateHeader(String name) {
        return 0L;
    }

    @Override
/**
 * Returns the header.
 */

    public String getHeader(String name) {
        return this.headers.get(name);
    }

    public final void addHeader(String headerName, String headerValue) {
        this.headers.put(headerName, headerValue);
    }

    /**
     * Ad deaders.
 */

    public final void addHeaders(Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            this.addHeader(entry.getKey(), entry.getValue());
        }

    }

    @Override
/**
 * Returns the headers.
 */

    public Enumeration<String> getHeaders(String name) {
        return null;
    }

    @Override
/**
 * Returns the heade rames.
 */

    public Enumeration<String> getHeaderNames() {
        ArrayList<String> headerNames = new ArrayList<>();

        for (String headerName : this.headers.keySet()) {
            headerNames.add(headerName);
        }

        return Collections.enumeration(headerNames);
    }

    /**
     * Returns the in teader.
 */

    public final int getIntHeader(String name) {
        return 0;
    }

    public final String getMethod() {
        return null;
    }

    /**
     * Returns the pat hnfo.
 */

    public final String getPathInfo() {
        return null;
    }

    public final String getPathTranslated() {
        return null;
    }

    /**
     * Returns the contex tath.
 */

    public final String getContextPath() {
        return null;
    }

    public final String getQueryString() {
        return null;
    }

    /**
     * Returns the remot eser.
 */

    public final String getRemoteUser() {
        return null;
    }

    public final String getRequestId() {
        return null;
    }

    /**
     * Returns whether this i sse r nole.
 */

    public final boolean isUserInRole(String role) {
        return false;
    }

    public final String changeSessionId() {
        return null;
    }

    /**
     * Authenticate.
 */

    public final boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
        return false;
    }

    public final void login(String username, String password) throws ServletException {
    }

    /**
     * Logout.
 */

    public final void logout() throws ServletException {
    }

    public final Principal getUserPrincipal() {
        return null;
    }

    /**
     * Returns the requeste dessio nd.
 */

    public final String getRequestedSessionId() {
        return null;
    }

    public final String getRequestURI() {
        return null;
    }

    /**
     * Returns the reques trl.
 */

    public final StringBuffer getRequestURL() {
        return null;
    }

    public final String getServletPath() {
        return null;
    }

    /**
     * Returns the session.
 */

    public final HttpSession getSession(boolean create) {
        return null;
    }

    public final HttpSession getSession() {
        return null;
    }

    /**
     * Returns whether this i sequeste dessio n dalid.
 */

    public final boolean isRequestedSessionIdValid() {
        return false;
    }

    public final boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    /**
     * Returns whether this i sequeste dessio n dro mrl.
 */

    public final boolean isRequestedSessionIdFromURL() {
        return false;
    }

    public final boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    /**
     * Upgrade.
 */

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
