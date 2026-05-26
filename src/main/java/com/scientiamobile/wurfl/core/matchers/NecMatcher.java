package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class NecMatcher extends MatcherBase {
   public NecMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "NEC-", "KGT");
   }

   @Override
   public final String getMatcherName() {
      return "NecMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Nec";
   }
}
