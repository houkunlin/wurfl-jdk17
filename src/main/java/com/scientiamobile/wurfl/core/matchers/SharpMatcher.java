package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class SharpMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(var1.getCleanedDeviceUserAgent(), "sharp");
   }

   public final String getMatcherName() {
      return "SharpMatcher";
   }

   public final String getBucketMatcherName() {
      return "Sharp";
   }
}
