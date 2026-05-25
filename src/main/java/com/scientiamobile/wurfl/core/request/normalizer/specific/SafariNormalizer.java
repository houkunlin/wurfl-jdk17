package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class SafariNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      int var2;
      if ((var2 = var1.indexOf("Version/")) != -1) {
         var2 += 8;
         int var3;
         if ((var3 = var1.indexOf(46, var2)) != -1) {
            return "Safari " + var1.substring(var2, var3) + "---" + var1;
         }
      }

      return var1;
   }
}
