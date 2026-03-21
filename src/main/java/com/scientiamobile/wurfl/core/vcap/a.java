package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.InternalDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityDevice;
import com.scientiamobile.wurfl.core.VirtualCapabilityUserAgentTool;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

abstract class a implements VirtualCapabilityEvaluator, Serializable {
  private static final long serialVersionUID = 8192401578396133213L;
  
  protected static final Pattern c = Pattern.compile("Mozilla/5.0 \\(Linux;( U;)? Android.*AppleWebKit.*\\(KHTML, like Gecko\\)");
  
  protected static final Pattern d = Pattern.compile("^Mozilla/5.0 \\(Linux;( U;)? Android [1234]\\.[\\d\\.]+(-update1)?; [a-zA-Z]+-[a-zA-Z]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ (Mobile )?Safari/[\\d\\.+]+$");
  
  protected static final Pattern e = Pattern.compile("Chrome/(\\d+)\\.");
  
  protected static final Pattern f = Pattern.compile("Android [1234]\\.[123]");
  
  static final Set g = new HashSet();
  
  private static Set a = new HashSet();
  
  static final Set h = new HashSet();
  
  protected static final List i = new ArrayList();
  
  private static List b = new ArrayList(3);
  
  private static final Pattern j = Pattern.compile("^(\\d+(?:\\.\\d+)?).*");
  
  protected static boolean a(String paramString1, String paramString2) {
    return ("iOS".equals(paramString1) && !paramString2.contains("Safari"));
  }
  
  protected static boolean a(Device paramDevice, String paramString, WURFLRequest paramWURFLRequest) {
    VirtualCapabilityDevice virtualCapabilityDevice = VirtualCapabilityUserAgentTool.getInstance().assignProperties(paramWURFLRequest, (InternalDevice)paramDevice);
    return ("Mac OS X".equals(virtualCapabilityDevice.getOsPairName()) && !paramString.contains("Safari"));
  }
  
  protected static boolean a(String paramString1, String paramString2, WURFLRequest paramWURFLRequest) {
    return a(paramString1, paramString2, paramWURFLRequest.getHeader("X-Requested-With"));
  }
  
  protected static boolean a(String paramString1, String paramString2, String paramString3) {
    return (paramString1.equals(paramString2) && StringUtils.isNotEmpty(paramString3) && a.contains(paramString3));
  }
  
  protected static boolean a(WURFLRequest paramWURFLRequest) {
    Map map = paramWURFLRequest.getHeaders();
    String str2 = paramWURFLRequest.isUrlEncoded() ? paramWURFLRequest.getCleanedDeviceUserAgent() : paramWURFLRequest.getOriginalUserAgent();
    String str1;
    if (map.containsKey("Accept-Encoding") && str2.contains("Trident/") && (str1 = (String)map.get("Accept-Encoding")) != null && !str1.contains("deflate"))
      return true; 
    for (String str : b) {
      if (str2.contains(str))
        return false; 
    } 
    return paramWURFLRequest._internalIsBot();
  }
  
  protected static boolean a(Device paramDevice) {
    int i;
    try {
      i = Integer.parseInt(paramDevice.getCapability("resolution_width"));
    } catch (NumberFormatException numberFormatException) {
      return false;
    } 
    if ("false".equals(paramDevice.getCapability("is_wireless_device")))
      return false; 
    if ("true".equals(paramDevice.getCapability("is_tablet")))
      return false; 
    if ("false".equals(paramDevice.getCapability("can_assign_phone_number")))
      return false; 
    if (!"touchscreen".equals(paramDevice.getCapability("pointing_method")))
      return false; 
    if (i < 320)
      return false; 
    String str2 = paramDevice.getCapability("device_os_version");
    Matcher matcher = j.matcher(str2);
    float f = 0.0F;
    boolean bool;
    if (bool = matcher.matches())
      try {
        f = Float.parseFloat(matcher.group(1));
      } catch (NumberFormatException numberFormatException) {
        bool = false;
      }  
    String str1 = paramDevice.getCapability("device_os");
    return "iOS".equals(str1) ? ((bool && f >= 3.0F)) : ("Android".equals(str1) ? ((bool && f >= 2.2F)) : ("Windows Phone OS".equals(str1) ? true : ("RIM OS".equals(str1) ? ((bool && f >= 7.0D)) : ("webOS".equals(str1) ? true : ("MeeGo".equals(str1) ? true : ("Bada OS".equals(str1) ? ((bool && f >= 2.0D)) : false))))));
  }
  
  static {
    b.add("CUBOT");
    b.add("Cubot");
    b.add("Botswana");
    i.add("^Dalvik");
    i.add("Darwin/");
    i.add("CFNetwork");
    i.add("^Windows Phone Ad Client");
    i.add("^NativeHost");
    i.add("^AndroidDownloadManager");
    i.add("-HttpClient");
    i.add("^AppCake");
    i.add("AppEngine-Google");
    i.add("AppleCoreMedia");
    i.add("^AppTrailers");
    i.add("^ChoiceFM");
    i.add("^ClassicFM");
    i.add("^Clipfish");
    i.add("^FaceFighter");
    i.add("^Flixster");
    i.add("^Gold/");
    i.add("^GoogleAnalytics/");
    i.add("^Heart/");
    i.add("^iBrowser/");
    i.add("iTunes-");
    i.add("^Java/");
    i.add("^LBC/3.");
    i.add("Twitter");
    i.add("Pinterest");
    i.add("^Instagram");
    i.add("FBAN");
    i.add("#iP(hone|od|ad)[\\d],[\\d]");
    i.add("#com(?:\\.[a-z]+){2,}");
    i.add("#net(?:\\.[a-z]+){2,}");
    i.add("WebView");
    i.add("FB_IAB");
    i.add("FB4A");
    i.add("MobileApp");
    i.add("DesktopApp");
    i.add("^mShop:::");
    i.add(" GSA/");
    g.add("com.android.browser");
    g.add("com.htc.sense.browser");
    g.add("com.asus.browser");
    g.add("com.google.android.browser");
    g.add("com.lenovo.browser");
    g.add("com.huawei.android.browser");
    a.add("com.facebook.katana");
    a.add("com.ksmobile.cb");
    a.add("com.nhn.android.search");
    a.add("app.staples");
    a.add("flipboard.app");
    a.add("com.google.android.apps.magazines");
    a.add("com.pandora.android");
    a.add("com.stumbleupon.android.app");
    h.add("UCBrowser");
    h.add("Opera");
    h.add(" OPR/");
    h.add("YaBrowser");
    h.add("MiuiBrowser");
    h.add("MQQBrowser");
    h.add("CriOS");
    h.add("Firefox");
  }
  
  static {
    Pattern.compile("^Mozilla/5.0 \\(Linux; Android [45]\\.[\\d\\.]+; .+ Build/.+\\) AppleWebKit/[\\d\\.+]+ \\(KHTML, like Gecko\\) Version/[\\d\\.]+ Chrome/([\\d]+)\\.[\\d\\.]+? (?:Mobile )?Safari/[\\d\\.+]+$");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\vcap\a.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */