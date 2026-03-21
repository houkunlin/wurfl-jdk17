package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class LocaleNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("; ?[a-z]{2}(?:-r?[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b-?(?!:)");
  
  public String normalize(String paramString) {
    return a.matcher(paramString).replaceAll("; xx-xx");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\LocaleNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */