package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class PantechMatcher extends MatcherBase {
   public PantechMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser()
         && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Pantech", "PT-", "PANTECH", "PG-");
   }

   public final String getMatcherName() {
      return "PantechMatcher";
   }

   public final String getBucketMatcherName() {
      return "Pantech";
   }
}
