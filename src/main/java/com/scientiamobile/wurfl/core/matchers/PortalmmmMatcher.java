package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PortalmmmMatcher extends MatcherBase {
   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("portalmmm");
   }

   @Override
   public String getMatcherName() {
      return "PortalmmmMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Portalmmm";
   }

   @Override
   protected String risMatch(String normalizedUserAgent) {
      return null;
   }
}
