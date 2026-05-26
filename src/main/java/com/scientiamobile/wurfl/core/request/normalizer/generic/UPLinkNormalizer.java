package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import org.apache.commons.lang3.Validate;

public class UPLinkNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      Validate.notNull(userAgent, "The userAgent is null");
      int upLinkIndex;
      if ((upLinkIndex = userAgent.indexOf("UP.Link")) >= 0) {
         userAgent = userAgent.substring(0, upLinkIndex);
      }

      return userAgent;
   }
}
