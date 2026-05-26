package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class MotorolaMatcher extends MatcherBase {
   private static final String MOT_MIB22_GENERIC = "mot_mib22_generic";

   public MotorolaMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("generic");
      var1.add(MOT_MIB22_GENERIC);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(var2, "Mot-", "MOT-", "MOTO", "moto") || var2.contains("Motorola");
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return StringMatchUtils.containsAnyOf(var1.getNormalizedDeviceUserAgent(), "MIB/2.2", "MIB/BER2.2") ? MOT_MIB22_GENERIC : "generic";
   }

   public final String getMatcherName() {
      return "MotorolaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Motorola";
   }
}
