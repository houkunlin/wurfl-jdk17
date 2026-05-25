package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang.StringUtils;

public class AndroidNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      String var2 = UserAgentUtils.getAndroidVersion(var1, false);
      String var3 = UserAgentUtils.getAndroidModel(var1);
      return !StringUtils.isEmpty(var2) && !StringUtils.isEmpty(var3) ? var2 + " " + var3 + "---" + var1 : var1;
   }
}
