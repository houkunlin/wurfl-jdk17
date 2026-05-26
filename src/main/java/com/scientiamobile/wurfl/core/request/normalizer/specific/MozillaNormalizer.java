package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class MozillaNormalizer implements UserAgentNormalizer {
   private static final Pattern LOCALE_PATTERN = Pattern.compile("(; [a-z]{2}(-[a-zA-Z]{0,2})?)");

   public String normalize(String userAgent) {
      return userAgent.startsWith("Mozilla/") ? LOCALE_PATTERN.matcher(userAgent).replaceFirst("") : userAgent;
   }
}
