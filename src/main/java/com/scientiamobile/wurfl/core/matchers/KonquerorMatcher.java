package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class KonquerorMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && var1.getCleanedDeviceUserAgent().contains("Konqueror");
   }

   public final String getMatcherName() {
      return "KonquerorMatcher";
   }

   public final String getBucketMatcherName() {
      return "Konqueror";
   }
}
