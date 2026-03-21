package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class b extends AbstractA {
  public b(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(str, new String[] { "Pantech", "PT-", "PANTECH", "PG-" }));
  }

  public final String getMatcherName() {
    return "PantechMatcher";
  }

  public final String getBucketMatcherName() {
    return "Pantech";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\b.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
