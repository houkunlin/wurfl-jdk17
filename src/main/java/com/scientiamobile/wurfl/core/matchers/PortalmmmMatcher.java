package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PortalmmmMatcher extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("portalmmm");
   }

   public final String getMatcherName() {
      return "PortalmmmMatcher";
   }

   public final String getBucketMatcherName() {
      return "Portalmmm";
   }

   protected final String risMatch(String var1) {
      return null;
   }
}

