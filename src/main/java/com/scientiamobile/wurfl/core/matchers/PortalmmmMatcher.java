package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PortalmmmMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("portalmmm");
   }

   @Override
   public final String getMatcherName() {
      return "PortalmmmMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Portalmmm";
   }

   @Override
   protected final String risMatch(String normalizedUserAgent) {
      return null;
   }
}
