package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class O extends a {
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic");
    return hashSet;
  }
  
  public O(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(paramWURFLRequest.getCleanedDeviceUserAgent(), "lg"));
  }
  
  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfOrLength(paramString, "/", paramString.indexOf("LG"));
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    G g;
    return ((str = StringMatchUtils.risMatch((g = getFilter().a()).a(), paramWURFLRequest.getNormalizedDeviceUserAgent(), 7)) != null) ? g.a(str) : "generic";
  }
  
  public final String getMatcherName() {
    return "LGMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "LG";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\O.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
