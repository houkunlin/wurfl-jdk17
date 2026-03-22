package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.resource.DefaultWURFLModel;
import com.scientiamobile.wurfl.core.resource.ResourceUtils;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CheckConnection extends Observable {
  private final transient Logger a = LoggerFactory.getLogger(getClass());

  private final List b = new ArrayList<>();

  private String c;

  private String d = System.getProperty("os.name") + " " + System.getProperty("os.version");

  private String e;

  private boolean f;

  public CheckConnection() {
    String str;
    this.e = StringUtils.isNotEmpty(System.getProperty("jboss.boot.library.list")) ? "JBoss/WildFly" : (StringUtils.isNotEmpty(System.getProperty("oracle.j2ee.home")) ? "OC4j/Oracle AS" : (((str = System.getProperty("java.class.path").toLowerCase()).contains("websphere") || str.contains("web sphere")) ? "IBM WebSphere" : ((str.contains("tomcat") || str.contains("juli")) ? "Tomcat" : (str.contains("jetty") ? "Jetty" : "Command line"))));
    this.f = ResourceUtils.getFullBuildId().startsWith("ch");
  }

  public void setup(WURFLEngine paramWURFLEngine, WURFLModel paramWURFLModel) {
    if (!this.f)
      return;
    String str = "unknown";
    if (paramWURFLModel instanceof DefaultWURFLModel) {
      DefaultWURFLModel defaultWURFLModel = (DefaultWURFLModel)paramWURFLModel;
      try {
        Field field;
        (field = DefaultWURFLModel.class.getDeclaredField("g")).setAccessible(true);
        str = StringUtils.isEmpty(str = (String)field.get(defaultWURFLModel)) ? "unknown" : str;
      } catch (Exception exception) {
        this.a.error("Unable to get data from model class " + exception.getMessage());
      }
      WURFLEngine wURFLEngine = paramWURFLEngine;
      CheckConnection checkConnection = this;
      String str1;
      int i;
      StringBuilder stringBuilder;
      int j;
      (stringBuilder = a(a(a(a(a(a(a(a(new StringBuilder("{ "), "api-smid", ResourceUtils.getBuildId(), true), "wurfl-smid", str, true), "api", checkConnection.getApiName(), true), "api_ver", "1.9.0.0", true), "wurfl", ((i = (str1 = wURFLEngine.getWURFLUtils().getVersion()).indexOf("for WURFL")) == -1) ? str1 : (((j = str1.indexOf(";")) != -1) ? str1.substring(i, j) : str1.substring(i)), true), "host", a(), true), "os", checkConnection.d, true), "platform", checkConnection.e, false)).append(" }");
      this.c = stringBuilder.toString();
    }
  }

  private static StringBuilder a(StringBuilder paramStringBuilder, String paramString1, String paramString2, boolean paramBoolean) {
    paramStringBuilder.append("\"").append(paramString1).append("\": \"").append(paramString2).append("\"");
    if (paramBoolean) {
      paramStringBuilder.append(", ");
    } else {
      paramStringBuilder.append(" ");
    }
    return paramStringBuilder;
  }

  private static String a() {
    try {
      return InetAddress.getLocalHost().getHostName();
    } catch (Exception exception) {
      return "unknown";
    }
  }

  public void check() {
    if (!this.f)
      return;
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    try {
      scheduledExecutorService.execute(new ConnectivityChecker(this));
      return;
    } finally {
      scheduledExecutorService.shutdown();
    }
  }

  public synchronized void addObserver(Observer paramObserver) {
    this.b.add(paramObserver);
  }

  public void notifyObservers(Object paramObject) {
    Iterator<Observer> iterator = this.b.iterator();
    while (iterator.hasNext()) {
      ((Observer)iterator.next()).update(this, paramObject);
      this.a.info("observer notified");
    }
  }

  public String getApiName() {
    String str;
    return ((str = System.getProperty("java.class.path")) != null && str.contains("wurfl-scala")) ? "WURFL_Scala_API" : "WURFL_Java_API";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\CheckConnection.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
