package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class MaemoMatcher extends MatcherBase {
   private static final String GENERIC_OPERA_MOBI_MAEMO = "generic_opera_mobi_maemo";
   private static final String NOKIA_GENERIC_MAEMO_WITH_FIREFOX = "nokia_generic_maemo_with_firefox";
   private static final String NOKIA_GENERIC_MAEMO = "nokia_generic_maemo";

   public MaemoMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
      super(normalizer, wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(GENERIC_OPERA_MOBI_MAEMO);
      requiredDeviceIds.add(NOKIA_GENERIC_MAEMO_WITH_FIREFOX);
      requiredDeviceIds.add(NOKIA_GENERIC_MAEMO);
      return requiredDeviceIds;
   }

   public final boolean canHandle(WURFLRequest request) {
      return request.getCleanedDeviceUserAgent().contains("Maemo");
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      String normalizedDeviceUserAgent;
      if ((normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent()).contains("Opera Mobi")) {
         return GENERIC_OPERA_MOBI_MAEMO;
      } else {
         return normalizedDeviceUserAgent.contains("Firefox") ? NOKIA_GENERIC_MAEMO_WITH_FIREFOX : NOKIA_GENERIC_MAEMO;
      }
   }

   protected final String risMatch(String normalizedUserAgent) {
      int matchLength;
      return (matchLength = normalizedUserAgent.indexOf("---")) >= 0
         ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength + 3)
         : super.risMatch(normalizedUserAgent);
   }

   public final String getMatcherName() {
      return "MaemoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Maemo";
   }
}
