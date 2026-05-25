package com.scientiamobile.wurfl.core.web.introspector;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.WURFLEngine;
import com.scientiamobile.wurfl.core.matchers.MatcherManager;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import com.scientiamobile.wurfl.core.web.WurflWebConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntrospectorServlet extends HttpServlet implements WurflWebConstants {
   private static final long serialVersionUID = 1L;
   private static WURFLEngine a = null;
   private final ObjectMapper b = new ObjectMapper();
   public static final String ESCAPED_SPLIT_CHAR = "\\|";
   public static final String SPLIT_CHAR = "|";
   public static final String COLON = ":";
   public static final String TABULATION = "\t";
   public static final String ACTION = "action";
   public static final String USER_AGENT_PARAMNAME = "ua";
   public static final String UAPROF_PARAMNAME = "uaprof";
   public static final String HEADERS = "headers";
   public static final String CAPABILITIES = "capabilities";
   public static final String ID = "id";
   public static final String CAPABILITY_MAP = "capabilityMap";
   public static final String X_WAP_PROFILE = "X-Wap-Profile";
   public static final String X_WAP_PROFILE_lc = "x-wap-profile";
   public static final String USER_AGENT_lc = "user-agent";
   public static final String USER_AGENT = "User-Agent";
   public static final String DEVICE_STOCK_UA = "Device-Stock-UA";
   public static final String REQUEST = "Request";
   public static final String REQUEST_PERFORMANCE = "RequestPerformance";
   public static final String REQUEST_ACCURACY = "RequestAccuracy";
   public static final String INFO = "Info";
   public static final String BUCKETS = "Buckets";
   private static final Pattern c = Pattern.compile("[\r\n]+");
   private static final Logger d = LoggerFactory.getLogger(IntrospectorServlet.class);
   private static String e;

   public static void setWURFLEngine(WURFLEngine var0) {
      a = var0;
   }

   public void init(ServletConfig var1) {
      super.init(var1);
      this.getServletContext().getServerInfo();
      (new StringBuilder()).append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.version"));
      (new StringBuilder()).append(System.getProperty("java.vendor")).append(" ").append(System.getProperty("java.version"));
   }

   protected void doGet(HttpServletRequest var1, HttpServletResponse var2) {
      this.doPost(var1, var2);
   }

   protected void doPost(HttpServletRequest var1, HttpServletResponse var2) {
      System.currentTimeMillis();
      String var5 = var1.getParameter("action");
      boolean var3 = false;
      var2.setContentType("text/plain");
      PrintWriter var7 = var2.getWriter();
      if ("Request".equalsIgnoreCase(var5)) {
         a.setEngineTarget(EngineTarget.accuracy);
         var3 = this.a(var1, var7);
      } else if ("Info".equalsIgnoreCase(var5)) {
         a.setEngineTarget(EngineTarget.accuracy);
         boolean var10000;
         if (a == null) {
            b(var7);
            var10000 = true;
         } else {
            EngineTarget var4 = a.getEngineTarget();
            IntrospectorInfoResponse var8 = new IntrospectorInfoResponse();
            var8.apiVersion = e;
            var8.wurflVersion = a.getWURFLUtils().getVersion();
            var8.engineTarget = var4.name();
            var8.userAgentPriority = a.getUserAgentPriority().name();
            var8.serverInfo = this.getServletContext().getServerInfo();
            var8.osName = System.getProperty("os.name");
            var8.osVersion = System.getProperty("os.version");
            var8.javaVendor = System.getProperty("java.vendor");
            var8.javaVersion = System.getProperty("java.version");
            String var6 = this.b.defaultPrettyPrintingWriter().writeValueAsString(var8);
            var7.println(var6);
            var10000 = true;
         }

         var3 = var10000;
      } else if ("form".equalsIgnoreCase(var5)) {
         a.setEngineTarget(EngineTarget.valueOf(var1.getParameter("wurflEngineTarget")));
         a.setUserAgentPriority(UserAgentPriority.valueOf(var1.getParameter("wurflUserAgentPriority")));
         var3 = this.a(var1, var7);
      } else if ("Buckets".equalsIgnoreCase(var5)) {
         var3 = this.a(var7);
      }

      if (!var3) {
         var7.println("action " + var5 + " not supported");
      }

      var7.flush();
   }

   private boolean a(PrintWriter var1) {
      if (a == null) {
         b(var1);
         return true;
      } else {
         try {
            var1.println("WURFL Java API " + e);
            Field var2;
            boolean var3 = (var2 = GeneralWURFLEngine.class.getDeclaredField("o")).isAccessible();
            var2.setAccessible(true);
            WURFLModel var4 = (WURFLModel)var2.get(a);
            Field var5;
            boolean var6 = (var5 = GeneralWURFLEngine.class.getDeclaredField("l")).isAccessible();
            var5.setAccessible(true);
            Object var7 = var5.get(a);
            ArrayList var8 = null;
            if (var4 != null && var7 != null) {
               Field var9;
               boolean var10 = (var9 = var7.getClass().getDeclaredField("engineTarget")).isAccessible();
               var9.setAccessible(true);
               EngineTarget var11 = (EngineTarget)var9.get(var7);
               Field var12;
               boolean var13 = (var12 = var7.getClass().getDeclaredField("matcherManager")).isAccessible();
               var12.setAccessible(true);
               MatcherManager var14 = (MatcherManager)var12.get(var7);
               Set var24 = var4.getAllDevices();
               ArrayList var15 = new ArrayList(var24.size());
               Iterator var25 = var24.iterator();

               while(var25.hasNext()) {
                  ModelDevice var18;
                  if (!StringUtils.isEmpty((var18 = (ModelDevice)var25.next()).getUserAgent())) {
                     var15.add(var18.getUserAgent());
                  }
               }

               var9.set(var7, EngineTarget.accuracy);
               var8 = new ArrayList(var15.size());
               ArrayList var26 = new ArrayList(var15.size());
               DefaultWURFLRequestFactory var16 = new DefaultWURFLRequestFactory();
               d.info("BUCKETS (1/3): start matching...");
               long var33 = System.currentTimeMillis();

               for(String var17 : var15) {
                  DeviceInfo var31 = var14.matchRequest(var16.createRequest(var17, EngineTarget.accuracy));
                  String var20 = a((String)"matcherName", (Object)var31);
                  String var21 = a((String)"normalizedUserAgent", (Object)var31);
                  String var22 = a((String)"originalUserAgent", (Object)var31);
                  var26.add(new MatchResultRow(var20, var31.getId(), var21, var22));
               }

               d.info("BUCKETS (1/3): finished matching. Took " + (System.currentTimeMillis() - var33) + " ms");
               d.info("BUCKETS (2/3): start sorting...");
               var33 = System.currentTimeMillis();
               Collections.sort(var26);
               d.info("BUCKETS (2/3): finished sorting. Took " + (System.currentTimeMillis() - var33) + " ms");
               d.info("BUCKETS (3/3): start building strings...");
               var33 = System.currentTimeMillis();

               for(MatchResultRow var32 : var26) {
                  var8.add(var32.toString());
               }

               d.info("BUCKETS (3/3): finished building strings. Took " + (System.currentTimeMillis() - var33) + " ms");
               var9.set(var7, var11);
               var12.setAccessible(var13);
               var9.setAccessible(var10);
            }

            var5.setAccessible(var6);
            var2.setAccessible(var3);

            for(String var28 : var8) {
               var1.println(var28);
            }

            return true;
         } catch (Exception var23) {
            d.debug(var23.getClass().getSimpleName() + " - " + var23.getMessage());
            return false;
         }
      }
   }

   private static String a(String var0, Object var1) {
      Field var3;
      boolean var2 = (var3 = var1.getClass().getDeclaredField(var0)).isAccessible();
      var3.setAccessible(true);
      String var4 = (String)var3.get(var1);
      var3.setAccessible(var2);
      return var4;
   }

   private boolean a(HttpServletRequest var1, PrintWriter var2) {
      if (a == null) {
         b(var2);
         return true;
      } else {
         String var3;
         if ((var3 = var1.getParameter("uaprof")) == null || var3.trim().length() == 0) {
            var3 = var1.getHeader("x-wap-profile");
         }

         if (var3 == null) {
            var3 = var1.getHeader("X-Wap-Profile");
         }

         HeaderOnlyHttpServletRequest var5 = new HeaderOnlyHttpServletRequest();
         if (var1.getParameter("form") == null) {
            HashMap var4 = new HashMap();
            Enumeration var6 = var1.getHeaderNames();

            while(var6.hasMoreElements()) {
               String var7 = var6.nextElement().toString();
               if ("User-Agent".equalsIgnoreCase(var7)) {
                  var4.put("User-Agent", var1.getHeader(var7));
               } else {
                  var4.put(var7, var1.getHeader(var7));
               }
            }

            var5.addHeaders(var4);
            UserAgentUtils.getUserAgent(var1);
         } else {
            String var11;
            if ((var11 = var1.getParameter("ua")) == null || var11.trim().length() <= 0) {
               var11 = var1.getHeader("User-Agent");
            }

            if (var11 != null) {
               var5.addHeader("User-Agent", var11);
            }

            String var16;
            if ((var16 = var1.getParameter("headers")) != null && var16.trim().length() > 0) {
               var16 = var16.trim();
               var11 = c.matcher(var16).replaceAll("|").split("\\|");

               for(int var8 = 0; var8 < ((Object[])var11).length; ++var8) {
                  String var9;
                  if ((var9 = ((Object[])var11)[var8]).indexOf(":") >= 0) {
                     String[] var10 = var9.split(":");
                     var5.addHeader(var10[0].trim(), var10[1].trim());
                  }
               }
            }
         }

         if (var3 != null) {
            var5.addHeader("X-Wap-Profile", var3);
         }

         String var13 = var1.getParameter("capabilities");
         String[] var18 = null;
         if (var13 != null && var13.trim().length() > 0) {
            var13 = var13.trim();
            var18 = c.matcher(var13).replaceAll("|").split("\\|");
         }

         IntrospectorRequestResponse var19 = new IntrospectorRequestResponse();
         Device var15;
         var15 = a.getDeviceForRequest((HttpServletRequest)var5);
         var19.deviceId = var15.getId();
         var19.userAgent = var5.getHeader("User-Agent");
         var19.requestType = var1.getParameter("form") == null ? "request" : "form";
         if (var18 != null && var18.length > 0) {
            HashMap var20 = new HashMap();

            for(int var22 = 0; var22 < var18.length; ++var22) {
               String var23 = var18[var22].trim();
               var20.put(var23, var15.getCapability(var23));
            }
            var19.capabilities = var20;
         }

         String var21 = this.b.defaultPrettyPrintingWriter().writeValueAsString(var19);
         var2.println(var21);
         return true;
      }
   }

   private static void b(PrintWriter var0) {
      StringBuilder var1;
      (var1 = new StringBuilder()).append("INTROSPECTOR SERVLET ERROR!\n");
      var1.append("\n");
      var1.append("No WURFLHolder was initialized in the IntrospectorServlet.\n");
      var1.append("To use IntrospectorServlet, please call the method:\n");
      var1.append("\tpublic static void IntrospectorServlet.setWURFLHolder(WURFLHolder holder)\n");
      var1.append("to provide the servlet with the running WURFL instance.\n");
      var1.append("\n");
      var1.append("For more details, see documentation.\n");
      var0.println(var1.toString());
   }

   static {
      String var0 = "/META-INF/maven/com.scientiamobile.wurfl/wurfl/pom.properties";
      InputStream var10 = IntrospectorServlet.class.getResourceAsStream(var0);
      Properties var1 = new Properties();

      try {
         var1.load(var10);
         e = "1.9.1.0";
      } catch (IOException var8) {
         e = "(Unavailable...)";
      } finally {
         try {
            var10.close();
         } catch (Exception var7) {
         }

      }

      if (e == null || "".equals(e)) {
         e = "(Unavailable...)";
      }

      d.info("WURFL core library running version " + var1.get("version"));
   }
}
