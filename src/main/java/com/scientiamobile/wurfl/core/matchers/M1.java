package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class M extends a {
  private static String b = "opwv_v62_generic";
  
  public M(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("KDDI-"));
  }
  
  protected final String a(String paramString) {
    String str;
    int i;
    return ((i = (str = paramString).startsWith("KDDI/") ? StringMatchUtils.secondSlash(str) : StringMatchUtils.firstSlash(str)) == -1) ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return b;
  }
  
  public final String getMatcherName() {
    return "KDDIMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Kddi";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\M.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */