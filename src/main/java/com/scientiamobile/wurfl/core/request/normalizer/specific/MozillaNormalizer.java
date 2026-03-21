package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class MozillaNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("(; [a-z]{2}(-[a-zA-Z]{0,2})?)");
  
  public String normalize(String paramString) {
    return paramString.startsWith("Mozilla/") ? a.matcher(paramString).replaceFirst("") : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\MozillaNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */