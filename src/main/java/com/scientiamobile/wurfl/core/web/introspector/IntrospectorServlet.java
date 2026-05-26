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
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntrospectorServlet extends HttpServlet implements WurflWebConstants {
   private static final long serialVersionUID = 1L;
   private static WURFLEngine wurflEngine = null;
   private final ObjectMapper objectMapper = new ObjectMapper();
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
   private static final Pattern LINE_BREAK_PATTERN = Pattern.compile("[\r\n]+");
   private static final Logger log = LoggerFactory.getLogger(IntrospectorServlet.class);
   private static String apiVersion;

   public static void setWURFLEngine(WURFLEngine wurflEngine) {
      IntrospectorServlet.wurflEngine = wurflEngine;
   }

   public void init(ServletConfig config) throws ServletException {
      super.init(config);
      this.getServletContext().getServerInfo();
      (new StringBuilder()).append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.version"));
      (new StringBuilder()).append(System.getProperty("java.vendor")).append(" ").append(System.getProperty("java.version"));
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.currentTimeMillis();
      String action = request.getParameter("action");
      boolean handled = false;
      response.setContentType("text/plain");
      PrintWriter out = response.getWriter();
      if ("Request".equalsIgnoreCase(action)) {
         wurflEngine.setEngineTarget(EngineTarget.accuracy);
         handled = this.handleRequest(request, out);
      } else if ("Info".equalsIgnoreCase(action)) {
         wurflEngine.setEngineTarget(EngineTarget.accuracy);
         boolean var10000;
         if (wurflEngine == null) {
            writeMissingEngineError(out);
            var10000 = true;
         } else {
            EngineTarget engineTarget = wurflEngine.getEngineTarget();
            IntrospectorInfoResponse responseBody = new IntrospectorInfoResponse();
            responseBody.apiVersion = apiVersion;
            responseBody.wurflVersion = wurflEngine.getWURFLUtils().getVersion();
            responseBody.engineTarget = engineTarget.name();
            responseBody.userAgentPriority = wurflEngine.getUserAgentPriority().name();
            responseBody.serverInfo = this.getServletContext().getServerInfo();
            responseBody.osName = System.getProperty("os.name");
            responseBody.osVersion = System.getProperty("os.version");
            responseBody.javaVendor = System.getProperty("java.vendor");
            responseBody.javaVersion = System.getProperty("java.version");
            String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
            out.println(json);
            var10000 = true;
         }

         handled = var10000;
      } else if ("form".equalsIgnoreCase(action)) {
         wurflEngine.setEngineTarget(EngineTarget.valueOf(request.getParameter("wurflEngineTarget")));
         wurflEngine.setUserAgentPriority(UserAgentPriority.valueOf(request.getParameter("wurflUserAgentPriority")));
         handled = this.handleRequest(request, out);
      } else if ("Buckets".equalsIgnoreCase(action)) {
         handled = this.writeBuckets(out);
      }

      if (!handled) {
         out.println("action " + action + " not supported");
      }

      out.flush();
   }

   private boolean writeBuckets(PrintWriter out) {
      if (wurflEngine == null) {
         writeMissingEngineError(out);
         return true;
      } else {
         try {
            out.println("WURFL Java API " + apiVersion);
            Field modelField;
            boolean originalModelAccess = (modelField = GeneralWURFLEngine.class.getDeclaredField("wurflModel")).canAccess(wurflEngine);
            modelField.setAccessible(true);
            WURFLModel wurflModel = (WURFLModel)modelField.get(wurflEngine);
            Field holderField;
            boolean originalHolderAccess = (holderField = GeneralWURFLEngine.class.getDeclaredField("wurflService")).canAccess(wurflEngine);
            holderField.setAccessible(true);
            Object wurflHolder = holderField.get(wurflEngine);
            ArrayList<String> resultLines = null;
            if (wurflModel != null && wurflHolder != null) {
               Field engineTargetField;
               boolean originalEngineTargetAccess = (engineTargetField = wurflHolder.getClass().getDeclaredField("engineTarget")).canAccess(wurflHolder);
               engineTargetField.setAccessible(true);
               EngineTarget originalEngineTarget = (EngineTarget)engineTargetField.get(wurflHolder);
               Field matcherManagerField;
               boolean originalMatcherManagerAccess = (matcherManagerField = wurflHolder.getClass().getDeclaredField("matcherManager")).canAccess(wurflHolder);
               matcherManagerField.setAccessible(true);
               MatcherManager matcherManager = (MatcherManager)matcherManagerField.get(wurflHolder);
               Set allDevices = wurflModel.getAllDevices();
               ArrayList<String> userAgents = new ArrayList<>(allDevices.size());
               Iterator var25 = allDevices.iterator();

               while(var25.hasNext()) {
                  ModelDevice device = (ModelDevice)var25.next();
                  if (!StringUtils.isEmpty(device.getUserAgent())) {
                     userAgents.add(device.getUserAgent());
                  }
               }

               engineTargetField.set(wurflHolder, EngineTarget.accuracy);
               resultLines = new ArrayList<>(userAgents.size());
               ArrayList<MatchResultRow> matchResults = new ArrayList<>(userAgents.size());
               DefaultWURFLRequestFactory requestFactory = new DefaultWURFLRequestFactory();
               log.info("BUCKETS (1/3): start matching...");
               long start = System.currentTimeMillis();

               for(String ua : userAgents) {
                  DeviceInfo deviceInfo = matcherManager.matchRequest(requestFactory.createRequest(ua, EngineTarget.accuracy));
                  String matcherName = readStringField("matcherName", deviceInfo);
                  String normalizedUserAgent = readStringField("normalizedUserAgent", deviceInfo);
                  String originalUserAgent = readStringField("originalUserAgent", deviceInfo);
                  matchResults.add(new MatchResultRow(matcherName, deviceInfo.getId(), normalizedUserAgent, originalUserAgent));
               }

               log.info("BUCKETS (1/3): finished matching. Took " + (System.currentTimeMillis() - start) + " ms");
               log.info("BUCKETS (2/3): start sorting...");
               start = System.currentTimeMillis();
               Collections.sort(matchResults);
               log.info("BUCKETS (2/3): finished sorting. Took " + (System.currentTimeMillis() - start) + " ms");
               log.info("BUCKETS (3/3): start building strings...");
               start = System.currentTimeMillis();

               for(MatchResultRow row : matchResults) {
                  resultLines.add(row.toString());
               }

               log.info("BUCKETS (3/3): finished building strings. Took " + (System.currentTimeMillis() - start) + " ms");
               engineTargetField.set(wurflHolder, originalEngineTarget);
               matcherManagerField.setAccessible(originalMatcherManagerAccess);
               engineTargetField.setAccessible(originalEngineTargetAccess);
            }

            holderField.setAccessible(originalHolderAccess);
            modelField.setAccessible(originalModelAccess);

            for(Object lineObj : resultLines) {
               out.println((String)lineObj);
            }

            return true;
         } catch (Exception var23) {
            log.debug(var23.getClass().getSimpleName() + " - " + var23.getMessage());
            return false;
         }
      }
   }

   private static String readStringField(String fieldName, Object target) {
      try {
         Field field;
         boolean originalAccess = (field = target.getClass().getDeclaredField(fieldName)).canAccess(target);
         field.setAccessible(true);
         String value = (String)field.get(target);
         field.setAccessible(originalAccess);
         return value;
      } catch (Exception var5) {
         return null;
      }
   }

   private boolean handleRequest(HttpServletRequest request, PrintWriter out) throws IOException {
      if (wurflEngine == null) {
         writeMissingEngineError(out);
         return true;
      } else {
         String uaProfile;
         if ((uaProfile = request.getParameter("uaprof")) == null || uaProfile.trim().length() == 0) {
            uaProfile = request.getHeader("x-wap-profile");
         }

         if (uaProfile == null) {
            uaProfile = request.getHeader("X-Wap-Profile");
         }

         HeaderOnlyHttpServletRequest headerOnlyRequest = new HeaderOnlyHttpServletRequest();
         if (request.getParameter("form") == null) {
            HashMap<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();

            while(headerNames.hasMoreElements()) {
               String headerName = headerNames.nextElement().toString();
               if ("User-Agent".equalsIgnoreCase(headerName)) {
                  headers.put("User-Agent", request.getHeader(headerName));
               } else {
                  headers.put(headerName, request.getHeader(headerName));
               }
            }

            headerOnlyRequest.addHeaders(headers);
            UserAgentUtils.getUserAgent(request);
         } else {
            String userAgent;
            if ((userAgent = request.getParameter("ua")) == null || userAgent.trim().length() <= 0) {
               userAgent = request.getHeader("User-Agent");
            }

            if (userAgent != null) {
               headerOnlyRequest.addHeader("User-Agent", userAgent);
            }

            String rawHeaders;
            if ((rawHeaders = request.getParameter("headers")) != null && rawHeaders.trim().length() > 0) {
               rawHeaders = rawHeaders.trim();
               String[] headerPairs = LINE_BREAK_PATTERN.matcher(rawHeaders).replaceAll("|").split("\\|");

               for(int i = 0; i < headerPairs.length; ++i) {
                  String headerPair = headerPairs[i];
                  if (headerPair.indexOf(":") >= 0) {
                     String[] headerKeyValue = headerPair.split(":");
                     headerOnlyRequest.addHeader(headerKeyValue[0].trim(), headerKeyValue[1].trim());
                  }
               }
            }
         }

         if (uaProfile != null) {
            headerOnlyRequest.addHeader("X-Wap-Profile", uaProfile);
         }

         String rawCapabilities = request.getParameter("capabilities");
         String[] capabilities = null;
         if (rawCapabilities != null && rawCapabilities.trim().length() > 0) {
            rawCapabilities = rawCapabilities.trim();
            capabilities = LINE_BREAK_PATTERN.matcher(rawCapabilities).replaceAll("|").split("\\|");
         }

         IntrospectorRequestResponse responseBody = new IntrospectorRequestResponse();
         Device device = wurflEngine.getDeviceForRequest((HttpServletRequest)headerOnlyRequest);
         responseBody.deviceId = device.getId();
         responseBody.userAgent = headerOnlyRequest.getHeader("User-Agent");
         responseBody.requestType = request.getParameter("form") == null ? "request" : "form";
         if (capabilities != null && capabilities.length > 0) {
            HashMap<String, String> capabilityMap = new HashMap<>();

            for(int i = 0; i < capabilities.length; ++i) {
               String capabilityName = capabilities[i].trim();
               capabilityMap.put(capabilityName, device.getCapability(capabilityName));
            }
            responseBody.capabilities = capabilityMap;
         }

         String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseBody);
         out.println(json);
         return true;
      }
   }

   private static void writeMissingEngineError(PrintWriter out) {
      StringBuilder message;
      (message = new StringBuilder()).append("INTROSPECTOR SERVLET ERROR!\n");
      message.append("\n");
      message.append("No WURFLEngine was initialized in the IntrospectorServlet.\n");
      message.append("To use IntrospectorServlet, please call the method:\n");
      message.append("\tpublic static void IntrospectorServlet.setWURFLEngine(WURFLEngine wurflEngine)\n");
      message.append("to provide the servlet with the running WURFL instance.\n");
      message.append("\n");
      message.append("For more details, see documentation.\n");
      out.println(message.toString());
   }

   static {
      String resourcePath = "/META-INF/maven/com.scientiamobile.wurfl/wurfl/pom.properties";
      InputStream pomPropertiesStream = IntrospectorServlet.class.getResourceAsStream(resourcePath);
      Properties pomProperties = new Properties();

      try {
         pomProperties.load(pomPropertiesStream);
         apiVersion = "1.9.1.0";
      } catch (IOException var8) {
         apiVersion = "(Unavailable...)";
      } finally {
         try {
            pomPropertiesStream.close();
         } catch (Exception var7) {
         }

      }

      if (apiVersion == null || "".equals(apiVersion)) {
         apiVersion = "(Unavailable...)";
      }

      log.info("WURFL core library running version " + pomProperties.get("version"));
   }
}
