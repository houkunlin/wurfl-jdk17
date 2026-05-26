package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class BenQMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser()
         && cleanedDeviceUserAgent != null
         && cleanedDeviceUserAgent.regionMatches(true, 0, "benq", 0, 4);
   }

   @Override
   public final String getMatcherName() {
      return "BenQMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "BenQ";
   }
}
