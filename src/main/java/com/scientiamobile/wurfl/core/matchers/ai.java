package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class ai extends a {
   public ai(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(var1.getCleanedDeviceUserAgent(), "NEC-", "KGT");
   }

   public final String getMatcherName() {
      return "NecMatcher";
   }

   public final String getBucketMatcherName() {
      return "Nec";
   }
}
