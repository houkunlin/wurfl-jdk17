package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class am extends a {
  private static String b = "nokia_generic_series30plus";
  
  private static String c = "nokia_generic_series40_ovibrosr";
  
  public am(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("S40OviBrowser"));
  }
  
  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfAnyOrLength(paramString, new String[] { "/", " " }, paramString.indexOf("Nokia"));
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("Series30Plus") ? b : c;
  }
  
  public final String getMatcherName() {
    return "NokiaOviBrowserMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "NokiaOviBrowser";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\am.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */