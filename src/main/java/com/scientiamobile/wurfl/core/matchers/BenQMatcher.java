package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class BenQMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser()
         && cleanedDeviceUserAgent != null
         && cleanedDeviceUserAgent.regionMatches(true, 0, "benq", 0, 4);
   }

   public final String getMatcherName() {
      return "BenQMatcher";
   }

   public final String getBucketMatcherName() {
      return "BenQ";
   }
}
