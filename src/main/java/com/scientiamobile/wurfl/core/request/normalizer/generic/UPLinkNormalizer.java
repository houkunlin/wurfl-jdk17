package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang.Validate;

public class UPLinkNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    Validate.notNull(paramString, "The userAgent is null");
    int i;
    if ((i = paramString.indexOf("UP.Link")) >= 0)
      paramString = paramString.substring(0, i); 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\UPLinkNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */