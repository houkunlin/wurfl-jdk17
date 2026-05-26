package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SiemensMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("SIE-");
   }

   @Override
   public final String getMatcherName() {
      return "SiemensMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Siemens";
   }
}
