package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;

final class SiemensMatcher extends MatcherBase {
   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("SIE-");
   }

   @Override
   public String getMatcherName() {
      return "SiemensMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Siemens";
   }
}
