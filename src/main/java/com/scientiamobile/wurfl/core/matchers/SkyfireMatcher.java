package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class SkyfireMatcher extends MatcherBase {
   private static String GENERIC_SKYFIRE_VERSION2 = "generic_skyfire_version2";
   private static String GENERIC_SKYFIRE_VERSION1 = "generic_skyfire_version1";

   public SkyfireMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GENERIC_SKYFIRE_VERSION1);
      var1.add(GENERIC_SKYFIRE_VERSION2);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Skyfire");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOf(var1, "Skyfire");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, StringMatchUtils.indexOfOrLength(var1, ".", var2));
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? GENERIC_SKYFIRE_VERSION2 : GENERIC_SKYFIRE_VERSION1;
   }

   public final String getMatcherName() {
      return "SkyfireMatcher";
   }

   public final String getBucketMatcherName() {
      return "Skyfire";
   }
}
