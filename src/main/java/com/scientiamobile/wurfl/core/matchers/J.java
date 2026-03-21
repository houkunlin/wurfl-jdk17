package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class j extends a {
  public j(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("Sony"));
  }
  
  protected final String a(String paramString) {
    if (paramString.startsWith("SonyEricsson")) {
      int k = StringMatchUtils.firstSlash(paramString);
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, k - 2);
    } 
    int i;
    return ((i = StringMatchUtils.secondSlash(paramString)) != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : StringMatchUtils.NULL_STRING;
  }
  
  public final String getMatcherName() {
    return "SonyEricssonMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "SonyEricsson";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\j.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */