package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class WURFLServletContextListener implements WurflWebConstants, ServletContextListener {
   private String engineKeyAttributeName;

   public WURFLServletContextListener() {
      this.engineKeyAttributeName = WURFL_ENGINE_KEY;
   }

   public void contextInitialized(ServletContextEvent servletContextEvent) {
      ServletContext servletContext = servletContextEvent.getServletContext();
      String wurflEngineKey;
      wurflEngineKey = servletContextEvent.getServletContext().getInitParameter("wurflEngineKey");
      if (!StringUtils.isEmpty(wurflEngineKey)) {
         this.engineKeyAttributeName = wurflEngineKey;
      }

      String wurflPath = servletContext.getInitParameter("wurfl");
      Validate.notEmpty(wurflPath, "Please Specify a Valid Location for wurfl context parameter");
      String[] wurflPatchPaths = StringUtils.split(StringUtils.defaultString(servletContext.getInitParameter("wurflPatch")), " ,");
      URI wurflResourceUri;
      XMLResource wurflResource;
      wurflResourceUri = resolveResourceUri(servletContext, wurflPath);
      if (wurflResourceUri != null) {
         wurflResource = new XMLResource(wurflResourceUri);
      } else {
         wurflResource = new XMLResource(wurflPath);
      }

      WURFLResources patchResources = new WURFLResources();

      for(int i = 0; i < wurflPatchPaths.length; ++i) {
         URI patchUri;
         XMLResource patchResource;
         patchUri = resolveResourceUri(servletContext, wurflPatchPaths[i]);
         if (patchUri != null) {
            patchResource = new XMLResource(patchUri);
         } else {
            patchResource = new XMLResource(wurflPatchPaths[i]);
         }

         patchResources.add(patchResource);
      }

      GeneralWURFLEngine wurflEngine = new GeneralWURFLEngine(wurflResource, patchResources);
      String capabilityFilterValue;
      capabilityFilterValue = servletContext.getInitParameter("capability-filter");
      if (capabilityFilterValue != null) {
         String[] capabilityFilter = capabilityFilterValue.split("\n");

         for(int i = 0; i < capabilityFilter.length; ++i) {
            if (capabilityFilter[i] != null) {
               capabilityFilter[i] = capabilityFilter[i].trim();
            }
         }

         wurflEngine.setCapabilityFilter(capabilityFilter);
      }

      String engineTargetValue;
      engineTargetValue = servletContext.getInitParameter("wurflEngineTarget");
      if (engineTargetValue != null) {
         try {
            wurflEngine.setEngineTarget(EngineTarget.valueOf(engineTargetValue));
         } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException("Invalid value for wurflEngineTarget; accepted values are: " + EngineTarget.performance.toString() + " (default) and " + EngineTarget.accuracy.toString(), e);
         }
      }

      String userAgentPriorityValue;
      userAgentPriorityValue = servletContext.getInitParameter("wurflUserAgentPriority");
      if (userAgentPriorityValue != null) {
         try {
            wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(userAgentPriorityValue));
         } catch (IllegalArgumentException e) {
            throw new WURFLRuntimeException("Invalid value for wurflUserAgentPriority; accepted values are: " + UserAgentPriority.OverrideSideloadedBrowserUserAgent.toString() + " (default) and " + UserAgentPriority.UsePlainUserAgent.toString(), e);
         }
      }

      servletContext.setAttribute(this.engineKeyAttributeName, wurflEngine);
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

   public void contextDestroyed(ServletContextEvent servletContextEvent) {
      servletContextEvent.getServletContext().removeAttribute(this.engineKeyAttributeName);
   }
}
