package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class ChromeNormalizer implements UserAgentNormalizer {
   public String normalize(String userAgent) {
      int chromeIndex;
      if ((chromeIndex = userAgent.indexOf("Chrome")) >= 0) {
         userAgent = userAgent.substring(chromeIndex);
      }

      return userAgent;
   }
}
