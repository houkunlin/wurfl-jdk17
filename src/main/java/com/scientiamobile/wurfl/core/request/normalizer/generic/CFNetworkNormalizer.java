package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CFNetworkNormalizer implements UserAgentNormalizer {
   private static final Pattern CFNETWORK_VERSION_PATTERN = Pattern.compile("CFNetwork/(\\d+\\.?[0-9]*)");

   @Override
   public String normalize(String userAgent) {
      Matcher cfNetworkMatcher;
      if ((cfNetworkMatcher = CFNETWORK_VERSION_PATTERN.matcher(userAgent)).find()) {
         String cfNetworkVersionNormalized = (new BigDecimal(cfNetworkMatcher.group(1))).setScale(2, RoundingMode.HALF_DOWN).toString();
         StringBuilder normalizedUaBuilder = new StringBuilder();
         if (userAgent.contains("x86_64")) {
            normalizedUaBuilder.append("CFNetworkDesktop/").append(cfNetworkVersionNormalized).append(" ").append(userAgent);
         } else {
            normalizedUaBuilder.append("CFNetwork/").append(cfNetworkVersionNormalized).append(" ").append(userAgent);
         }

         return normalizedUaBuilder.toString();
      } else {
         return userAgent;
      }
   }
}
