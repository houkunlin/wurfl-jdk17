package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class PhilipsMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2 != null && var2.regionMatches(true, 0, "philips", 0, 7);
   }

   public final String getMatcherName() {
      return "PhilipsMatcher";
   }

   public final String getBucketMatcherName() {
      return "Philips";
   }
}
