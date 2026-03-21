package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class e extends AbstractA {
  public e(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic_web_browser");
    hashSet.add("generic_xhtml");
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(str, new String[] { "Safari" }) && StringMatchUtils.startsWithAnyOf(str, new String[] { "Mozilla/5.0 (Macintosh", "Mozilla/5.0 (Windows" }));
  }

  protected final String a(String paramString) {
    int i;
    return ((i = paramString.indexOf("---")) != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 3) : null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return (StringUtils.contains(str = paramWURFLRequest.getNormalizedDeviceUserAgent(), "Macintosh") || StringUtils.contains(str, "Windows")) ? "generic_web_browser" : "generic_xhtml";
  }

  public final String getMatcherName() {
    return "SafariMatcher";
  }

  public final String getBucketMatcherName() {
    return "Safari";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\e.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
