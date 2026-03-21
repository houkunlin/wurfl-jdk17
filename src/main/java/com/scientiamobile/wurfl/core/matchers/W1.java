package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang.StringUtils;

final class W extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(paramWURFLRequest.getCleanedDeviceUserAgent(), "grundig"));
  }
  
  public final String getMatcherName() {
    return "GrundigMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Grundig";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\W.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */