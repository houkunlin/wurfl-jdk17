package com.scientiamobile.wurfl.core.web;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.exc.WURFLRuntimeException;
import com.scientiamobile.wurfl.core.resource.WURFLResource;
import com.scientiamobile.wurfl.core.resource.WURFLResources;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class WURFLServletContextListener implements WurflWebConstants, ServletContextListener {
  private String a = WURFL_ENGINE_KEY;
  
  public void contextInitialized(ServletContextEvent paramServletContextEvent) {
    XMLResource xMLResource;
    ServletContext servletContext = paramServletContextEvent.getServletContext();
    String str1;
    if (!StringUtils.isEmpty(str1 = paramServletContextEvent.getServletContext().getInitParameter("wurflEngineKey")))
      this.a = str1; 
    Validate.notEmpty(str1 = servletContext.getInitParameter("wurfl"), "Please Specify a Valid Location for wurfl context parameter");
    String[] arrayOfString = StringUtils.split(StringUtils.defaultString(servletContext.getInitParameter("wurflPatch")), " ,");
    URI uRI;
    if ((uRI = a(servletContext, str1)) != null) {
      xMLResource = new XMLResource(uRI);
    } else {
      xMLResource = new XMLResource((String)xMLResource);
    } 
    WURFLResources wURFLResources = new WURFLResources();
    for (byte b = 0; b < arrayOfString.length; b++) {
      XMLResource xMLResource1;
      URI uRI1;
      if ((uRI1 = a(servletContext, arrayOfString[b])) != null) {
        xMLResource1 = new XMLResource(uRI1);
      } else {
        xMLResource1 = new XMLResource(arrayOfString[b]);
      } 
      wURFLResources.add((WURFLResource)xMLResource1);
    } 
    GeneralWURFLEngine generalWURFLEngine = new GeneralWURFLEngine((WURFLResource)xMLResource, wURFLResources);
    String str2;
    if ((str2 = servletContext.getInitParameter("capability-filter")) != null) {
      String[] arrayOfString1 = str2.toString().split("\n");
      for (byte b1 = 0; b1 < arrayOfString1.length; b1++) {
        if (arrayOfString1[b1] != null)
          arrayOfString1[b1] = arrayOfString1[b1].trim(); 
      } 
      generalWURFLEngine.setCapabilityFilter(arrayOfString1);
    } 
    String str3;
    if ((str3 = servletContext.getInitParameter("wurflEngineTarget")) != null)
      try {
        generalWURFLEngine.setEngineTarget(EngineTarget.valueOf(str3));
      } catch (IllegalArgumentException illegalArgumentException1) {
        throw new WURFLRuntimeException("Invalid value for wurflEngineTarget; accepted values are: " + EngineTarget.performance.toString() + " (default) and " + EngineTarget.accuracy.toString(), illegalArgumentException1);
      }  
    String str4;
    if ((str4 = servletContext.getInitParameter("wurflUserAgentPriority")) != null)
      try {
        generalWURFLEngine.setUserAgentPriority(UserAgentPriority.valueOf(str4));
      } catch (IllegalArgumentException illegalArgumentException) {
        throw new WURFLRuntimeException("Invalid value for wurflUserAgentPriority; accepted values are: " + UserAgentPriority.OverrideSideloadedBrowserUserAgent.toString() + " (default) and " + UserAgentPriority.UsePlainUserAgent.toString(), illegalArgumentException);
      }  
    servletContext.setAttribute(this.a, illegalArgumentException);
  }
  
  private static URI a(ServletContext paramServletContext, String paramString) {
    try {
      URL uRL;
      if ((uRL = paramServletContext.getResource(paramString)) == null)
        return null; 
      URI uRI = uRL.toURI();
    } catch (URISyntaxException uRISyntaxException) {
      throw new RuntimeException(uRISyntaxException);
    } catch (MalformedURLException malformedURLException) {
      throw new RuntimeException(malformedURLException);
    } 
    return (URI)malformedURLException;
  }
  
  public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
    paramServletContextEvent.getServletContext().removeAttribute(this.a);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\web\WURFLServletContextListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
