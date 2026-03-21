package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFNetworkNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("CFNetwork/(\\d+\\.?[0-9]*)");
  
  public String normalize(String paramString) {
    Matcher matcher;
    if ((matcher = a.matcher(paramString)).find()) {
      String str = (new BigDecimal(matcher.group(1))).setScale(2, RoundingMode.HALF_DOWN).toString();
      StringBuilder stringBuilder = new StringBuilder();
      if (paramString.contains("x86_64")) {
        stringBuilder.append("CFNetworkDesktop/").append(str).append(" ").append(paramString);
      } else {
        stringBuilder.append("CFNetwork/").append(str).append(" ").append(paramString);
      } 
      return stringBuilder.toString();
    } 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\CFNetworkNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */