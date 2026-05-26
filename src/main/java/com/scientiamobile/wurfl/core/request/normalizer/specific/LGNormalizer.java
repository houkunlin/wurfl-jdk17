package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class LGNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      int lgIndex;
      return (lgIndex = userAgent.indexOf("LG")) > 0 ? userAgent.substring(lgIndex) : userAgent;
   }
}
