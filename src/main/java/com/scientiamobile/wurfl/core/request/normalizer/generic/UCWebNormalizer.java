package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.regex.Pattern;

public class UCWebNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("^(JUC \\(Linux; U;)(?= \\d)");
   private static final Pattern b = Pattern.compile("(Android|JUC|[;\\)])(?=[\\w|\\(])");

   public String normalize(String var1) {
      if (StringMatchUtils.startsWithAnyOf(var1, "JUC", "Mozilla/5.0(Linux;U;Android")) {
         var1 = a.matcher(var1).replaceFirst("$1 Android");
         var1 = b.matcher(var1).replaceAll("$1 ");
      }

      return var1;
   }
}
