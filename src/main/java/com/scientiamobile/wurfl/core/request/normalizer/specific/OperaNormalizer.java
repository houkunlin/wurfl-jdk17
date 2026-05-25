package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public class OperaNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("Version/(\\d+\\.\\d+)");
   private static final Pattern b = Pattern.compile("OPR/(\\d+\\.\\d+)");

   public String normalize(String var1) {
      Matcher var2;
      if (var1.startsWith("Opera/9.80") && (var2 = a.matcher(var1)).find()) {
         return StringUtils.replace(var1, "Opera/9.80", "Opera/" + var2.group(1));
      } else {
         return (var2 = b.matcher(var1)).find() ? "Opera/" + var2.group(1) + " " + var1 : var1;
      }
   }
}
