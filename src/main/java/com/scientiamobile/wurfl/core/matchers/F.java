package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class f extends a {
  private static final String[] b = new String[] { "SEC-", "SAMSUNG-", "SCH" };
  
  private static final String[] c = new String[] { "Samsung", "SPH", "SGH" };
  
  private static final String[] d = new String[] { "SEC-", "SPH", "SGH", "SCH" };
  
  public f(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return paramWURFLRequest.getOriginalUserAgent().contains("SamsungBrowser") ? false : ((!paramWURFLRequest._internalIsDesktopBrowser() && (StringMatchUtils.startsWithAnyOf(str, d) || str.toLowerCase().contains("samsung"))));
  }
  
  protected final String a(String paramString) {
    String str;
    int i;
    return ((i = StringMatchUtils.startsWithAnyOf(str = paramString, b) ? StringMatchUtils.firstSlash(str) : (StringMatchUtils.startsWithAnyOf(str, c) ? StringMatchUtils.firstSpace(str) : StringMatchUtils.secondSlash(str))) == -1) ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    int i = StringMatchUtils.indexOf(str = paramWURFLRequest.getNormalizedDeviceUserAgent(), "Samsung");
    i = StringMatchUtils.indexOfOrLength(str, "/", i);
    return !StringUtils.isBlank(str = StringMatchUtils.risMatch(getFilter().a().a(), str, i)) ? getFilter().a().a(str) : "generic";
  }
  
  public final String getMatcherName() {
    return "SamsungMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Samsung";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\f.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */