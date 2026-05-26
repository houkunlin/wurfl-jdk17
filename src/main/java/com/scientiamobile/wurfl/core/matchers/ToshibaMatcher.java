package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class ToshibaMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Toshiba");
   }

   public final String getMatcherName() {
      return "ToshibaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Toshiba";
   }
}
