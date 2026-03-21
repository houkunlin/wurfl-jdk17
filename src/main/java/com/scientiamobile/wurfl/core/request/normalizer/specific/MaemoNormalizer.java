package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaemoNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("Maemo [bB]rowser [\\d\\.]+ (.+)");
  
  public String normalize(String paramString) {
    Matcher matcher;
    if ((matcher = a.matcher(paramString)).find()) {
      String str;
      int i;
      if ((i = (str = matcher.group(1)).indexOf(" GTB")) == -1)
        i = str.length(); 
      return "Maemo " + str.substring(0, i) + "---" + paramString;
    } 
    return paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\MaemoNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */