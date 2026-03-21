package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

final class n extends AbstractA {
  public n(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("Vodafone"));
  }

  public final String getMatcherName() {
    return "VodafoneMatcher";
  }

  public final String getBucketMatcherName() {
    return "Vodafone";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\n.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
