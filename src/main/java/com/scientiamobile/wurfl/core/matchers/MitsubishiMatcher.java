package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class MitsubishiMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Mitsu");
   }

   public final String getMatcherName() {
      return "MitsubishiMatcher";
   }

   public final String getBucketMatcherName() {
      return "Mitsubishi";
   }
}
