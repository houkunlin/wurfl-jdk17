package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class KDDIMatcher extends MatcherBase {
   private static String OPWV_V62_GENERIC = "opwv_v62_generic";

   public KDDIMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(OPWV_V62_GENERIC);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("KDDI-");
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = var1.startsWith("KDDI/") ? StringMatchUtils.secondSlash(var1) : StringMatchUtils.firstSlash(var1)) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return OPWV_V62_GENERIC;
   }

   public final String getMatcherName() {
      return "KDDIMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kddi";
   }
}
