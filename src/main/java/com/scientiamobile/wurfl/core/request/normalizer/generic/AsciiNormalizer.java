package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class AsciiNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("[^ -~]+");

   public String normalize(String var1) {
      return a.matcher(var1).replaceAll("");
   }
}
