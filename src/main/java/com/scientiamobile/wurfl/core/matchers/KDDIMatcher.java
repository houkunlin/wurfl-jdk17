package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class KDDIMatcher extends MatcherBase {
   private static final String OPWV_V62_GENERIC = "opwv_v62_generic";

   public KDDIMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(OPWV_V62_GENERIC);
      return requiredDeviceIds;
   }

   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("KDDI-");
   }

   protected final String risMatch(String normalizedUserAgent) {
      int matchLength = normalizedUserAgent.startsWith("KDDI/") ? StringMatchUtils.secondSlash(normalizedUserAgent) : StringMatchUtils.firstSlash(normalizedUserAgent);
      return matchLength == -1
         ? StringMatchUtils.NULL_STRING
         : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      return OPWV_V62_GENERIC;
   }

   public final String getMatcherName() {
      return "KDDIMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kddi";
   }
}
