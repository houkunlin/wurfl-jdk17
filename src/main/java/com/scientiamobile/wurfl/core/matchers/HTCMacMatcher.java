package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class HTCMacMatcher extends MatcherBase {
   private static String GENERIC_HTC_ANDROID_DISGUISED_AS_MAC = "generic_android_htc_disguised_as_mac";

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GENERIC_HTC_ANDROID_DISGUISED_AS_MAC);
      return var1;
   }

   public HTCMacMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return (var2 = var1.getCleanedDeviceUserAgent()).startsWith("Mozilla/5.0 (Macintosh") && var2.contains("HTC");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "---");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return GENERIC_HTC_ANDROID_DISGUISED_AS_MAC;
   }

   public final String getMatcherName() {
      return "HTCMacMatcher";
   }

   public final String getBucketMatcherName() {
      return "HTCMac";
   }
}
