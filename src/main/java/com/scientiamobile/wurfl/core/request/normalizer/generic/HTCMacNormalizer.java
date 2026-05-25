package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTCMacNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("; [a-z]{2}(?:-[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b ");
   private static final Pattern b = Pattern.compile("(HTC[^;\\)]+)");

   public String normalize(String var1) {
      var1 = a.matcher(var1).replaceFirst("; xx-xx");
      Matcher var2;
      if ((var2 = b.matcher(var1)).find()) {
         String var4 = var2.group();
         return var4.replaceAll("[ _\\-/]", "~") + "---" + var1;
      } else {
         return var1;
      }
   }
}
