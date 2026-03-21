package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class D extends a {
  private static String b = "docomo_generic_jap_ver2";
  
  private static String c = "docomo_generic_jap_ver1";
  
  public D(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(c);
    hashSet.add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("DoCoMo"));
  }
  
  protected final String a(String paramString) {
    int i;
    if ((i = StringMatchUtils.secondSlash(paramString)) == -1)
      i = StringMatchUtils.firstOpenParenthesis(paramString); 
    return (i != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : StringMatchUtils.NULL_STRING;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? b : c;
  }
  
  public final String getMatcherName() {
    return "DoCoMoMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "DoCoMo";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */