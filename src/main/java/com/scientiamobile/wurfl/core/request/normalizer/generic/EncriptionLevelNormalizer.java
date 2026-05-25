package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class EncriptionLevelNormalizer implements UserAgentNormalizer {
   private static final CharSequence a = new String(" U;");

   public String normalize(String var1) {
      return var1.replace(a, "");
   }
}
