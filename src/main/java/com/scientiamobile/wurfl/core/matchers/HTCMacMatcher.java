package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class HTCMacMatcher extends MatcherBase {
   private static final String GENERIC_HTC_ANDROID_DISGUISED_AS_MAC = "generic_android_htc_disguised_as_mac";

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(GENERIC_HTC_ANDROID_DISGUISED_AS_MAC);
      return requiredDeviceIds;
   }

   public HTCMacMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
      super(normalizer, wurflModel);
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return cleanedDeviceUserAgent.startsWith("Mozilla/5.0 (Macintosh") && cleanedDeviceUserAgent.contains("HTC");
   }

   @Override
   protected final String risMatch(String normalizedUserAgent) {
      int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "---");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      return GENERIC_HTC_ANDROID_DISGUISED_AS_MAC;
   }

   @Override
   public final String getMatcherName() {
      return "HTCMacMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "HTCMac";
   }
}
