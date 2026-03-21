package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class J1 extends AbstractA {
  private static String b = "generic_android_htc_disguised_as_mac";

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }

  public J1(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str;
    return ((str = paramWURFLRequest.getCleanedDeviceUserAgent()).startsWith("Mozilla/5.0 (Macintosh") && str.contains("HTC"));
  }

  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfOrLength(paramString, "---");
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return b;
  }

  public final String getMatcherName() {
    return "HTCMacMatcher";
  }

  public final String getBucketMatcherName() {
    return "HTCMac";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\J.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
