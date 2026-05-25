package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class ae extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(var1.getCleanedDeviceUserAgent(), "kyocera", "KWC-", "QC-");
   }

   public final String getMatcherName() {
      return "KyoceraMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kyocera";
   }
}
