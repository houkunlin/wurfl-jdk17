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
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class WURFLServletContextListener implements WurflWebConstants, ServletContextListener {
   private String a;

   public WURFLServletContextListener() {
      this.a = WURFL_ENGINE_KEY;
   }

   public void contextInitialized(ServletContextEvent var1) {
      ServletContext var2 = var1.getServletContext();
      String var10;
      if (!StringUtils.isEmpty(var10 = var1.getServletContext().getInitParameter("wurflEngineKey"))) {
         this.a = var10;
      }

      Validate.notEmpty(var10 = var2.getInitParameter("wurfl"), "Please Specify a Valid Location for wurfl context parameter");
      String[] var3 = StringUtils.split(StringUtils.defaultString(var2.getInitParameter("wurflPatch")), " ,");
      URI var4;
      XMLResource var12;
      if ((var4 = a(var2, var10)) != null) {
         var12 = new XMLResource(var4);
      } else {
         var12 = new XMLResource(var10);
      }

      WURFLResources var14 = new WURFLResources();

      for(int var5 = 0; var5 < var3.length; ++var5) {
         URI var6;
         XMLResource var7;
         if ((var6 = a(var2, var3[var5])) != null) {
            var7 = new XMLResource(var6);
         } else {
            var7 = new XMLResource(var3[var5]);
         }

         var14.add(var7);
      }

      GeneralWURFLEngine var13 = new GeneralWURFLEngine(var12, var14);
      String var15;
      if ((var15 = var2.getInitParameter("capability-filter")) != null) {
         String[] var16 = var15.toString().split("\n");

         for(int var18 = 0; var18 < var16.length; ++var18) {
            if (var16[var18] != null) {
               var16[var18] = var16[var18].trim();
            }
         }

         var13.setCapabilityFilter(var16);
      }

      String var17;
      if ((var17 = var2.getInitParameter("wurflEngineTarget")) != null) {
         try {
            var13.setEngineTarget(EngineTarget.valueOf(var17));
         } catch (IllegalArgumentException var9) {
            throw new WURFLRuntimeException("Invalid value for wurflEngineTarget; accepted values are: " + EngineTarget.performance.toString() + " (default) and " + EngineTarget.accuracy.toString(), var9);
         }
      }

      String var19;
      if ((var19 = var2.getInitParameter("wurflUserAgentPriority")) != null) {
         try {
            var13.setUserAgentPriority(UserAgentPriority.valueOf(var19));
         } catch (IllegalArgumentException var8) {
            throw new WURFLRuntimeException("Invalid value for wurflUserAgentPriority; accepted values are: " + UserAgentPriority.OverrideSideloadedBrowserUserAgent.toString() + " (default) and " + UserAgentPriority.UsePlainUserAgent.toString(), var8);
         }
      }

      var2.setAttribute(this.a, var13);
   }

   private static URI a(ServletContext var0, String var1) {
      try {
         URL var4;
         if ((var4 = var0.getResource(var1)) == null) {
            return null;
         } else {
            URI var5 = var4.toURI();
            return var5;
         }
      } catch (URISyntaxException var2) {
         throw new RuntimeException(var2);
      } catch (MalformedURLException var3) {
         throw new RuntimeException(var3);
      }
   }

   public void contextDestroyed(ServletContextEvent var1) {
      var1.getServletContext().removeAttribute(this.a);
   }
}
