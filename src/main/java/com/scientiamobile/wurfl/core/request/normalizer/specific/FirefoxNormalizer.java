package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.StringUtils;

public class FirefoxNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    String str = paramString;
    int i;
    if ((i = paramString.indexOf("Firefox")) >= 0)
      str = StringUtils.substring(paramString, i); 
    return str;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\FirefoxNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
