package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class r extends a {
  private static String b = "microsoft_xboxone_ver1";
  
  private static String c = "microsoft_xbox360_ver1_subie10";
  
  private static String d = "microsoft_xbox360_ver1";
  
  public r(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    hashSet.add(d);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getCleanedDeviceUserAgent().contains("Xbox");
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    return null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("MSIE 10.0") ? (str.contains("Xbox One") ? b : c) : d;
  }
  
  public final String getMatcherName() {
    return "XBoxMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Xbox";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\r.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */