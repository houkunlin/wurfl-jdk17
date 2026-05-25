package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class ac extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Mitsu");
   }

   public final String getMatcherName() {
      return "MitsubishiMatcher";
   }

   public final String getBucketMatcherName() {
      return "Mitsubishi";
   }
}
