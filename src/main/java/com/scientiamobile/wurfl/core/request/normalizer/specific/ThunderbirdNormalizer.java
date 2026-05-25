package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang.StringUtils;

public class ThunderbirdNormalizer implements UserAgentNormalizer {
   public String normalize(String var1) {
      int var2;
      return (var2 = StringMatchUtils.indexOf(var1, "Thunderbird")) >= 0 ? StringUtils.substring(var1, var2) : var1;
   }
}
