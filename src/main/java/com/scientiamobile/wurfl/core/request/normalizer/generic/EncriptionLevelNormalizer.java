package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class EncriptionLevelNormalizer implements UserAgentNormalizer {
  private static final CharSequence a = new String(" U;");
  
  public String normalize(String paramString) {
    return paramString.replace(a, "");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\EncriptionLevelNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */