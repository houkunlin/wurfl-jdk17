package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class R extends AbstractA {
  private static String b = "generic_opera_mobi_maemo";

  private static String c = "nokia_generic_maemo_with_firefox";

  private static String d = "nokia_generic_maemo";

  public R(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add(b);
    hashSet.add(c);
    hashSet.add(d);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return paramWURFLRequest.getCleanedDeviceUserAgent().contains("Maemo");
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    return (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Opera Mobi") ? b : (str.contains("Firefox") ? c : d);
  }

  protected final String a(String paramString) {
    int i;
    return ((i = paramString.indexOf("---")) >= 0) ? StringMatchUtils.risMatch(getFilter().a().a(), paramString, i + 3) : super.a(paramString);
  }

  public final String getMatcherName() {
    return "MaemoMatcher";
  }

  public final String getBucketMatcherName() {
    return "Maemo";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\R.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
