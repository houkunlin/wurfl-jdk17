package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.COMMENTS;

public class LocaleNormalizer implements UserAgentNormalizer {
   private static final Pattern LOCALE_PATTERN = Pattern.compile("; ?[a-z]{2}(?:-r?[a-zA-Z]{2})?(?:\\.utf8|\\.big5)?\\b-?(?!:)", Pattern.COMMENTS);

   @Override
   public String normalize(String userAgent) {
      return LOCALE_PATTERN.matcher(userAgent).replaceAll("; xx-xx");
   }
}
