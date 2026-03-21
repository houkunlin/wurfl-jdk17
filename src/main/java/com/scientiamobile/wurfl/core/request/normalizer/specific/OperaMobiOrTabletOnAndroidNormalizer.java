package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

public class OperaMobiOrTabletOnAndroidNormalizer implements UserAgentNormalizer {
  public String normalize(String paramString) {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append(paramString.contains("Opera Mobi") ? "Opera Mobi" : "Opera Tablet").append(" ");
    String str;
    if ((str = UserAgentUtils.getOperaOnAndroidVersion(paramString, false)) != null)
      stringBuilder.append(str).append(" "); 
    stringBuilder.append("Android");
    if ((str = UserAgentUtils.getAndroidVersion(paramString, false)) != null)
      stringBuilder.append(" ").append(str); 
    stringBuilder.append("---").append(paramString);
    return stringBuilder.toString();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\OperaMobiOrTabletOnAndroidNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */