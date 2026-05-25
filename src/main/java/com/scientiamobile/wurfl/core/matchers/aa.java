package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class aa extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Panasonic");
   }

   public final String getMatcherName() {
      return "PanasonicMatcher";
   }

   public final String getBucketMatcherName() {
      return "Panasonic";
   }
}
