package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class k extends AbstractA {
  private static String b = "generic_android_ver2_0_ucweb";

  private static Map c;

  public k(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(c.values());
    hashSet.add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(str, new String[] { "Android", "UCWEB7" }));
  }

  protected final String a(String paramString) {
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, StringMatchUtils.indexOfOrLength(paramString, "UCWEB7"));
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str = UserAgentUtils.getAndroidVersion(paramWURFLRequest.getNormalizedDeviceUserAgent(), true);
    return ((str = (String)c.get(str)) != null) ? str : b;
  }

  public final String getMatcherName() {
    return "UCWEB7OnAndroidMatcher";
  }

  public final String getBucketMatcherName() {
    return "Ucweb7OnAndroid";
  }

  static {
    (c = new HashMap<Object, Object>()).put("1.6", "generic_android_ver1_6_ucweb");
    c.put("2.1", "generic_android_ver2_1_ucweb");
    c.put("2.2", "generic_android_ver2_2_ucweb");
    c.put("2.3", "generic_android_ver2_3_ucweb");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\k.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
