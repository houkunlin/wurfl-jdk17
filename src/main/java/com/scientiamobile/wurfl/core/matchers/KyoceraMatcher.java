package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class KyoceraMatcher extends MatcherBase {
   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "kyocera", "KWC-", "QC-");
   }

   @Override
   public String getMatcherName() {
      return "KyoceraMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Kyocera";
   }
}
