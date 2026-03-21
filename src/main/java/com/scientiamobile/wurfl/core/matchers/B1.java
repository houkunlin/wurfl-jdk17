package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class B extends a {
  private static String b = "google_chrome";
  
  public B(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsMobileBrowser() && StringUtils.contains(paramWURFLRequest.getCleanedDeviceUserAgent(), "Chrome"));
  }
  
  protected final String a(String paramString) {
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, StringMatchUtils.indexOfOrLength(paramString, "."));
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return b;
  }
  
  public final String getMatcherName() {
    return "ChromeMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Chrome";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\B.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
