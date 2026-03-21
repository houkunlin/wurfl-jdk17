package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class ae extends a {
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "kyocera", "KWC-", "QC-" }));
  }
  
  public final String getMatcherName() {
    return "KyoceraMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Kyocera";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ae.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */