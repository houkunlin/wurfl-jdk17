package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class N1 extends AbstractA {
  private static String b = "generic_amazon_kindle";

  private static final Map<String, String> c = new LinkedHashMap<>();

  public N1(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<>()).add(b);
    hashSet.addAll(c.values());
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str;
    return ((str = paramWURFLRequest.getCleanedDeviceUserAgent()).contains("Android") && StringMatchUtils.containsAnyOf(str, new String[] { "/Kindle", "Silk" })) ? false : StringMatchUtils.containsAnyOf(str, new String[] { "Kindle", "Silk" });
  }

  protected final String a(String paramString) {
    int i;
    if ((i = paramString.indexOf("Build/")) != -1)
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
    i += 7;
    char c;
    return ((i = paramString.indexOf("Kindle/")) >= 0 && (c = paramString.charAt(i)) >= '1' && c <= '3') ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 1) : (((i = paramString.indexOf("PlayStation Vita")) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 16 + 1) : null);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getNormalizedDeviceUserAgent();
    for (Map.Entry<String, String> entry : c.entrySet()) {
      if (str.contains(entry.getKey()))
        return entry.getValue();
    }
    return b;
  }

  public final String getMatcherName() {
    return "KindleMatcher";
  }

  public final String getBucketMatcherName() {
    return "Kindle";
  }

  static {
    (c = new LinkedHashMap<Object, Object>()).put("Kindle/1", "amazon_kindle_ver1");
    c.put("Kindle/2", "amazon_kindle2_ver1");
    c.put("Kindle/3", "amazon_kindle3_ver1");
    c.put("Kindle Fire", "amazon_kindle_fire_ver1");
    c.put("Silk", "amazon_kindle_fire_ver1");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\N.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
