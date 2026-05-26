package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SharpMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "sharp", 0, 5);
   }

   @Override
   public final String getMatcherName() {
      return "SharpMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Sharp";
   }
}
