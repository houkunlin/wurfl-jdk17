package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class MozillaNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("(; [a-z]{2}(-[a-zA-Z]{0,2})?)");

   public String normalize(String var1) {
      return var1.startsWith("Mozilla/") ? a.matcher(var1).replaceFirst("") : var1;
   }
}
