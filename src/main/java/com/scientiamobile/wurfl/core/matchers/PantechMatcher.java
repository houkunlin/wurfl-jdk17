package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class PantechMatcher extends a {
   public PantechMatcher(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(var2, "Pantech", "PT-", "PANTECH", "PG-");
   }

   public final String getMatcherName() {
      return "PantechMatcher";
   }

   public final String getBucketMatcherName() {
      return "Pantech";
   }
}

