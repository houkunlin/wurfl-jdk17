package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import org.apache.commons.lang3.StringUtils;

final class BenQMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(var2, "benq");
   }

   public final String getMatcherName() {
      return "BenQMatcher";
   }

   public final String getBucketMatcherName() {
      return "BenQ";
   }
}
