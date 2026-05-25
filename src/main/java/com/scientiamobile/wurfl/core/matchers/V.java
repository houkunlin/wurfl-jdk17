package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class V extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Toshiba");
   }

   public final String getMatcherName() {
      return "ToshibaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Toshiba";
   }
}
