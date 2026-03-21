package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class aq extends AbstractA {
  private static String b = "generic_android_ver2_0_opera_mobi";

  private static String c = "generic_android_ver2_1_opera_tablet";

  private static Set d;

  public aq(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    return new HashSet(d);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!paramWURFLRequest._internalIsDesktopBrowser() && paramWURFLRequest.getCleanedDeviceUserAgent().contains("Android") && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Opera Tablet", "Opera Mobi" }));
  }

  protected final String a(String paramString) {
    int i = ((i = paramString.indexOf("---")) == -1) ? paramString.length() : (i + 3);
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str;
    boolean bool = (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Opera Tablet");
    str = UserAgentUtils.getAndroidVersion(str, true);
    str = "generic_android_ver" + str.replaceAll("\\.", "_") + "_opera_" + (bool ? "tablet" : "mobi");
    return d.contains(str) ? str : (bool ? c : b);
  }

  public final String getMatcherName() {
    return "OperaMobiOrTabletOnAndroidMatcher";
  }

  public final String getBucketMatcherName() {
    return "OperaMobiOrTabletOnAndroid";
  }

  static {
    (d = new HashSet<String>()).add("generic_android_ver1_5_opera_mobi");
    d.add("generic_android_ver1_6_opera_mobi");
    d.add(b);
    d.add("generic_android_ver2_1_opera_mobi");
    d.add("generic_android_ver2_2_opera_mobi");
    d.add("generic_android_ver2_3_opera_mobi");
    d.add("generic_android_ver4_0_opera_mobi");
    d.add("generic_android_ver4_1_opera_mobi");
    d.add("generic_android_ver4_2_opera_mobi");
    d.add(c);
    d.add("generic_android_ver2_2_opera_tablet");
    d.add("generic_android_ver2_3_opera_tablet");
    d.add("generic_android_ver3_0_opera_tablet");
    d.add("generic_android_ver3_1_opera_tablet");
    d.add("generic_android_ver3_2_opera_tablet");
    d.add("generic_android_ver4_0_opera_tablet");
    d.add("generic_android_ver4_1_opera_tablet");
    d.add("generic_android_ver4_2_opera_tablet");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\aq.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
