package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class X1 extends AbstractA {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("portalmmm"));
  }

  public final String getMatcherName() {
    return "PortalmmmMatcher";
  }

  public final String getBucketMatcherName() {
    return "Portalmmm";
  }

  protected final String a(String paramString) {
    return null;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\X.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
