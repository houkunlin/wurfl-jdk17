package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperaMiniNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("^Opera/[\\d\\.]+ .+?\\d{3}X\\d{3} (.+)$");
  
  public String normalize(String paramString) {
    Matcher matcher;
    return (matcher = a.matcher(paramString)).matches() ? (matcher.group(1) + "---" + paramString) : paramString;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\OperaMiniNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */