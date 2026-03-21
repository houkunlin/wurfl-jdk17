package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Locale;

final class g extends a {
  public g(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && (str.toLowerCase(Locale.US).startsWith("sanyo") || str.contains("MobilePhone")));
  }
  
  protected final String a(String paramString) {
    if (paramString.contains("MobilePhone")) {
      int i = StringMatchUtils.indexOfOrLength(paramString, "/", StringMatchUtils.indexOf(paramString, "MobilePhone"));
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
    } 
    return super.a(paramString);
  }
  
  public final String getMatcherName() {
    return "SanyoMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Sanyo";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\g.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */