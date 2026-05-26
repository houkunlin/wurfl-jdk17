package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

public class ThunderbirdNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      int thunderbirdIndex = StringMatchUtils.indexOf(userAgent, "Thunderbird");
      return thunderbirdIndex >= 0 ? StringUtils.substring(userAgent, thunderbirdIndex) : userAgent;
   }
}
