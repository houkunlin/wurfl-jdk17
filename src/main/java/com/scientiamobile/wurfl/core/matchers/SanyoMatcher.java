package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Locale;

final class SanyoMatcher extends MatcherBase {
   public SanyoMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser()
         && (cleanedDeviceUserAgent.toLowerCase(Locale.US).startsWith("sanyo") || cleanedDeviceUserAgent.contains("MobilePhone"));
   }

   protected final String risMatch(String normalizedUserAgent) {
      if (normalizedUserAgent.contains("MobilePhone")) {
         int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", StringMatchUtils.indexOf(normalizedUserAgent, "MobilePhone"));
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
      } else {
         return super.risMatch(normalizedUserAgent);
      }
   }

   public final String getMatcherName() {
      return "SanyoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Sanyo";
   }
}
