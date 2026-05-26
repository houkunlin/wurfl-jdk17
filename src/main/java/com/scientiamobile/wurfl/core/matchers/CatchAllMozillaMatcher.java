package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class CatchAllMozillaMatcher extends AbstractMatcher {
   public CatchAllMozillaMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add("generic");
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return StringMatchUtils.startsWithAnyOf(request.getCleanedDeviceUserAgent(), "Mozilla/3", "Mozilla/4", "Mozilla/5");
   }

   @Override
   protected final String applyConclusiveMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      int matchLength = StringMatchUtils.firstCloseParenthesis(normalizedUserAgent);
      String matchedUserAgent = matchLength != -1
         ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
         : StringMatchUtils.NULL_STRING;
      String deviceId = "generic";
      if (matchedUserAgent != null) {
         deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent);
      }

      if (deviceId == null) {
         deviceId = "generic";
      }

      return deviceId;
   }

   @Override
   public final String getMatcherName() {
      return "CatchAllMozillaMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "CatchAllMozilla";
   }
}
