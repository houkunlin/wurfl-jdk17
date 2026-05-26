package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SagemMatcher extends MatcherBase {
   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "sagem", 0, 5);
   }

   @Override
   public String getMatcherName() {
      return "SagemMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Sagem";
   }
}
