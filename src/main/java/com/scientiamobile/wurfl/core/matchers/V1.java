package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class V extends AbstractA {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("Toshiba"));
  }

  public final String getMatcherName() {
    return "ToshibaMatcher";
  }

  public final String getBucketMatcherName() {
    return "Toshiba";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\V.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
