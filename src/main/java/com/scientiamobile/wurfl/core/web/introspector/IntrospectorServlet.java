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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
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
  
  public static void setWURFLEngine(WURFLEngine paramWURFLEngine) {
    a = paramWURFLEngine;
  }
  
  public void init(ServletConfig paramServletConfig) {
    super.init(paramServletConfig);
    getServletContext().getServerInfo();
    (new StringBuilder()).append(System.getProperty("os.name")).append(" ").append(System.getProperty("os.version"));
    (new StringBuilder()).append(System.getProperty("java.vendor")).append(" ").append(System.getProperty("java.version"));
  }
  
  protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
    doPost(paramHttpServletRequest, paramHttpServletResponse);
  }
  
  protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse) {
    System.currentTimeMillis();
    String str = paramHttpServletRequest.getParameter("action");
    boolean bool = false;
    paramHttpServletResponse.setContentType("text/plain");
    PrintWriter printWriter = paramHttpServletResponse.getWriter();
    if ("Request".equalsIgnoreCase(str)) {
      a.setEngineTarget(EngineTarget.accuracy);
      bool = a(paramHttpServletRequest, printWriter);
    } else {
      String str1;
      if ("Info".equalsIgnoreCase(str)) {
        a.setEngineTarget(EngineTarget.accuracy);
        PrintWriter printWriter1 = printWriter;
        IntrospectorServlet introspectorServlet = this;
        b(printWriter1);
        a.getWURFLUtils().getVersion();
        EngineTarget engineTarget = a.getEngineTarget();
        (new StringBuilder("high-")).append(engineTarget.name());
        c c = new c();
        str1 = introspectorServlet.b.defaultPrettyPrintingWriter().writeValueAsString(c);
        printWriter1.println(str1);
        boolean bool1 = (a == null) ? true : true;
      } else if ("form".equalsIgnoreCase(str)) {
        a.setEngineTarget(EngineTarget.valueOf(str1.getParameter("wurflEngineTarget")));
        a.setUserAgentPriority(UserAgentPriority.valueOf(str1.getParameter("wurflUserAgentPriority")));
        bool = a((HttpServletRequest)str1, printWriter);
      } else if ("Buckets".equalsIgnoreCase(str)) {
        bool = a(printWriter);
      } 
    } 
    if (!bool)
      printWriter.println("action " + str + " not supported"); 
    printWriter.flush();
  }
  
  private boolean a(PrintWriter paramPrintWriter) {
    if (a == null) {
      b(paramPrintWriter);
      return true;
    } 
    try {
      paramPrintWriter.println("WURFL Java API " + e);
      Field field1;
      boolean bool1 = (field1 = GeneralWURFLEngine.class.getDeclaredField("o")).isAccessible();
      field1.setAccessible(true);
      WURFLModel wURFLModel = (WURFLModel)field1.get(a);
      Field field2;
      boolean bool2 = (field2 = GeneralWURFLEngine.class.getDeclaredField("l")).isAccessible();
      field2.setAccessible(true);
      Object object = field2.get(a);
      ArrayList<String> arrayList = null;
      if (wURFLModel != null && object != null) {
        Field field3;
        boolean bool3 = (field3 = object.getClass().getDeclaredField("engineTarget")).isAccessible();
        field3.setAccessible(true);
        EngineTarget engineTarget = (EngineTarget)field3.get(object);
        Field field4;
        boolean bool4 = (field4 = object.getClass().getDeclaredField("matcherManager")).isAccessible();
        field4.setAccessible(true);
        MatcherManager matcherManager = (MatcherManager)field4.get(object);
        Set set = wURFLModel.getAllDevices();
        ArrayList<String> arrayList2 = new ArrayList(set.size());
        Iterator<ModelDevice> iterator = set.iterator();
        while (iterator.hasNext()) {
          ModelDevice modelDevice;
          if (!StringUtils.isEmpty((modelDevice = iterator.next()).getUserAgent()))
            arrayList2.add(modelDevice.getUserAgent()); 
        } 
        field3.set(object, EngineTarget.accuracy);
        arrayList = new ArrayList(arrayList2.size());
        ArrayList<b> arrayList1 = new ArrayList(arrayList2.size());
        DefaultWURFLRequestFactory defaultWURFLRequestFactory = new DefaultWURFLRequestFactory();
        d.info("BUCKETS (1/3): start matching...");
        long l = System.currentTimeMillis();
        for (String str1 : arrayList2) {
          DeviceInfo deviceInfo = matcherManager.matchRequest(defaultWURFLRequestFactory.createRequest(str1, EngineTarget.accuracy));
          String str2 = a("matcherName", deviceInfo);
          String str3 = a("normalizedUserAgent", deviceInfo);
          String str4 = a("originalUserAgent", deviceInfo);
          arrayList1.add(new b(str2, deviceInfo.getId(), str3, str4, (byte)0));
        } 
        d.info("BUCKETS (1/3): finished matching. Took " + (System.currentTimeMillis() - l) + " ms");
        d.info("BUCKETS (2/3): start sorting...");
        l = System.currentTimeMillis();
        Collections.sort(arrayList1);
        d.info("BUCKETS (2/3): finished sorting. Took " + (System.currentTimeMillis() - l) + " ms");
        d.info("BUCKETS (3/3): start building strings...");
        l = System.currentTimeMillis();
        for (b b : arrayList1)
          arrayList.add(b.toString()); 
        d.info("BUCKETS (3/3): finished building strings. Took " + (System.currentTimeMillis() - l) + " ms");
        field3.set(object, engineTarget);
        field4.setAccessible(bool4);
        field3.setAccessible(bool3);
      } 
      field2.setAccessible(bool2);
      field1.setAccessible(bool1);
      for (String str : arrayList)
        paramPrintWriter.println(str); 
      return true;
    } catch (Exception exception) {
      d.debug(exception.getClass().getSimpleName() + " - " + exception.getMessage());
      return false;
    } 
  }
  
  private static String a(String paramString, Object paramObject) {
    Field field;
    boolean bool = (field = paramObject.getClass().getDeclaredField(paramString)).isAccessible();
    field.setAccessible(true);
    paramObject = field.get(paramObject);
    field.setAccessible(bool);
    return (String)paramObject;
  }
  
  private boolean a(HttpServletRequest paramHttpServletRequest, PrintWriter paramPrintWriter) {
    if (a == null) {
      b(paramPrintWriter);
      return true;
    } 
    String str1;
    if ((str1 = paramHttpServletRequest.getParameter("uaprof")) == null || str1.trim().length() == 0)
      str1 = paramHttpServletRequest.getHeader("x-wap-profile"); 
    if (str1 == null)
      str1 = paramHttpServletRequest.getHeader("X-Wap-Profile"); 
    e e = new e();
    if (paramHttpServletRequest.getParameter("form") == null) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      Enumeration<E> enumeration = paramHttpServletRequest.getHeaderNames();
      while (enumeration.hasMoreElements()) {
        String str = enumeration.nextElement().toString();
        if ("User-Agent".equalsIgnoreCase(str)) {
          hashMap.put("User-Agent", paramHttpServletRequest.getHeader(str));
          continue;
        } 
        hashMap.put(str, paramHttpServletRequest.getHeader(str));
      } 
      e.a(hashMap);
      UserAgentUtils.getUserAgent(paramHttpServletRequest);
    } else {
      String str4;
      if ((str4 = paramHttpServletRequest.getParameter("ua")) == null || str4.trim().length() <= 0)
        str4 = paramHttpServletRequest.getHeader("User-Agent"); 
      if (str4 != null)
        e.a("User-Agent", str4); 
      String str5;
      if ((str5 = paramHttpServletRequest.getParameter("headers")) != null && str5.trim().length() > 0) {
        str5 = str5.trim();
        String[] arrayOfString1 = c.matcher(str5).replaceAll("|").split("\\|");
        for (byte b = 0; b < arrayOfString1.length; b++) {
          String str;
          if ((str = arrayOfString1[b]).indexOf(":") >= 0) {
            String[] arrayOfString2 = str.split(":");
            e.a(arrayOfString2[0].trim(), arrayOfString2[1].trim());
          } 
        } 
      } 
    } 
    if (str1 != null)
      e.a("X-Wap-Profile", str1); 
    String str2 = paramHttpServletRequest.getParameter("capabilities");
    String[] arrayOfString = null;
    if (str2 != null && str2.trim().length() > 0) {
      str2 = str2.trim();
      arrayOfString = c.matcher(str2).replaceAll("|").split("\\|");
    } 
    d d = new d();
    Device device;
    (device = a.getDeviceForRequest(e)).getId();
    device.toString();
    if (arrayOfString != null && arrayOfString.length > 0) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      for (byte b = 0; b < arrayOfString.length; b++) {
        String str = arrayOfString[b].trim();
        hashMap.put(str, device.getCapability(str));
      } 
    } 
    String str3 = this.b.defaultPrettyPrintingWriter().writeValueAsString(d);
    paramPrintWriter.println(str3);
    return true;
  }
  
  private static void b(PrintWriter paramPrintWriter) {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append("INTROSPECTOR SERVLET ERROR!\n");
    stringBuilder.append("\n");
    stringBuilder.append("No WURFLHolder was initialized in the IntrospectorServlet.\n");
    stringBuilder.append("To use IntrospectorServlet, please call the method:\n");
    stringBuilder.append("\tpublic static void IntrospectorServlet.setWURFLHolder(WURFLHolder holder)\n");
    stringBuilder.append("to provide the servlet with the running WURFL instance.\n");
    stringBuilder.append("\n");
    stringBuilder.append("For more details, see documentation.\n");
    paramPrintWriter.println(stringBuilder.toString());
  }
  
  static {
    String str = "/META-INF/maven/com.scientiamobile.wurfl/wurfl/pom.properties";
    InputStream inputStream = IntrospectorServlet.class.getResourceAsStream(str);
    null = new Properties();
    try {
      null.load(inputStream);
      e = "1.9.0.0";
    } catch (IOException iOException) {
      e = "(Unavailable...)";
    } finally {
      try {
        inputStream.close();
      } catch (Exception exception) {}
    } 
    if (e == null || "".equals(e))
      e = "(Unavailable...)"; 
    d.info("WURFL core library running version " + SYNTHETIC_LOCAL_VARIABLE_1.get("version"));
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\web\introspector\IntrospectorServlet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
