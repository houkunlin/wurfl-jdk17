package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaemoNormalizer implements UserAgentNormalizer {
   private static final Pattern MAEMO_BROWSER_MODEL_PATTERN = Pattern.compile("Maemo [bB]rowser [\\d\\.]+ (.+)");

   public String normalize(String userAgent) {
      Matcher maemoMatcher;
      if ((maemoMatcher = MAEMO_BROWSER_MODEL_PATTERN.matcher(userAgent)).find()) {
         int modelEndIndex;
         String deviceModel;
         if ((modelEndIndex = (deviceModel = maemoMatcher.group(1)).indexOf(" GTB")) == -1) {
            modelEndIndex = deviceModel.length();
         }

         return "Maemo " + deviceModel.substring(0, modelEndIndex) + "---" + userAgent;
      } else {
         return userAgent;
      }
   }
}
