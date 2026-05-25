package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Locale;

final class SanyoMatcher extends MatcherBase {
   public SanyoMatcher(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && (var2.toLowerCase(Locale.US).startsWith("sanyo") || var2.contains("MobilePhone"));
   }

   protected final String risMatch(String var1) {
      if (var1.contains("MobilePhone")) {
         int var2 = StringMatchUtils.indexOfOrLength(var1, "/", StringMatchUtils.indexOf(var1, "MobilePhone"));
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
      } else {
         return super.risMatch(var1);
      }
   }

   public final String getMatcherName() {
      return "SanyoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Sanyo";
   }
}
