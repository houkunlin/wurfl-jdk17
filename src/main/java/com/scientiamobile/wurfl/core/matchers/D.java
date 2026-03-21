package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

final class d extends AbstractA {
  public d(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.contains(paramWURFLRequest.getCleanedDeviceUserAgent(), "SPV"));
  }

  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfOrLength(paramString, ";", StringMatchUtils.indexOfOrLength(paramString, "SPV"));
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  public final String getMatcherName() {
    return "SPVMatcher";
  }

  public final String getBucketMatcherName() {
    return "SPV";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\d.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
