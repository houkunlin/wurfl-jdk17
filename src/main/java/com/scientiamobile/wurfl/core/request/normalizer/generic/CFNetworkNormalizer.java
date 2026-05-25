package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFNetworkNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("CFNetwork/(\\d+\\.?[0-9]*)");

   public String normalize(String var1) {
      Matcher var2;
      if ((var2 = a.matcher(var1)).find()) {
         String var4 = (new BigDecimal(var2.group(1))).setScale(2, RoundingMode.HALF_DOWN).toString();
         StringBuilder var3 = new StringBuilder();
         if (var1.contains("x86_64")) {
            var3.append("CFNetworkDesktop/").append(var4).append(" ").append(var1);
         } else {
            var3.append("CFNetwork/").append(var4).append(" ").append(var1);
         }

         return var3.toString();
      } else {
         return var1;
      }
   }
}
