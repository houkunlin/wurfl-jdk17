package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class SafariNormalizer implements UserAgentNormalizer {
   public String normalize(String userAgent) {
      int versionTokenStart;
      if ((versionTokenStart = userAgent.indexOf("Version/")) != -1) {
         versionTokenStart += 8;
         int majorVersionDotIndex;
         if ((majorVersionDotIndex = userAgent.indexOf(46, versionTokenStart)) != -1) {
            return "Safari " + userAgent.substring(versionTokenStart, majorVersionDotIndex) + "---" + userAgent;
         }
      }

      return userAgent;
   }
}
