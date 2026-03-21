package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebOSNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile(" ([^/]+)/([\\d\\.]+)$");
  
  private static final Pattern b = Pattern.compile("(?:hpw|web)OS.(\\d)\\.");
  
  public String normalize(String paramString) {
    String str4 = paramString;
    Matcher matcher2;
    String str1 = (matcher2 = b.matcher(str4)).find() ? "webOS".concat(matcher2.group(1)) : null;
    String str3 = paramString;
    Matcher matcher1;
    String str2 = (matcher1 = a.matcher(str3)).find() ? (matcher1.group(1) + " " + matcher1.group(2)) : null;
    return (str1 != null && str2 != null) ? (str2 + " " + str1 + "---" + paramString) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\WebOSNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */