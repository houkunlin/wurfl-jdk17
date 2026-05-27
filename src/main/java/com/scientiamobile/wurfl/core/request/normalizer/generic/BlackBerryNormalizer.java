package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class BlackBerryNormalizer implements UserAgentNormalizer {
   private static final Pattern BLACKBERRY_PATTERN = Pattern.compile("(?i)black(?i)berry");

   @Override
   public String normalize(String userAgent) {
      int blackBerryIndex;
      blackBerryIndex = (userAgent = BLACKBERRY_PATTERN.matcher(userAgent).replaceAll("BlackBerry")).indexOf("BlackBerry");
      if (blackBerryIndex > 0) {
         userAgent = userAgent.substring(blackBerryIndex);
      }

      return userAgent;
   }
}
