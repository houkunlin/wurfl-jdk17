package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class U extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(str, "benq"));
  }
  
  public final String getMatcherName() {
    return "BenQMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "BenQ";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\U.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
