package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class NecMatcher extends MatcherBase {
   public NecMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "NEC-", "KGT");
   }

   @Override
   public String getMatcherName() {
      return "NecMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Nec";
   }
}
