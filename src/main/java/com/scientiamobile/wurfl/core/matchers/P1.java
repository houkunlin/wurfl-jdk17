package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class P1 extends AbstractA {
  private static String b = "generic_lguplus";

  private static final Map<String, String[]> c = new LinkedHashMap<>();

  public P1(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set<String> a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<>()).addAll(c.keySet());
    hashSet.add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "lgtelecom", "LGUPLUS" }));
  }

  protected final String a(WURFLRequest paramWURFLRequest) {
    return null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    for (Map.Entry entry : c.entrySet()) {
      if (StringMatchUtils.containsAllOf(paramWURFLRequest.getNormalizedDeviceUserAgent(), (String[])entry.getValue()))
        return (String)entry.getKey();
    }
    return b;
  }

  public final String getMatcherName() {
    return "LGUPLUSMatcher";
  }

  public final String getBucketMatcherName() {
    return "LGUPLUS";
  }

  static {
    (c = new LinkedHashMap<Object, Object>()).put("generic_lguplus_rexos_facebook_browser", new String[] { "Windows NT 5", "POLARIS" });
    c.put("generic_lguplus_rexos_webviewer_browser", new String[] { "Windows NT 5" });
    c.put("generic_lguplus_winmo_facebook_browser", new String[] { "Windows CE", "POLARIS" });
    c.put("generic_lguplus_android_webkit_browser", new String[] { "Android", "AppleWebKit" });
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\P.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
