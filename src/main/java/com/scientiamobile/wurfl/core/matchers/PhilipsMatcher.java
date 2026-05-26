package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PhilipsMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "philips", 0, 7);
   }

   public final String getMatcherName() {
      return "PhilipsMatcher";
   }

   public final String getBucketMatcherName() {
      return "Philips";
   }
}
