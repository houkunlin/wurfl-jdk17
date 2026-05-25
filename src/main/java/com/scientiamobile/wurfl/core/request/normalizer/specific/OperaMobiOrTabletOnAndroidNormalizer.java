package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;

public class OperaMobiOrTabletOnAndroidNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      StringBuilder var2;
      (var2 = new StringBuilder()).append(var1.contains("Opera Mobi") ? "Opera Mobi" : "Opera Tablet").append(" ");
      String var3;
      if ((var3 = UserAgentUtils.getOperaOnAndroidVersion(var1, false)) != null) {
         var2.append(var3).append(" ");
      }

      var2.append("Android");
      if ((var3 = UserAgentUtils.getAndroidVersion(var1, false)) != null) {
         var2.append(" ").append(var3);
      }

      var2.append("---").append(var1);
      return var2.toString();
   }
}
