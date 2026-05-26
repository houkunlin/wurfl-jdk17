package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class NecMatcher extends MatcherBase {
   public NecMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "NEC-", "KGT");
   }

   public final String getMatcherName() {
      return "NecMatcher";
   }

   public final String getBucketMatcherName() {
      return "Nec";
   }
}
