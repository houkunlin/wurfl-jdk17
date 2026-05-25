package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class GenericAndroidNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("Android[ \\-\\/](\\d\\.\\d)[^; \\/\\)]+");

   public String normalize(String var1) {
      return a.matcher(var1).replaceAll("Android $1");
   }
}
