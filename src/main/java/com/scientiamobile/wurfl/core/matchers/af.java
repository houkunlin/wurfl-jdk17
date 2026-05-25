package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Locale;

final class af extends a {
   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel");
   }

   public final String getMatcherName() {
      return "AlcatelMatcher";
   }

   public final String getBucketMatcherName() {
      return "Alcatel";
   }
}
