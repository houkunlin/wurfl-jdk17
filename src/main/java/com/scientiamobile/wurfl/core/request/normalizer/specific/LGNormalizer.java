package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class LGNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      int lgIndex;
lgIndex = userAgent.indexOf("LG");
return lgIndex > 0 ? userAgent.substring(lgIndex) : userAgent;
   }
}
