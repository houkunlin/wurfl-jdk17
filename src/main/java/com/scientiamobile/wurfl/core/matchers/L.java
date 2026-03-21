package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class L extends a {
  private static String b = "generic_midp_midlet";
  
  public L(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getCleanedDeviceUserAgent().contains("UNTRUSTED/1.0");
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    return b;
  }
  
  public final String getMatcherName() {
    return "JavaMidletMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "JavaMidlet";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\L.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */