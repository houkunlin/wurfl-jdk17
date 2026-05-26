package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckConnection {
   private final transient Logger a = LoggerFactory.getLogger(this.getClass());
   private final List<CheckConnectionObserver> b = new ArrayList<>();
   private String c;
   private String d = System.getProperty("os.name") + " " + System.getProperty("os.version");
   private String e;
   private boolean f;

   public interface CheckConnectionObserver {
      void update(CheckConnection var1, Object var2);
   }

   public CheckConnection() {
      String var1;
      this.e = StringUtils.isNotEmpty(System.getProperty("jboss.boot.library.list")) ? "JBoss/WildFly" : (StringUtils.isNotEmpty(System.getProperty("oracle.j2ee.home")) ? "OC4j/Oracle AS" : (!(var1 = System.getProperty("java.class.path").toLowerCase()).contains("websphere") && !var1.contains("web sphere") ? (!var1.contains("tomcat") && !var1.contains("juli") ? (var1.contains("jetty") ? "Jetty" : "Command line") : "Tomcat") : "IBM WebSphere"));
      this.f = ResourceUtils.getFullBuildId().startsWith("ch");
   }

   public void setup(WURFLEngine var1, WURFLModel var2) {
      if (this.f) {
         String var3 = "unknown";
         if (var2 instanceof DefaultWURFLModel) {
            var2 = var2;

            try {
               Field var4;
               (var4 = DefaultWURFLModel.class.getDeclaredField("g")).setAccessible(true);
               var3 = StringUtils.isEmpty(var3 = (String)var4.get(var2)) ? "unknown" : var3;
            } catch (Exception var5) {
               this.a.error("Unable to get data from model class " + var5.getMessage());
            }

            String var7;
            int var9;
            int var10;
            StringBuilder var11;
            (var11 = a(a(a(a(a(a(a(a(new StringBuilder("{ "), "api-smid", ResourceUtils.getBuildId(), true), "wurfl-smid", var3, true), "api", this.getApiName(), true), "api_ver", "1.9.1.0", true), "wurfl", (var9 = (var7 = var1.getWURFLUtils().getVersion()).indexOf("for WURFL")) == -1 ? var7 : ((var10 = var7.indexOf(";")) != -1 ? var7.substring(var9, var10) : var7.substring(var9)), true), "host", a(), true), "os", this.d, true), "platform", this.e, false)).append(" }");
            this.c = var11.toString();
         }

      }
   }

   private static StringBuilder a(StringBuilder var0, String var1, String var2, boolean var3) {
      var0.append("\"").append(var1).append("\": \"").append(var2).append("\"");
      if (var3) {
         var0.append(", ");
      } else {
         var0.append(" ");
      }

      return var0;
   }

   private static String a() {
      try {
         return InetAddress.getLocalHost().getHostName();
      } catch (Exception var0) {
         return "unknown";
      }
   }

   public void check() {
      if (this.f) {
         ScheduledExecutorService var1 = Executors.newSingleThreadScheduledExecutor();

         try {
            var1.execute(new ConnectivityCheckerTask(this));
         } finally {
            var1.shutdown();
         }

      }
   }

   public synchronized void addObserver(CheckConnectionObserver var1) {
      this.b.add(var1);
   }

   public void notifyObservers(Object var1) {
      Iterator<CheckConnectionObserver> var2 = this.b.iterator();

      while(var2.hasNext()) {
         var2.next().update(this, var1);
         this.a.info("observer notified");
      }

   }

   public String getApiName() {
      String var1;
      return (var1 = System.getProperty("java.class.path")) != null && var1.contains("wurfl-scala") ? "WURFL_Scala_API" : "WURFL_Java_API";
   }

   // $FF: synthetic method
   static String a(CheckConnection var0) {
      return var0.c;
   }
}
