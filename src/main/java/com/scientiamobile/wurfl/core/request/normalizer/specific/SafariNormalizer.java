package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class SafariNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    i += 8;
    int i;
    int j;
    return ((i = paramString.indexOf("Version/")) != -1 && (j = paramString.indexOf('.', i)) != -1) ? ("Safari " + paramString.substring(i, j) + "---" + paramString) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\SafariNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */