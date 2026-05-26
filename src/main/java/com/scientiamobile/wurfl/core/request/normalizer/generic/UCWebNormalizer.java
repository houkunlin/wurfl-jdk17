package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.regex.Pattern;

public class UCWebNormalizer implements UserAgentNormalizer {
   private static final Pattern JUC_ANDROID_VERSION_PATTERN = Pattern.compile("^(JUC \\(Linux; U;)(?= \\d)");
   private static final Pattern MISSING_SPACE_PATTERN = Pattern.compile("(Android|JUC|[;\\)])(?=[\\w|\\(])");

   @Override
   public String normalize(String userAgent) {
      if (StringMatchUtils.startsWithAnyOf(userAgent, "JUC", "Mozilla/5.0(Linux;U;Android")) {
         userAgent = JUC_ANDROID_VERSION_PATTERN.matcher(userAgent).replaceFirst("$1 Android");
         userAgent = MISSING_SPACE_PATTERN.matcher(userAgent).replaceAll("$1 ");
      }

      return userAgent;
   }
}
