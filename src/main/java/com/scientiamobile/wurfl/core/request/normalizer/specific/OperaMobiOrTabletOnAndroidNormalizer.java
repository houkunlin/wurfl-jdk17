package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

public class OperaMobiOrTabletOnAndroidNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      StringBuilder normalizedUaBuilder;
      (normalizedUaBuilder = new StringBuilder()).append(userAgent.contains("Opera Mobi") ? "Opera Mobi" : "Opera Tablet").append(" ");
      String operaOrAndroidVersion;
      if ((operaOrAndroidVersion = UserAgentUtils.getOperaOnAndroidVersion(userAgent, false)) != null) {
         normalizedUaBuilder.append(operaOrAndroidVersion).append(" ");
      }

      normalizedUaBuilder.append("Android");
      if ((operaOrAndroidVersion = UserAgentUtils.getAndroidVersion(userAgent, false)) != null) {
         normalizedUaBuilder.append(" ").append(operaOrAndroidVersion);
      }

      normalizedUaBuilder.append("---").append(userAgent);
      return normalizedUaBuilder.toString();
   }
}
