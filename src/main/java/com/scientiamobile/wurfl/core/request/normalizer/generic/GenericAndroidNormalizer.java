package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class GenericAndroidNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("Android[ \\-\\/](\\d\\.\\d)[^; \\/\\)]+");
  
  public String normalize(String paramString) {
    return a.matcher(paramString).replaceAll("Android $1");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\GenericAndroidNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */