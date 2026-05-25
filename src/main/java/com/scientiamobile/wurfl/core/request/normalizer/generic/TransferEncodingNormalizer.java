package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class TransferEncodingNormalizer implements UserAgentNormalizer {
   private static final CharSequence a = new String(",gzip(gfe)");

   public String normalize(String var1) {
      return var1.replace(a, "");
   }
}
