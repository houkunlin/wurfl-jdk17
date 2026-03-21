package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTCMacNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("; [a-z]{2}(?:-[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b ");
  
  private static final Pattern b = Pattern.compile("(HTC[^;\\)]+)");
  
  public String normalize(String paramString) {
    paramString = a.matcher(paramString).replaceFirst("; xx-xx");
    Matcher matcher;
    if ((matcher = b.matcher(paramString)).find()) {
      String str = matcher.group();
      return str.replaceAll("[ _\\-/]", "~") + "---" + paramString;
    } 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\HTCMacNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */