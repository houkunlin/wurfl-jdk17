package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SharpMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "sharp", 0, 5);
   }

   public final String getMatcherName() {
      return "SharpMatcher";
   }

   public final String getBucketMatcherName() {
      return "Sharp";
   }
}
