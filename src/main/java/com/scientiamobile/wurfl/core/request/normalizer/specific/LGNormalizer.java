package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class LGNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    int i;
    return ((i = paramString.indexOf("LG")) > 0) ? paramString.substring(i) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\LGNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */