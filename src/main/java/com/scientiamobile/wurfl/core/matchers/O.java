package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class o extends AbstractA {
  private static String b = "hp_tablet_webos_generic";

  private static String c = "hp_webos_generic";

  public o(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "webOS", "hpwOS" }));
  }

  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfOrLength(paramString, "---");
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("hpwOS/3") ? b : c;
  }

  public final String getMatcherName() {
    return "WebOSMatcher";
  }

  public final String getBucketMatcherName() {
    return "WebOS";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\o.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
