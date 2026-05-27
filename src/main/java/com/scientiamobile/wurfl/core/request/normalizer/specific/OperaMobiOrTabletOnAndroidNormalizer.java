package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

public class OperaMobiOrTabletOnAndroidNormalizer implements UserAgentNormalizer {
   @Override
   public String normalize(String userAgent) {
      StringBuilder normalizedUaBuilder;
normalizedUaBuilder = new StringBuilder();
normalizedUaBuilder.append(userAgent.contains("Opera Mobi") ? "Opera Mobi" : "Opera Tablet").append(" ");
      String operaOrAndroidVersion;
      operaOrAndroidVersion = UserAgentUtils.getOperaOnAndroidVersion(userAgent, false);
      if (operaOrAndroidVersion != null) {
         normalizedUaBuilder.append(operaOrAndroidVersion).append(" ");
      }

      normalizedUaBuilder.append("Android");
      operaOrAndroidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
      if (operaOrAndroidVersion != null) {
         normalizedUaBuilder.append(" ").append(operaOrAndroidVersion);
      }

      normalizedUaBuilder.append("---").append(userAgent);
      return normalizedUaBuilder.toString();
   }
}
