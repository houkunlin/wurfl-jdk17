package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang.StringUtils;

public class AndroidNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    String str1 = UserAgentUtils.getAndroidVersion(paramString, false);
    String str2 = UserAgentUtils.getAndroidModel(paramString);
    return (!StringUtils.isEmpty(str1) && !StringUtils.isEmpty(str2)) ? (str1 + " " + str2 + "---" + paramString) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\AndroidNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */