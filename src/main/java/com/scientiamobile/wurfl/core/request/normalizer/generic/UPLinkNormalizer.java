package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang.Validate;

public class UPLinkNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      Validate.notNull(var1, "The userAgent is null");
      int var2;
      if ((var2 = var1.indexOf("UP.Link")) >= 0) {
         var1 = var1.substring(0, var2);
      }

      return var1;
   }
}
