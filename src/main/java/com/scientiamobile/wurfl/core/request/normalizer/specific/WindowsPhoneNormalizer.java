package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Pattern;

public class WindowsPhoneNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("Windows ?Phone ?Ad ?Client/[0-9\\.]+ ?\\(.+; ?Windows ?Phone(?: ?OS)? ?[0-9\\.]+; ?([^;\\)]+(; ?[^;\\)]+)?)");
  
  public String normalize(String paramString) {
    String str1 = null;
    String str2 = null;
    if (StringMatchUtils.containsAnyOf(paramString, new String[] { "WPDesktop", "ZuneWP7" }) || StringMatchUtils.containsAllOf(paramString, new String[] { "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/" })) {
      str1 = UserAgentUtils.getWindowsPhoneDesktopModel(paramString);
      str2 = UserAgentUtils.getWindowsPhoneDesktopVersion(paramString);
    } else if (UserAgentUtils.isWindowsPhoneAdClient(paramString)) {
      str1 = UserAgentUtils.cleanAndReplaceWindowsPhoneModel(paramString, new Pattern[] { a });
      str2 = UserAgentUtils.getWindowsPhoneVersion(paramString);
    } else if (!paramString.contains("NativeHost")) {
      str1 = UserAgentUtils.getWindowsPhoneModel(paramString);
      str2 = UserAgentUtils.getWindowsPhoneVersion(paramString);
    } 
    return (str1 != null && str2 != null) ? ("WP" + str2 + " " + str1 + "---" + paramString) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\WindowsPhoneNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */