package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class LGMatcher extends MatcherBase {
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("generic");
      return var1;
   }

   public LGMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2 != null && var2.regionMatches(true, 0, "lg", 0, 2);
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "/", var1.indexOf("LG"));
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      FilteredDeviceIndex var2 = this.getFilter().getIndex();
      String var3;
      return (var3 = StringMatchUtils.risMatch(var2.getUserAgents(), var1.getNormalizedDeviceUserAgent(), 7)) != null ? var2.getDeviceIdByUserAgent(var3) : "generic";
   }

   public final String getMatcherName() {
      return "LGMatcher";
   }

   public final String getBucketMatcherName() {
      return "LG";
   }
}
