package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class SkyfireMatcher extends MatcherBase {
   private static final String GENERIC_SKYFIRE_VERSION2 = "generic_skyfire_version2";
   private static final String GENERIC_SKYFIRE_VERSION1 = "generic_skyfire_version1";

   public SkyfireMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION1);
      requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION2);
      return requiredDeviceIds;
   }

   public final boolean canHandle(WURFLRequest request) {
      return request.getCleanedDeviceUserAgent().contains("Skyfire");
   }

   protected final String risMatch(String userAgent) {
      int skyfireIndex = StringMatchUtils.indexOf(userAgent, "Skyfire");
      int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ".", skyfireIndex);
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? GENERIC_SKYFIRE_VERSION2 : GENERIC_SKYFIRE_VERSION1;
   }

   public final String getMatcherName() {
      return "SkyfireMatcher";
   }

   public final String getBucketMatcherName() {
      return "Skyfire";
   }
}
