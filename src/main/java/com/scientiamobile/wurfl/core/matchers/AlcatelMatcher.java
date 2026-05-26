package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.util.Locale;

final class AlcatelMatcher extends MatcherBase {
   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().toLowerCase(Locale.US).startsWith("alcatel");
   }

   @Override
   public final String getMatcherName() {
      return "AlcatelMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Alcatel";
   }
}
