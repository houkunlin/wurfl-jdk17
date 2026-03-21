package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcwebU3Normalizer implements UserAgentNormalizer {
  public static final Pattern IPHONE = Pattern.compile("iPhone OS (\\d+)_(\\d+)(?:_\\d+)* like");
  
  public static final Pattern IPAD = Pattern.compile("CPU OS (\\d+)_(\\d+)?.+like Mac.+; iPad([0-9,]+)\\) AppleWebKit");
  
  public String normalize(String paramString) {
    String str1;
    if ((str1 = UserAgentUtils.getUcBrowserVersion(paramString, false)) == null)
      return paramString; 
    String str2 = null;
    if (paramString.contains("Windows Phone")) {
      String str3 = UserAgentUtils.getWindowsPhoneVersion(paramString);
      String str4;
      if ((str4 = UserAgentUtils.getWindowsPhoneModel(paramString)) != null && str3 != null)
        str2 = str3 + " U3WP " + str1 + " " + str4 + "---"; 
    } else if (paramString.contains("Android")) {
      String str3 = UserAgentUtils.getAndroidModel(paramString);
      String str4 = UserAgentUtils.getAndroidVersion(paramString, false);
      if (str3 != null && str4 != null)
        str2 = str4 + " U3Android " + str1 + " " + str3 + "---"; 
    } else if (paramString.contains("iPhone")) {
      Matcher matcher;
      if ((matcher = IPHONE.matcher(paramString)).find()) {
        String str = matcher.group(1) + "." + ((matcher.group(2) == null) ? "" : matcher.group(2));
        str2 = str + " U3iPhone " + str1 + "---";
      } 
    } else {
      Matcher matcher;
      if (paramString.contains("iPad") && (matcher = IPAD.matcher(paramString)).find()) {
        String str4 = matcher.group(1);
        str2 = matcher.group(2);
        str2 = str4 + "." + ((str2 == null) ? "" : str2);
        String str3 = matcher.group(3);
        str2 = str2 + " U3iPad " + str1 + " " + str3 + "---";
      } 
    } 
    return (str2 == null) ? paramString : (str2 + paramString);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\UcwebU3Normalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */