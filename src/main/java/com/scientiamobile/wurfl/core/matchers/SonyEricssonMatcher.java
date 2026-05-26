package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class SonyEricssonMatcher extends MatcherBase {
   public SonyEricssonMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("Sony");
   }

   protected final String risMatch(String normalizedUserAgent) {
      if (normalizedUserAgent.startsWith("SonyEricsson")) {
         int matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength - 2);
      } else {
         int matchLength = StringMatchUtils.secondSlash(normalizedUserAgent);
         return matchLength != -1
            ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
            : StringMatchUtils.NULL_STRING;
      }
   }

   public final String getMatcherName() {
      return "SonyEricssonMatcher";
   }

   public final String getBucketMatcherName() {
      return "SonyEricsson";
   }
}
