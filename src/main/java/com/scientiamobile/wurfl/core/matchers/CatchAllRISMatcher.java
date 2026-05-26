package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Collection;

final class CatchAllRISMatcher extends AbstractMatcher {
   @Override
   public boolean canHandle(WURFLRequest request) {
      return true;
   }

   @Override
   protected String risMatch(String normalizedUserAgent) {
      Collection<?> userAgents = this.getFilter().getIndex().getUserAgents();
      if (normalizedUserAgent != null && normalizedUserAgent.startsWith("CFNetwork")) {
         int matchLength = StringMatchUtils.firstSpace(normalizedUserAgent);
         if (matchLength != -1) {
            return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
         }
      } else {
         int matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
         if (matchLength != -1) {
            return StringMatchUtils.risMatch(userAgents, normalizedUserAgent, matchLength);
         }
      }

      return StringMatchUtils.NULL_STRING;
   }

   @Override
   public String getMatcherName() {
      return "CatchAllRISMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "CatchAllRis";
   }
}
