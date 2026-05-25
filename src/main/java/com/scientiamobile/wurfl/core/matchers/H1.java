package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class H extends a {
   private static String b = "firefox";

   public H(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsMobileBrowser() && var2.contains("Firefox") && !StringMatchUtils.containsAnyOf(var2, "Tablet", "Sony", "Novarra", "Opera");
   }

   protected final String a(String var1) {
      int var2;
      String var3;
      return (var2 = StringMatchUtils.indexOfOrLength(var3 = var1.substring(var1.indexOf("Firefox")), ".")) == -1 ? null : StringMatchUtils.risMatch(this.getFilter().a().a(), var3, var2 + 1);
   }

   protected final String b(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "FirefoxMatcher";
   }

   public final String getBucketMatcherName() {
      return "Firefox";
   }
}
