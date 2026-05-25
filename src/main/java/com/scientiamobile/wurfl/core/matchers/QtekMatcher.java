package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class QtekMatcher extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Qtek");
   }

   public final String getMatcherName() {
      return "QtekMatcher";
   }

   public final String getBucketMatcherName() {
      return "Qtek";
   }
}

