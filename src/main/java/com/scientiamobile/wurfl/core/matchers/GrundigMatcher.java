package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class GrundigMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser()
         && cleanedDeviceUserAgent != null
         && cleanedDeviceUserAgent.regionMatches(true, 0, "grundig", 0, 7);
   }

   public final String getMatcherName() {
      return "GrundigMatcher";
   }

   public final String getBucketMatcherName() {
      return "Grundig";
   }
}
