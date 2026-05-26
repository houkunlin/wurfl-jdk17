package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class SonyEricssonMatcher extends MatcherBase {
   public SonyEricssonMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("Sony");
   }

   @Override
   protected String risMatch(String normalizedUserAgent) {
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

   @Override
   public String getMatcherName() {
      return "SonyEricssonMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "SonyEricsson";
   }
}
