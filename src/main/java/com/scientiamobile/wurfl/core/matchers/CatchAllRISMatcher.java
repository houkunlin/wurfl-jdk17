package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Collection;

final class CatchAllRISMatcher extends AbstractMatcher {
   public final boolean canHandle(WURFLRequest request) {
      return true;
   }

   protected final String risMatch(String normalizedUserAgent) {
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

   public final String getMatcherName() {
      return "CatchAllRISMatcher";
   }

   public final String getBucketMatcherName() {
      return "CatchAllRis";
   }
}
