package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class E1 extends AbstractA {
  private static final Pattern b = Pattern.compile("^.+?\\(.+?rv:\\d+(\\.)");

  private static String c = "generic_android_ver2_0_fennec";

  private static String d = "generic_android_ver2_0_fennec_tablet";

  private static String e = "generic_android_ver2_0_fennec_desktop";

  public E1(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(c);
    hashSet.add(d);
    hashSet.add(e);
    hashSet.add("generic");
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("Android") && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Fennec", "Firefox" }));
  }

  protected final String a(String paramString) {
    Matcher matcher;
    int i;
    return ((matcher = b.matcher(paramString)).find() && (i = matcher.end()) < paramString.length()) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    if ((str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Fennec") || str.contains("Mobile"))
      return c;
    if (str.contains("Firefox")) {
      if (str.contains("Tablet"))
        return d;
      if (str.contains("Desktop"))
        return e;
    }
    return "generic";
  }

  public final String getMatcherName() {
    return "FennecOnAndroidMatcher";
  }

  public final String getBucketMatcherName() {
    return "FennecOnAndroid";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\E.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
