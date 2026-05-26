package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class MitsubishiMatcher extends MatcherBase {
   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Mitsu");
   }

   @Override
   public String getMatcherName() {
      return "MitsubishiMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Mitsubishi";
   }
}
