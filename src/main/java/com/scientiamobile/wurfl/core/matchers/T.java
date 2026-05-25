package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class T extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("SIE-");
   }

   public final String getMatcherName() {
      return "SiemensMatcher";
   }

   public final String getBucketMatcherName() {
      return "Siemens";
   }
}
