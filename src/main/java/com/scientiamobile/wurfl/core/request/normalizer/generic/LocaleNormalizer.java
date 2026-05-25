package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class LocaleNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("; ?[a-z]{2}(?:-r?[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b-?(?!:)");

   public String normalize(String var1) {
      return a.matcher(var1).replaceAll("; xx-xx");
   }
}
