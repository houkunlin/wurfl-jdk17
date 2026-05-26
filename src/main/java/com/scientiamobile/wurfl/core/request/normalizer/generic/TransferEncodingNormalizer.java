package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

public class TransferEncodingNormalizer implements UserAgentNormalizer {
   private static final CharSequence TRANSFER_ENCODING_TOKEN = new String(",gzip(gfe)");

   public String normalize(String userAgent) {
      return userAgent.replace(TRANSFER_ENCODING_TOKEN, "");
   }
}
