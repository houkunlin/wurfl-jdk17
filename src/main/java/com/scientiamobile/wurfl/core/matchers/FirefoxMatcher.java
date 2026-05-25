package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class FirefoxMatcher extends a {
   private static String FIREFOX_DEVICE_ID = "firefox";

   public FirefoxMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(FIREFOX_DEVICE_ID);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsMobileBrowser() && var2.contains("Firefox") && !StringMatchUtils.containsAnyOf(var2, "Tablet", "Sony", "Novarra", "Opera");
   }

   protected final String risMatch(String var1) {
      int var2;
      String var3;
      return (var2 = StringMatchUtils.indexOfOrLength(var3 = var1.substring(var1.indexOf("Firefox")), ".")) == -1 ? null : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var3, var2 + 1);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return FIREFOX_DEVICE_ID;
   }

   public final String getMatcherName() {
      return "FirefoxMatcher";
   }

   public final String getBucketMatcherName() {
      return "Firefox";
   }
}

