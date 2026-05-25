package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class GrundigMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(var1.getCleanedDeviceUserAgent(), "grundig");
   }

   public final String getMatcherName() {
      return "GrundigMatcher";
   }

   public final String getBucketMatcherName() {
      return "Grundig";
   }
}
