package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class BenQMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2 != null && var2.regionMatches(true, 0, "benq", 0, 4);
   }

   public final String getMatcherName() {
      return "BenQMatcher";
   }

   public final String getBucketMatcherName() {
      return "BenQ";
   }
}
