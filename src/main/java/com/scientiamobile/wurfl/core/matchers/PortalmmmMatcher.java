package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PortalmmmMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("portalmmm");
   }

   public final String getMatcherName() {
      return "PortalmmmMatcher";
   }

   public final String getBucketMatcherName() {
      return "Portalmmm";
   }

   protected final String risMatch(String normalizedUserAgent) {
      return null;
   }
}
