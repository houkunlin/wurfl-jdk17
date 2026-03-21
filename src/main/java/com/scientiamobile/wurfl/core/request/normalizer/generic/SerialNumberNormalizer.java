package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class SerialNumberNormalizer implements UserAgentNormalizer {
  private static final Pattern a = Pattern.compile("/SN[\\dX]+");
  
  private static final Pattern b = Pattern.compile("\\[(ST|TF|NT)[\\dX]+\\]");
  
  public String normalize(String paramString) {
    paramString = a.matcher(paramString).replaceAll("/SNXXXXXXXXXXXXXXX");
    return b.matcher(paramString).replaceAll("TFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\normalizer\generic\SerialNumberNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */