package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class KonquerorMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringUtils.contains(var1.getCleanedDeviceUserAgent(), "Konqueror");
   }

   public final String getMatcherName() {
      return "KonquerorMatcher";
   }

   public final String getBucketMatcherName() {
      return "Konqueror";
   }
}
