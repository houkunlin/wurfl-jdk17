package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Locale;

final class AlcatelMatcher extends MatcherBase {
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel");
   }

   public final String getMatcherName() {
      return "AlcatelMatcher";
   }

   public final String getBucketMatcherName() {
      return "Alcatel";
   }
}
