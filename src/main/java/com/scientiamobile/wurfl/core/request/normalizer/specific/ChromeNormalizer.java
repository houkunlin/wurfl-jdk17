package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class ChromeNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      int var2;
      if ((var2 = var1.indexOf("Chrome")) >= 0) {
         var1 = var1.substring(var2);
      }

      return var1;
   }
}
