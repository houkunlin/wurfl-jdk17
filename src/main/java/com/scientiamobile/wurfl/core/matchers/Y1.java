package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class Y extends AbstractA {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsMobileBrowser() && StringUtils.contains(paramWURFLRequest.getCleanedDeviceUserAgent(), "Konqueror"));
  }

  public final String getMatcherName() {
    return "KonquerorMatcher";
  }

  public final String getBucketMatcherName() {
    return "Konqueror";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\Y.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
