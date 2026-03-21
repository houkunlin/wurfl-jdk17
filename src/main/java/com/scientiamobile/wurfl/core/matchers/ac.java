package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class ac extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("Mitsu"));
  }
  
  public final String getMatcherName() {
    return "MitsubishiMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Mitsubishi";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ac.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */