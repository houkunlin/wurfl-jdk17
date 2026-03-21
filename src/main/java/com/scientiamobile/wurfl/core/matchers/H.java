package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class h extends AbstractA {
  private static String b = "generic_skyfire_version2";

  private static String c = "generic_skyfire_version1";

  public h(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(c);
    hashSet.add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getCleanedDeviceUserAgent().contains("Skyfire");
  }

  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOf(paramString, "Skyfire");
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, StringMatchUtils.indexOfOrLength(paramString, ".", i));
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? b : c;
  }

  public final String getMatcherName() {
    return "SkyfireMatcher";
  }

  public final String getBucketMatcherName() {
    return "Skyfire";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\h.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
