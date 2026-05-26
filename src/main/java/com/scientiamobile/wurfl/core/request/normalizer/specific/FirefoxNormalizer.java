package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.StringUtils;

public class FirefoxNormalizer implements UserAgentNormalizer {
   public String normalize(String userAgent) {
      String normalizedUserAgent = userAgent;
      int firefoxIndex;
      if ((firefoxIndex = userAgent.indexOf("Firefox")) >= 0) {
         normalizedUserAgent = StringUtils.substring(userAgent, firefoxIndex);
      }

      return normalizedUserAgent;
   }
}
