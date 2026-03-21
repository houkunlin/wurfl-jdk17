package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

public class UbuntuTouchOSMatcher extends AbstractA {
  private static String b = "generic_ubuntu_touch_os";

  private static String c = "generic_ubuntu_touch_os_tablet";

  public UbuntuTouchOSMatcher(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    return hashSet;
  }

  public boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (paramWURFLRequest.getCleanedDeviceUserAgent().contains("Ubuntu") && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Mobile", "Tablet" }));
  }

  protected final String a(String paramString) {
    int i;
    if ((i = paramString.indexOf("like Android")) >= 0) {
      i += 12;
    } else if ((i = paramString.indexOf("WebKit/")) >= 0) {
      i += 7;
    }
    return (i >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("Tablet") ? c : b;
  }

  public String getMatcherName() {
    return "UbuntuTouchOSMatcher";
  }

  public String getBucketMatcherName() {
    return "UbuntuTouchOS";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\UbuntuTouchOSMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
