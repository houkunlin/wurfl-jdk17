package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class WebOSMatcher extends AbstractMatcher {
   private static final String HP_TABLET_WEBOS_GENERIC = "hp_tablet_webos_generic";
   private static final String HP_WEBOS_GENERIC = "hp_webos_generic";

   public WebOSMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(HP_TABLET_WEBOS_GENERIC);
      var1.add(HP_WEBOS_GENERIC);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "webOS", "hpwOS");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "---");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("hpwOS/3") ? HP_TABLET_WEBOS_GENERIC : HP_WEBOS_GENERIC;
   }

   public final String getMatcherName() {
      return "WebOSMatcher";
   }

   public final String getBucketMatcherName() {
      return "WebOS";
   }
}
