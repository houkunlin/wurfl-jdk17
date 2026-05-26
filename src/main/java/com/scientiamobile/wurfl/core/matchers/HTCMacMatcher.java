package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class HTCMacMatcher extends MatcherBase {
   private static final String GENERIC_HTC_ANDROID_DISGUISED_AS_MAC = "generic_android_htc_disguised_as_mac";

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(GENERIC_HTC_ANDROID_DISGUISED_AS_MAC);
      return requiredDeviceIds;
   }

   public HTCMacMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
      super(normalizer, wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return cleanedDeviceUserAgent.startsWith("Mozilla/5.0 (Macintosh") && cleanedDeviceUserAgent.contains("HTC");
   }

   protected final String risMatch(String normalizedUserAgent) {
      int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "---");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      return GENERIC_HTC_ANDROID_DISGUISED_AS_MAC;
   }

   public final String getMatcherName() {
      return "HTCMacMatcher";
   }

   public final String getBucketMatcherName() {
      return "HTCMac";
   }
}
