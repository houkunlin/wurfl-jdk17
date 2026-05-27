package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckConnection {
   private static final Logger logger = LoggerFactory.getLogger(CheckConnection.class);
   private final List<CheckConnectionObserver> observers = new ArrayList<>();
   private String payloadJson;
   private String osNameAndVersion = System.getProperty("os.name") + " " + System.getProperty("os.version");
   private String platformName;
   private boolean enabled;

   public interface CheckConnectionObserver {
      void update(CheckConnection checkConnection, Object argument);
   }

   public CheckConnection() {
      String classPathLowerCase;
      this.platformName = StringUtils.isNotEmpty(System.getProperty("jboss.boot.library.list"))
         ? "JBoss/WildFly"
         : (StringUtils.isNotEmpty(System.getProperty("oracle.j2ee.home"))
            ? "OC4j/Oracle AS"
            : (!(classPathLowerCase = System.getProperty("java.class.path").toLowerCase(Locale.ENGLISH)).contains("websphere") && !classPathLowerCase.contains("web sphere")
               ? (!classPathLowerCase.contains("tomcat") && !classPathLowerCase.contains("juli")
                  ? (classPathLowerCase.contains("jetty") ? "Jetty" : "Command line")
                  : "Tomcat")
               : "IBM WebSphere"));
      this.enabled = ResourceUtils.getFullBuildId().startsWith("ch");
   }

   public void setup(WURFLEngine wurflEngine, WURFLModel wurflModel) {
      if (this.enabled) {
         String wurflSmid = "unknown";
         if (wurflModel instanceof DefaultWURFLModel) {

            try {
               Field smidField;
               (smidField = DefaultWURFLModel.class.getDeclaredField("g")).setAccessible(true);
               wurflSmid = StringUtils.isEmpty(wurflSmid = (String)smidField.get(wurflModel)) ? "unknown" : wurflSmid;
            } catch (Exception e) {
               logger.error("Unable to get data from model class " + e.getMessage());
            }

            String wurflVersion = wurflEngine.getWURFLUtils().getVersion();
            int versionIndex = wurflVersion.indexOf("for WURFL");
            if (versionIndex != -1) {
               int versionEnd = wurflVersion.indexOf(";");
               wurflVersion = versionEnd != -1 ? wurflVersion.substring(versionIndex, versionEnd) : wurflVersion.substring(versionIndex);
            }

            StringBuilder payloadBuilder;
            (payloadBuilder = appendJsonField(
               appendJsonField(
                  appendJsonField(
                     appendJsonField(
                        appendJsonField(
                           appendJsonField(
                              appendJsonField(
                                 appendJsonField(new StringBuilder("{ "), "api-smid", ResourceUtils.getBuildId(), true),
                                 "wurfl-smid",
                                 wurflSmid,
                                 true
                              ),
                              "api",
                              this.getApiName(),
                              true
                           ),
                           "api_ver",
                           "1.9.1.0",
                           true
                        ),
                        "wurfl",
                        wurflVersion,
                        true
                     ),
                     "host",
                     getHostNameOrUnknown(),
                     true
                  ),
                  "os",
                  this.osNameAndVersion,
                  true
               ),
               "platform",
               this.platformName,
               false
            )).append(" }");
            this.payloadJson = payloadBuilder.toString();
         }

      }
   }

   private static StringBuilder appendJsonField(StringBuilder builder, String key, String value, boolean hasMoreFields) {
      builder.append("\"").append(key).append("\": \"").append(value).append("\"");
      if (hasMoreFields) {
         builder.append(", ");
      } else {
         builder.append(" ");
      }

      return builder;
   }

   private static String getHostNameOrUnknown() {
      try {
         return InetAddress.getLocalHost().getHostName();
      } catch (IOException | RuntimeException e) {
         return "unknown";
      }
   }

   public void check() {
      if (this.enabled) {
         ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

         try {
            executorService.execute(new ConnectivityCheckerTask(this));
         } finally {
            executorService.shutdown();
         }

      }
   }

   public synchronized void addObserver(CheckConnectionObserver observer) {
      this.observers.add(observer);
   }

   public synchronized void notifyObservers(Object argument) {
      Iterator<CheckConnectionObserver> iterator = this.observers.iterator();

      while(iterator.hasNext()) {
         iterator.next().update(this, argument);
         logger.info("observer notified");
      }

   }

   final String getPayloadJson() {
      return this.payloadJson;
   }

   public String getApiName() {
      String classPath;
      return (classPath = System.getProperty("java.class.path")) != null && classPath.contains("wurfl-scala") ? "WURFL_Scala_API" : "WURFL_Java_API";
   }
}
