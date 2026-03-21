package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class H extends AbstractA {
  private static String b = "firefox";

  public H(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsMobileBrowser() && str.contains("Firefox") && !StringMatchUtils.containsAnyOf(str, new String[] { "Tablet", "Sony", "Novarra", "Opera" }));
  }

  protected final String a(String paramString) {
    int i;
    return ((i = StringMatchUtils.indexOfOrLength(paramString = paramString.substring(paramString.indexOf("Firefox")), ".")) == -1) ? null : StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 1);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return b;
  }

  public final String getMatcherName() {
    return "FirefoxMatcher";
  }

  public final String getBucketMatcherName() {
    return "Firefox";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\H.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
