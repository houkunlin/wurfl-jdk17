package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class BlackBerryNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("(?i)black(?i)berry");
  
  public String normalize(String paramString) {
    int i;
    if ((i = (paramString = a.matcher(paramString).replaceAll("BlackBerry")).indexOf("BlackBerry")) > 0)
      paramString = paramString.substring(i); 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\BlackBerryNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */