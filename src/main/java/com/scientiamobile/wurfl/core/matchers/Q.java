package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class q extends a {
  private static String b = "windows_8_rt_ver1_subos81";
  
  private static String c = "generic_windows_8_rt";
  
  public q(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(c);
    hashSet.add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return StringMatchUtils.containsAllOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Windows NT ", " ARM;", "Trident/" });
  }
  
  protected final String a(String paramString) {
    if (paramString.contains("like Gecko")) {
      int i;
      if ((i = paramString.indexOf(" Gecko")) >= 0)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 6); 
    } else {
      int i;
      if ((i = paramString.indexOf(" ARM;")) >= 0)
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 5); 
    } 
    return null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("like Gecko") ? b : c;
  }
  
  public final String getMatcherName() {
    return "WindowsRTMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "WindowsRT";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\q.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */