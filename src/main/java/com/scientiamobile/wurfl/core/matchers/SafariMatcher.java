package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class SafariMatcher extends MatcherBase {
   public SafariMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("generic_web_browser");
      var1.add("generic_xhtml");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(var2, "Safari") && StringMatchUtils.startsWithAnyOf(var2, "Mozilla/5.0 (Macintosh", "Mozilla/5.0 (Windows");
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = var1.indexOf("---")) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 3) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      return !(var2 = var1.getNormalizedDeviceUserAgent()).contains("Macintosh") && !var2.contains("Windows") ? "generic_xhtml" : "generic_web_browser";
   }

   public final String getMatcherName() {
      return "SafariMatcher";
   }

   public final String getBucketMatcherName() {
      return "Safari";
   }
}
