package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

final class VodafoneMatcher extends MatcherBase {
   public VodafoneMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("Vodafone");
   }

   @Override
   public final String getMatcherName() {
      return "VodafoneMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Vodafone";
   }
}
