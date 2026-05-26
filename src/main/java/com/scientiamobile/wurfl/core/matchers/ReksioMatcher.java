package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class ReksioMatcher extends MatcherBase {
   private static String REKSIO_DEVICE_ID = "generic_reksio";

   public ReksioMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(REKSIO_DEVICE_ID);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Reksio");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return REKSIO_DEVICE_ID;
   }

   public final String getMatcherName() {
      return "ReksioMatcher";
   }

   public final String getBucketMatcherName() {
      return "Reksio";
   }
}
