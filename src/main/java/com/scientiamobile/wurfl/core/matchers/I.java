package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class i extends a {
  private static String b = "generic_smarttv_googletv_browser";
  
  private static String c = "generic_smarttv_appletv_browser";
  
  private static String d = "generic_smarttv_boxeebox_browser";
  
  private static String e = "generic_smarttv_chromecast";
  
  private static String f = "generic_tizen_smarttv_3_0";
  
  private static String g = "generic_tizen_smarttv_2_4";
  
  private static String h = "generic_tizen_smarttv_2_3";
  
  private static String i = "generic_tizen_smarttv";
  
  public i(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic_smarttv_browser");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest._internalIsSmartTvBrowser();
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    return null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Tizen 3.0") ? f : (str.contains("Tizen 2.4") ? g : (str.contains("Tizen 2.3") ? h : (str.contains("Tizen") ? i : (str.contains("SmartTV") ? "generic_smarttv_browser" : (str.contains("GoogleTV") ? b : (str.contains("AppleTV") ? c : (str.contains("Boxee") ? d : (str.contains("CrKey") ? e : "generic_smarttv_browser"))))))));
  }
  
  public final String getMatcherName() {
    return "SmartTvMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "SmartTV";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\i.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */