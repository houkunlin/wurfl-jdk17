package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class SPVMatcher extends MatcherBase {
   public SPVMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("SPV");
   }

   protected final String risMatch(String userAgent) {
      int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ";", StringMatchUtils.indexOfOrLength(userAgent, "SPV"));
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
   }

   public final String getMatcherName() {
      return "SPVMatcher";
   }

   public final String getBucketMatcherName() {
      return "SPV";
   }
}
