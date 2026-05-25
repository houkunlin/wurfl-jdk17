package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class LGNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      int var2;
      return (var2 = var1.indexOf("LG")) > 0 ? var1.substring(var2) : var1;
   }
}
