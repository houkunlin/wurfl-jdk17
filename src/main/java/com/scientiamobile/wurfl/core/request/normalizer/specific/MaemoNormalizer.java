package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaemoNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("Maemo [bB]rowser [\\d\\.]+ (.+)");

   public String normalize(String var1) {
      Matcher var2;
      if ((var2 = a.matcher(var1)).find()) {
         int var3;
         String var4;
         if ((var3 = (var4 = var2.group(1)).indexOf(" GTB")) == -1) {
            var3 = var4.length();
         }

         return "Maemo " + var4.substring(0, var3) + "---" + var1;
      } else {
         return var1;
      }
   }
}
