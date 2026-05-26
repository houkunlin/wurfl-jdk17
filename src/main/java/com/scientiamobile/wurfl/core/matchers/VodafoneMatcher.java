package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

final class VodafoneMatcher extends MatcherBase {
   public VodafoneMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Vodafone");
   }

   public final String getMatcherName() {
      return "VodafoneMatcher";
   }

   public final String getBucketMatcherName() {
      return "Vodafone";
   }
}
