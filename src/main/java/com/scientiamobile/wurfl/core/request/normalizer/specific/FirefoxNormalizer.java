package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.StringUtils;

public class FirefoxNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      String var2 = var1;
      int var3;
      if ((var3 = var1.indexOf("Firefox")) >= 0) {
         var2 = StringUtils.substring(var1, var3);
      }

      return var2;
   }
}
