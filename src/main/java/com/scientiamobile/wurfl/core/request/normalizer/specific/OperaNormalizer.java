package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class OperaNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("Version/(\\d+\\.\\d+)");
  
  private static final Pattern b = Pattern.compile("OPR/(\\d+\\.\\d+)");
  
  public String normalize(String paramString) {
    Matcher matcher;
    return (paramString.startsWith("Opera/9.80") && (matcher = a.matcher(paramString)).find()) ? StringUtils.replace(paramString, "Opera/9.80", "Opera/" + matcher.group(1)) : ((matcher = b.matcher(paramString)).find() ? ("Opera/" + matcher.group(1) + " " + paramString) : paramString);
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\specific\OperaNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
