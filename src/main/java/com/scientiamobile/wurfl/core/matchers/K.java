package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class K extends a {
  private static final Pattern b = Pattern.compile("^.*?HTC.+?[/ ;]");
  
  public K(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic");
    hashSet.add("generic_ms_mobile");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "HTC", "XV6875" }));
  }
  
  protected final String a(String paramString) {
    int i = paramString.length();
    Matcher matcher;
    if ((matcher = b.matcher(paramString)).find())
      i = matcher.group(0).length(); 
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
  }
  
  public final String getMatcherName() {
    return "HTCMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "HTC";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\K.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */