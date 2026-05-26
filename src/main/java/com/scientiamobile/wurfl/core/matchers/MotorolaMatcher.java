package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class MotorolaMatcher extends MatcherBase {
   private static final String MOT_MIB22_GENERIC = "mot_mib22_generic";

   public MotorolaMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add("generic");
      requiredDeviceIds.add(MOT_MIB22_GENERIC);
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "Mot-", "MOT-", "MOTO", "moto")
         || cleanedDeviceUserAgent.contains("Motorola");
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      return StringMatchUtils.containsAnyOf(request.getNormalizedDeviceUserAgent(), "MIB/2.2", "MIB/BER2.2") ? MOT_MIB22_GENERIC : "generic";
   }

   @Override
   public final String getMatcherName() {
      return "MotorolaMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Motorola";
   }
}
