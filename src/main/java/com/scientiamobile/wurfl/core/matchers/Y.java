package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class y extends a {
  public y(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return StringMatchUtils.startsWithAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Mozilla/3", "Mozilla/4", "Mozilla/5" });
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    String str2 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    String str3 = str2;
    y y1 = this;
    int i;
    String str1 = ((i = StringMatchUtils.firstCloseParenthesis(str3)) != -1) ? StringMatchUtils.risMatch(y1.getFilter().a().a(), str3, i) : StringMatchUtils.NULL_STRING;
    str3 = "generic";
    if (str1 != null)
      str3 = getFilter().a().a(str1); 
    if (str3 == null)
      str3 = "generic"; 
    return str3;
  }
  
  public final String getMatcherName() {
    return "CatchAllMozillaMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "CatchAllMozilla";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\y.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */