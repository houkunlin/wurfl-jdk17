package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;

public class AndroidNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      String androidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
      String androidModel = UserAgentUtils.getAndroidModel(userAgent);
      return !StringUtils.isEmpty(androidVersion) && !StringUtils.isEmpty(androidModel)
         ? androidVersion + " " + androidModel + "---" + userAgent
         : userAgent;
   }
}
