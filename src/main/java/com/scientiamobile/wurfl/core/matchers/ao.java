package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.commons.collections.CollectionUtils;

final class ao extends a {
  private static final SortedMap b;
  
  public ao(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(b.values());
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Opera Mini", "OperaMini", "Opera Mobi", "OperaMobi" }));
  }
  
  protected final String a(String paramString) {
    int i;
    return ((i = paramString.indexOf("---")) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 3) : (((i = StringMatchUtils.indexOf(paramString, "Opera Mini")) >= 0 && (i = StringMatchUtils.indexOf(paramString, ".", i)) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 1) : (((i = StringMatchUtils.firstSlash(paramString)) != -1) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i) : StringMatchUtils.NULL_STRING));
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str1 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    String str2;
    return ((str2 = (String)CollectionUtils.find(b.keySet(), UserAgentUtils.isContainedIn(str1))) != null) ? (String)b.get(str2) : (str1.contains("Opera Mobi") ? "generic_opera_mini_version4" : "generic_opera_mini_version1");
  }
  
  public final String getMatcherName() {
    return "OperaMiniMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "OperaMini";
  }
  
  static {
    (b = new TreeMap<Object, Object>()).put("Opera Mini/1", "generic_opera_mini_version1");
    b.put("Opera Mini/2", "generic_opera_mini_version2");
    b.put("Opera Mini/3", "generic_opera_mini_version3");
    b.put("Opera Mini/4", "generic_opera_mini_version4");
    b.put("Opera Mini/5", "generic_opera_mini_version5");
    b.put("Opera Mini/6", "generic_opera_mini_version6");
    b.put("Opera Mini/7", "generic_opera_mini_version7");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ao.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */