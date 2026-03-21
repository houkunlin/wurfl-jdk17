package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class u extends AbstractA {
  private static String b = "generic_android";

  private static String c = "generic_android_ver2_2";

  private static String d = "generic_android_ver1_5_tablet";

  private static final Set e = new HashSet();

  private static final Set f = new HashSet();

  public u(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  protected final Set a() {
    HashSet<?> hashSet;
    (hashSet = new HashSet()).addAll(e);
    hashSet.addAll(f);
    hashSet.add(b);
    return hashSet;
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    return (!StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "like Android", "Symbian" }) && StringMatchUtils.containsAnyOf(paramWURFLRequest.getCleanedDeviceUserAgent(), new String[] { "Android", "android" }));
  }

  protected final String a(String paramString) {
    int j;
    if ((j = paramString.indexOf("---")) >= 0) {
      j += 3;
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, j);
    }
    if (!StringMatchUtils.startsWithAnyOf(paramString, new String[] { "Mozilla", "Dalvik" }))
      return null;
    String str;
    if ((str = UserAgentUtils.getAndroidModel(paramString)) == null || str.length() == 0) {
      int k = paramString.length();
      return StringMatchUtils.risMatch(getFilter().a().a(), paramString, k);
    }
    int i = Math.min(StringMatchUtils.indexOfOrLength(paramString, " Build/"), StringMatchUtils.indexOfOrLength(paramString, " AppleWebKit"));
    return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    String str1;
    if ((str1 = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("Froyo"))
      return c;
    String str2 = UserAgentUtils.getAndroidVersion(str1, true).replaceAll("\\.", "_");
    if ((str2 = "generic_android_ver" + str2).endsWith("2_0") || str2.endsWith("4_0"))
      str2 = str2.substring(0, str2.length() - 2);
    if (!str2.startsWith("generic_android_ver3_") && !str1.contains("Mobile") && str1.contains("Safari")) {
      str2 = str2.concat("_tablet");
      return f.contains(str2) ? str2 : d;
    }
    return e.contains(str2) ? str2 : b;
  }

  public final String getMatcherName() {
    return "AndroidMatcher";
  }

  public final String getBucketMatcherName() {
    return "Android";
  }

  static {
    e.add("generic_android_ver1_5");
    e.add("generic_android_ver1_6");
    e.add("generic_android_ver2");
    e.add("generic_android_ver2_1");
    e.add(c);
    e.add("generic_android_ver2_3");
    e.add("generic_android_ver3_0");
    e.add("generic_android_ver3_1");
    e.add("generic_android_ver3_2");
    e.add("generic_android_ver3_3");
    e.add("generic_android_ver4");
    e.add("generic_android_ver4_1");
    e.add("generic_android_ver4_2");
    e.add("generic_android_ver4_3");
    e.add("generic_android_ver4_4");
    e.add("generic_android_ver4_5");
    e.add("generic_android_ver5_0");
    e.add("generic_android_ver5_1");
    e.add("generic_android_ver5_2");
    e.add("generic_android_ver5_3");
    e.add("generic_android_ver6_0");
    e.add("generic_android_ver6_1");
    e.add("generic_android_ver7_0");
    e.add("generic_android_ver7_1");
    e.add("generic_android_ver7_2");
    e.add("generic_android_ver8_0");
    e.add("generic_android_ver8_1");
    f.add(d);
    f.add("generic_android_ver1_6_tablet");
    f.add("generic_android_ver2_tablet");
    f.add("generic_android_ver2_1_tablet");
    f.add("generic_android_ver2_2_tablet");
    f.add("generic_android_ver2_3_tablet");
    f.add("generic_android_ver4_tablet");
    f.add("generic_android_ver4_1_tablet");
    f.add("generic_android_ver4_2_tablet");
    f.add("generic_android_ver4_3_tablet");
    f.add("generic_android_ver4_4_tablet");
    f.add("generic_android_ver4_5_tablet");
    f.add("generic_android_ver5_0_tablet");
    f.add("generic_android_ver5_1_tablet");
    f.add("generic_android_ver5_2_tablet");
    f.add("generic_android_ver5_3_tablet");
    f.add("generic_android_ver6_0_tablet");
    f.add("generic_android_ver6_1_tablet");
    f.add("generic_android_ver7_0_tablet");
    f.add("generic_android_ver7_1_tablet");
    f.add("generic_android_ver7_2_tablet");
    f.add("generic_android_ver8_0_tablet");
    f.add("generic_android_ver8_1_tablet");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matcher\\u.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
