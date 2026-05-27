package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class WURFLServletContextListener implements WurflWebConstants, ServletContextListener {
    private String engineKeyAttributeName;

    public WURFLServletContextListener() {
        this.engineKeyAttributeName = WURFL_ENGINE_KEY;
    }

    private static URI resolveResourceUri(ServletContext servletContext, String resourcePath) {
        try {
            URL resourceUrl;
            resourceUrl = servletContext.getResource(resourcePath);
            if (resourceUrl == null) {
                return null;
            } else {
                URI resourceUri = resourceUrl.toURI();
                return resourceUri;
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        resolveEngineKey(servletContext);
        String wurflPath = servletContext.getInitParameter("wurfl");
        Validate.notEmpty(wurflPath, "Please Specify a Valid Location for wurfl context parameter");
        String[] wurflPatchPaths = StringUtils.split(
                StringUtils.defaultString(servletContext.getInitParameter("wurflPatch")), " ,");
        XMLResource wurflResource = createXmlResource(servletContext, wurflPath);
        WURFLResources patchResources = createPatchResources(servletContext, wurflPatchPaths);
        GeneralWURFLEngine wurflEngine = new GeneralWURFLEngine(wurflResource, patchResources);
        configureCapabilityFilter(servletContext, wurflEngine);
        configureEngineTarget(servletContext, wurflEngine);
        configureUserAgentPriority(servletContext, wurflEngine);
        servletContext.setAttribute(this.engineKeyAttributeName, wurflEngine);
    }

    private void resolveEngineKey(ServletContext servletContext) {
        String wurflEngineKey = servletContext.getInitParameter("wurflEngineKey");
        if (!StringUtils.isEmpty(wurflEngineKey)) {
            this.engineKeyAttributeName = wurflEngineKey;
        }
    }

    private static XMLResource createXmlResource(ServletContext servletContext, String path) {
        URI uri = resolveResourceUri(servletContext, path);
        return uri != null ? new XMLResource(uri) : new XMLResource(path);
    }

    private static WURFLResources createPatchResources(ServletContext servletContext, String[] patchPaths) {
        WURFLResources resources = new WURFLResources();
        for (String patchPath : patchPaths) {
            URI uri = resolveResourceUri(servletContext, patchPath);
            XMLResource resource = uri != null ? new XMLResource(uri) : new XMLResource(patchPath);
            resources.add(resource);
        }
        return resources;
    }

    private static void configureCapabilityFilter(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String filterValue = servletContext.getInitParameter("capability-filter");
        if (filterValue == null) {
            return;
        }
        String[] capabilityFilter = filterValue.split("\n");
        for (int i = 0; i < capabilityFilter.length; i++) {
            if (capabilityFilter[i] != null) {
                capabilityFilter[i] = capabilityFilter[i].trim();
            }
        }
        wurflEngine.setCapabilityFilter(capabilityFilter);
    }

    private static void configureEngineTarget(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String value = servletContext.getInitParameter("wurflEngineTarget");
        if (value == null) {
            return;
        }
        try {
            wurflEngine.setEngineTarget(EngineTarget.valueOf(value));
        } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException(
                    "Invalid value for wurflEngineTarget; accepted values are: "
                            + EngineTarget.performance + " (default) and " + EngineTarget.accuracy, e);
        }
    }

    private static void configureUserAgentPriority(ServletContext servletContext, GeneralWURFLEngine wurflEngine) {
        String value = servletContext.getInitParameter("wurflUserAgentPriority");
        if (value == null) {
            return;
        }
        try {
            wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(value));
        } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException(
                    "Invalid value for wurflUserAgentPriority; accepted values are: "
                            + UserAgentPriority.OverrideSideloadedBrowserUserAgent + " (default) and "
                            + UserAgentPriority.UsePlainUserAgent, e);
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        servletContextEvent.getServletContext().removeAttribute(this.engineKeyAttributeName);
    }
}
