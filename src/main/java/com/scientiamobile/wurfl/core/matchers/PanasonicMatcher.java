package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PanasonicMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Panasonic");
   }

   public final String getMatcherName() {
      return "PanasonicMatcher";
   }

   public final String getBucketMatcherName() {
      return "Panasonic";
   }
}
