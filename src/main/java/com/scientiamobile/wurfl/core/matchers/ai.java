package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class ai extends AbstractA {
  public ai(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "NEC-", "KGT" }));
  }

  public final String getMatcherName() {
    return "NecMatcher";
  }

  public final String getBucketMatcherName() {
    return "Nec";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ai.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
