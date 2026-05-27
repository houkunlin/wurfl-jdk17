package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class ChromeNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      int chromeIndex;
      chromeIndex = userAgent.indexOf("Chrome");
      if (chromeIndex >= 0) {
         userAgent = userAgent.substring(chromeIndex);
      }

      return userAgent;
   }
}
