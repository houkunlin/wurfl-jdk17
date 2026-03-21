package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class al extends a {
  private static String b = "nokia_generic_series60";
  
  private static String c = "nokia_generic_series80";
  
  private static String d = "nokia_generic_meego";
  
  public al(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    hashSet.add(d);
    hashSet.add("generic_mobile");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("Nokia") && !StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Android", "iPhone" }));
  }
  
  protected final String a(String paramString) {
    int i = StringMatchUtils.indexOfAnyOrLength(paramString, new String[] { "/", " " }, paramString.indexOf("Nokia"));
    if (StringMatchUtils.startsWithAnyOf(paramString, new String[] { "Nokia/", "Nokia " }))
      i = paramString.length(); 
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return StringUtils.contains(str = paramWURFLRequest.getNormalizedDeviceUserAgent(), "Series60") ? b : (StringUtils.contains(str, "Series80") ? c : (StringUtils.contains(str, "MeeGo") ? d : "generic"));
  }
  
  public final String getMatcherName() {
    return "NokiaMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Nokia";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\al.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
