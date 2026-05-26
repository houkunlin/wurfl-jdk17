package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class QtekMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Qtek");
   }

   public final String getMatcherName() {
      return "QtekMatcher";
   }

   public final String getBucketMatcherName() {
      return "Qtek";
   }
}
