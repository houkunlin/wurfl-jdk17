package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class J extends a {
   private static String b = "generic_android_htc_disguised_as_mac";

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public J(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return (var2 = var1.getCleanedDeviceUserAgent()).startsWith("Mozilla/5.0 (Macintosh") && var2.contains("HTC");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "---");
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "HTCMacMatcher";
   }

   public final String getBucketMatcherName() {
      return "HTCMac";
   }
}
