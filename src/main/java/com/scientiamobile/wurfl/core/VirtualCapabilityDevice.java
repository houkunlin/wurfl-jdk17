package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class VirtualCapabilityDevice implements Serializable {
  private static final long serialVersionUID = -9083698933173727805L;
  
  private static final Pattern a = Pattern.compile("Windows NT ([0-9]+?\\.[0-9])");
  
  private static final Pattern b = Pattern.compile("Windows [0-9\\.]+");
  
  private static final Pattern c = Pattern.compile("PPC.+OS X ([0-9\\._]+)");
  
  private static final Pattern d = Pattern.compile("PPC.+OS X");
  
  private static final Pattern e = Pattern.compile("Trident/([\\d\\.]+)");
  
  private static final Pattern f = Pattern.compile("Intel Mac OS X ([0-9\\._]+)");
  
  private static final Pattern g = Pattern.compile("\\.");
  
  private final l h;
  
  private final l i;
  
  private static Map j = new HashMap<Object, Object>();
  
  private static Map k = new HashMap<Object, Object>();
  
  private static Map l = new HashMap<Object, Object>();
  
  private String m;
  
  private String n;
  
  private String o;
  
  public String getDeviceUserAgent() {
    return this.m;
  }
  
  public String getBrowserUserAgent() {
    return this.n;
  }
  
  public String getCleanedDeviceUserAgent() {
    return this.o;
  }
  
  public l getBrowserPair() {
    return this.h;
  }
  
  public String getOsPairName() {
    return this.i.a();
  }
  
  public String getBrowserPairName() {
    return this.h.a();
  }
  
  public String getBrowserPairVersion() {
    return this.h.b();
  }
  
  public String getOsPairVersion() {
    return this.i.b();
  }
  
  public l getOsPair() {
    return this.i;
  }
  
  public VirtualCapabilityDevice(WURFLRequest paramWURFLRequest) {
    if (paramWURFLRequest.isUrlEncoded()) {
      this.m = paramWURFLRequest.getCleanedDeviceUserAgent();
      this.n = this.m;
      this.o = paramWURFLRequest.getCleanedDeviceUserAgent();
    } else {
      this.m = paramWURFLRequest.getDeviceUserAgent();
      this.n = paramWURFLRequest.getBrowserUserAgent();
      this.o = paramWURFLRequest.getCleanedDeviceUserAgent();
    } 
    this.h = new l();
    this.i = new l();
  }
  
  public VirtualCapabilityDevice(String paramString1, String paramString2, String paramString3, String paramString4) {
    if (UserAgentUtils.isRawUrlEncoded(paramString4) || UserAgentUtils.hasIIsLoggingStyle(paramString4)) {
      this.m = paramString1;
      this.n = paramString2;
      this.o = paramString3;
    } else {
      this.m = paramString1;
      this.n = paramString2;
      this.o = paramString3;
    } 
    this.h = new l();
    this.i = new l();
  }
  
  public void normalizeOS() {
    if (this.i.a() != null && StringMatchUtils.indexOf(this.m, "Windows") >= 0) {
      Matcher matcher;
      if ((matcher = a.matcher(this.i.a())).find()) {
        this.i.a("Windows");
        this.i.b(j.containsKey(matcher.group(1)) ? (String)j.get(matcher.group(1)) : matcher.group());
        return;
      } 
      if (b.matcher(this.i.a()).find())
        return; 
    } 
    if (StringMatchUtils.indexOf(this.i.a(), "Windows Phone") >= 0 && this.i.b() != null && l.containsKey(this.i.b()))
      getOsPair().b((String)l.get(this.i.b())); 
    if (this.i.a(this.m, c, "Mac OS X", (String)null)) {
      if (this.i.b() != null)
        this.i.b(this.i.b().replaceAll("_", ".")); 
      return;
    } 
    if (this.i.a(this.m, d, "Mac OS X", (String)null))
      return; 
    if (this.i.a(this.m, f, "Mac OS X", 1)) {
      if (this.i.b() != null) {
        this.i.b(this.i.b().replaceAll("_", "."));
        String[] arrayOfString;
        if (StringUtils.isNotEmpty(this.i.b()) && (arrayOfString = g.split(this.i.b())) != null && arrayOfString.length > 1 && StringUtils.isNumeric(arrayOfString[0]) && StringUtils.isNumeric(arrayOfString[1]) && Integer.parseInt(arrayOfString[0]) >= 10 && Integer.parseInt(arrayOfString[1]) >= 12) {
          this.i.a("macOS");
          return;
        } 
      } 
      return;
    } 
    if (this.i.a(this.m, "Mac_PowerPC", "Mac OS X"))
      return; 
    if (this.i.a(this.m, "CrOS", "Chrome OS"))
      return; 
    if (!StringUtils.isEmpty(this.i.a()))
      return; 
    if (StringMatchUtils.indexOf(this.m, "Linux") >= 0 || StringMatchUtils.indexOf(this.m, "X11") >= 0) {
      this.i.a("Linux");
      return;
    } 
  }
  
  public void normalizeBrowser() {
    Matcher matcher = e.matcher(this.m);
    if ("IE".equals(this.h.a()) && matcher.find()) {
      String str = matcher.group(1);
      if (k.containsKey(str) && !(str = (String)k.get(str)).equals(this.h.b()))
        this.h.b(str + "(Compatibility View)"); 
    } 
  }
  
  static {
    j.put("4.0", "NT 4.0");
    j.put("5.0", "2000");
    j.put("5.1", "XP");
    j.put("5.2", "XP");
    j.put("6.0", "Vista");
    j.put("6.1", "7");
    j.put("6.2", "8");
    j.put("6.3", "8.1");
    j.put("6.4", "10");
    j.put("10.0", "10");
    k.put("4.0", "8.0");
    k.put("5.0", "9.0");
    k.put("6.0", "10.0");
    k.put("7.0", "11.0");
    l.put("7.10", "7.5");
    l.put("8.10", "8.1");
    l.put("8.15", "10");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\VirtualCapabilityDevice.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
