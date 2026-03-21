package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.regex.Pattern;

public class UCWebNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("^(JUC \\(Linux; U;)(?= \\d)");
  
  private static final Pattern b = Pattern.compile("(Android|JUC|[;\\)])(?=[\\w|\\(])");
  
  public String normalize(String paramString) {
    if (StringMatchUtils.startsWithAnyOf(paramString, new String[] { "JUC", "Mozilla/5.0(Linux;U;Android" })) {
      paramString = a.matcher(paramString).replaceFirst("$1 Android");
      paramString = b.matcher(paramString).replaceAll("$1 ");
    } 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\UCWebNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */