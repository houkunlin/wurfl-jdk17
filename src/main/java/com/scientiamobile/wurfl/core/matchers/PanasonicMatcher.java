package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PanasonicMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Panasonic");
   }

   @Override
   public final String getMatcherName() {
      return "PanasonicMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Panasonic";
   }
}
