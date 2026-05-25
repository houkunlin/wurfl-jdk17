package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class BlackBerryNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("(?i)black(?i)berry");

   public String normalize(String var1) {
      int var2;
      if ((var2 = (var1 = a.matcher(var1).replaceAll("BlackBerry")).indexOf("BlackBerry")) > 0) {
         var1 = var1.substring(var2);
      }

      return var1;
   }
}
