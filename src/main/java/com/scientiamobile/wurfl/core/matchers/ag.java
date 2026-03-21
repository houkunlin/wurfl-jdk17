package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang.StringUtils;

final class ag extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(paramWURFLRequest.getCleanedDeviceUserAgent(), "sharp"));
  }
  
  public final String getMatcherName() {
    return "SharpMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Sharp";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */