package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class p extends a {
  private static String b = "generic_ms_phone_os7";
  
  private static String c = "generic_ms_phone_os7_desktopmode";
  
  private static String d = "generic_ms_phone_os7_5_desktopmode";
  
  private static String e = "generic_ms_phone_os8_desktopmode";
  
  private static String f = "generic_ms_phone_os10_desktopmode";
  
  private static final Map g;
  
  public p(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(g.values());
    hashSet.add(c);
    hashSet.add(d);
    hashSet.add(e);
    hashSet.add(f);
    hashSet.add("generic");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && (StringMatchUtils.containsAnyOf(str, new String[] { "WPDesktop", "ZuneWP7" }) || StringMatchUtils.containsAllOf(str, new String[] { "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/" }) || StringMatchUtils.containsAnyOf(str, new String[] { "Windows Phone", "WindowsPhone", "NativeHost" })));
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    String str;
    boolean bool;
    return ((bool = (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("---")) && (StringMatchUtils.containsAnyOf(str, new String[] { "WPDesktop", "ZuneWP7" }) || StringMatchUtils.containsAllOf(str, new String[] { "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/" }) || UserAgentUtils.isWindowsPhoneAdClient(paramWURFLRequest.getCleanedDeviceUserAgent()))) ? super.a(paramWURFLRequest) : ((!bool && str.contains("NativeHost")) ? b : super.a(paramWURFLRequest));
  }
  
  protected final String a(String paramString) {
    int i;
    return ((i = paramString.indexOf("---")) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 3) : null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str1;
    boolean bool1 = StringMatchUtils.containsAnyOf(str1 = paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "WPDesktop", "ZuneWP7" });
    boolean bool2 = false;
    if (!bool1)
      bool2 = StringMatchUtils.containsAllOf(str1, new String[] { "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/" }); 
    if (bool1 || bool2)
      return bool2 ? f : (str1.contains("WPDesktop") ? e : (str1.contains("Trident/5.0") ? d : c)); 
    String str2 = UserAgentUtils.getWindowsPhoneVersion(str1);
    return ((str2 = (String)g.get(str2)) != null) ? str2 : (UserAgentUtils.isWindowsPhoneAdClient(str1) ? b : "generic");
  }
  
  public final String getMatcherName() {
    return "WindowsPhoneMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "WindowsPhone";
  }
  
  static {
    (g = new HashMap<Object, Object>()).put("10.0", "generic_ms_phone_os10");
    g.put("8.1", "generic_ms_phone_os8_1");
    g.put("8.0", "generic_ms_phone_os8");
    g.put("7.8", "generic_ms_phone_os7_8");
    g.put("7.5", "generic_ms_phone_os7_5");
    g.put("7.0", b);
    g.put("6.5", "generic_ms_winmo6_5");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\p.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */