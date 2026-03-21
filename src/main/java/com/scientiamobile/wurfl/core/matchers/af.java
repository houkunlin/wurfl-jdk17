package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Locale;

final class af extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel"));
  }
  
  public final String getMatcherName() {
    return "AlcatelMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Alcatel";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\af.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */