package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class HTCMatcher extends MatcherBase {
   private static final Pattern HTC_PREFIX = Pattern.compile("^.*?HTC.+?[/ ;]");

   public HTCMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("generic");
      var1.add("generic_ms_mobile");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "HTC", "XV6875");
   }

   protected final String risMatch(String var1) {
      int var2 = var1.length();
      Matcher var3;
      if ((var3 = HTC_PREFIX.matcher(var1)).find()) {
         var2 = var3.group(0).length();
      }

      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
   }

   public final String getMatcherName() {
      return "HTCMatcher";
   }

   public final String getBucketMatcherName() {
      return "HTC";
   }
}
