package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperaMiniNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("^Opera/[\\d\\.]+ .+?\\d{3}X\\d{3} (.+)$");

   public String normalize(String var1) {
      Matcher var2;
      return (var2 = a.matcher(var1)).matches() ? var2.group(1) + "---" + var1 : var1;
   }
}
