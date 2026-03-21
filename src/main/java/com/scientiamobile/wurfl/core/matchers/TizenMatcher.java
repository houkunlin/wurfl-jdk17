package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TizenMatcher extends a {
  private static final Pattern b = Pattern.compile("Tizen (\\d+?\\.\\d+?)");
  
  private static final List c = new ArrayList();
  
  private static final List d = new ArrayList();
  
  public TizenMatcher(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(c);
    hashSet.add("generic_tizen");
    return hashSet;
  }
  
  public boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (paramWURFLRequest.getCleanedDeviceUserAgent().startsWith("Mozilla") && paramWURFLRequest.getCleanedDeviceUserAgent().contains("Tizen"));
  }
  
  protected final String a(String paramString) {
    int i;
    return ((i = paramString.indexOf("AppleWebKit/")) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 12) : null;
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    String str2 = paramWURFLRequest.getNormalizedDeviceUserAgent();
    Matcher matcher;
    String str1 = "generic_tizen_ver" + (((matcher = b.matcher(str2)).find() && d.contains(matcher.group(1))) ? matcher.group(1).replace('.', '_') : "1_0");
    return c.contains(str1) ? str1 : "generic_tizen";
  }
  
  public String getMatcherName() {
    return "TizenMatcher";
  }
  
  public String getBucketMatcherName() {
    return "Tizen";
  }
  
  static {
    c.add("generic_tizen_ver1_0");
    c.add("generic_tizen_ver2_0");
    c.add("generic_tizen_ver2_1");
    c.add("generic_tizen_ver2_2");
    c.add("generic_tizen_ver2_3");
    c.add("generic_tizen_ver2_4");
    c.add("generic_tizen_ver3_0");
    d.add("1.0");
    d.add("2.0");
    d.add("2.1");
    d.add("2.2");
    d.add("2.3");
    d.add("2.4");
    d.add("3.0");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\TizenMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */