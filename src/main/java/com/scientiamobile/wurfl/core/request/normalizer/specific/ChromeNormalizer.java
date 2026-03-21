package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class ChromeNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    int i;
    if ((i = paramString.indexOf("Chrome")) >= 0)
      paramString = paramString.substring(i); 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\ChromeNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */