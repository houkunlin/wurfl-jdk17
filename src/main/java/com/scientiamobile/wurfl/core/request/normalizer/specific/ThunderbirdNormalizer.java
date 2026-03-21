package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang.StringUtils;

public class ThunderbirdNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    int i;
    return ((i = StringMatchUtils.indexOf(paramString, "Thunderbird")) >= 0) ? StringUtils.substring(paramString, i) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\ThunderbirdNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */