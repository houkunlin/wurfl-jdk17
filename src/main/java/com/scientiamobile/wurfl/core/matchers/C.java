package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

final class c extends AbstractA {
  private static String b = "generic_reksio";

  public c(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringUtils.startsWith(paramWURFLRequest.getCleanedDeviceUserAgent(), "Reksio"));
  }

  protected final String a(WURFLRequest paramWURFLRequest) {
    return b;
  }

  public final String getMatcherName() {
    return "ReksioMatcher";
  }

  public final String getBucketMatcherName() {
    return "Reksio";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\c.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
