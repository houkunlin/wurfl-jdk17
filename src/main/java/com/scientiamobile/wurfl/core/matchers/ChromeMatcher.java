package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class ChromeMatcher extends MatcherBase {
   private static String CHROME_DEVICE_ID = "google_chrome";

   public ChromeMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(CHROME_DEVICE_ID);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringUtils.contains(var1.getCleanedDeviceUserAgent(), "Chrome");
   }

   protected final String risMatch(String var1) {
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, StringMatchUtils.indexOfOrLength(var1, "."));
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return CHROME_DEVICE_ID;
   }

   public final String getMatcherName() {
      return "ChromeMatcher";
   }

   public final String getBucketMatcherName() {
      return "Chrome";
   }
}
