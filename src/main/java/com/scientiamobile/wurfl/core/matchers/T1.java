package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class T extends AbstractA {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("SIE-"));
  }

  public final String getMatcherName() {
    return "SiemensMatcher";
  }

  public final String getBucketMatcherName() {
    return "Siemens";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\T.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
