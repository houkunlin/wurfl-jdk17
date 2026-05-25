package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;

final class VodafoneMatcher extends a {
   public VodafoneMatcher(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("Vodafone");
   }

   public final String getMatcherName() {
      return "VodafoneMatcher";
   }

   public final String getBucketMatcherName() {
      return "Vodafone";
   }
}
