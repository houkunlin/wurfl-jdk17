package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Pattern;

public class SerialNumberNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("/SN[\\dX]+");
   private static final Pattern b = Pattern.compile("\\[(ST|TF|NT)[\\dX]+\\]");

   public String normalize(String var1) {
      var1 = a.matcher(var1).replaceAll("/SNXXXXXXXXXXXXXXX");
      return b.matcher(var1).replaceAll("TFXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
   }
}
