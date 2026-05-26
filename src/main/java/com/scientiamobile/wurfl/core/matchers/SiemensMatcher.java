package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SiemensMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("SIE-");
   }

   public final String getMatcherName() {
      return "SiemensMatcher";
   }

   public final String getBucketMatcherName() {
      return "Siemens";
   }
}
