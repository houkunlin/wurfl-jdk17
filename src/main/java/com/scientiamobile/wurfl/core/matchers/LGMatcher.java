package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class LGMatcher extends MatcherBase {
   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add("generic");
      return requiredDeviceIds;
   }

   public LGMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
      super(normalizer, wurflModel);
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent != null && cleanedDeviceUserAgent.regionMatches(true, 0, "lg", 0, 2);
   }

   @Override
   protected final String risMatch(String normalizedUserAgent) {
      int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", normalizedUserAgent.indexOf("LG"));
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      FilteredDeviceIndex deviceIndex = this.getFilter().getIndex();
      String matchedUserAgent;
      String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
      return (matchedUserAgent = StringMatchUtils.risMatch(deviceIndex.getUserAgents(), normalizedDeviceUserAgent, 7)) != null
         ? deviceIndex.getDeviceIdByUserAgent(matchedUserAgent)
         : "generic";
   }

   @Override
   public final String getMatcherName() {
      return "LGMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "LG";
   }
}
