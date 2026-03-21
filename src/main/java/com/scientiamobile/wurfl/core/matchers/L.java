package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class l extends AbstractA {
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic_ucweb");
    return hashSet;
  }

  public l(UserAgentNormalizer paramUserAgentNormalizer, WURFLModel paramWURFLModel) {
    super(paramUserAgentNormalizer, paramWURFLModel);
  }

  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return (!paramWURFLRequest._internalIsDesktopBrowser() && str.startsWith("UCWEB") && str.contains("UCBrowser"));
  }

  protected final String a(String paramString) {
    if (UserAgentUtils.getUcBrowserVersion(paramString, true) == null)
      return null;
    int i;
    if ((i = paramString.indexOf("---")) > 0) {
      i += 3;
      String str = paramString.substring(i);
      if (paramString.contains("Adr")) {
        str = UserAgentUtils.getUcAndroidModel(paramString, false);
        String str1 = UserAgentUtils.getUcAndroidVersion(paramString, false);
        if (str != null && str1 != null)
          return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
      } else if (paramString.contains("iPh OS")) {
        if (UcwebU2Normalizer.IPHONE.matcher(str).find())
          return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
      } else if (paramString.contains("wds")) {
        if (UcwebU2Normalizer.WINDOWS_PHONE.matcher(str).find())
          return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
      } else if (paramString.contains("Symbian")) {
        if (UcwebU2Normalizer.SYMBIAN.matcher(str).find())
          return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
      } else if (paramString.contains("Java") && UcwebU2Normalizer.JAVA.matcher(str).find()) {
        return StringMatchUtils.risMatch(getFilter().a().a(), paramString, i);
      }
    }
    return null;
  }

  protected final String b(WURFLRequest paramWURFLRequest) {
    return "generic_ucweb";
  }

  public final String getMatcherName() {
    return "UcwebU2Matcher";
  }

  public final String getBucketMatcherName() {
    return "UcwebU2";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\l.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
