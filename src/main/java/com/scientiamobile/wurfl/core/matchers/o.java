package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class WebOSMatcher extends AbstractMatcher {
   private static String b = "hp_tablet_webos_generic";
   private static String c = "hp_webos_generic";

   public WebOSMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add(c);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "webOS", "hpwOS");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "---");
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("hpwOS/3") ? b : c;
   }

   public final String getMatcherName() {
      return "WebOSMatcher";
   }

   public final String getBucketMatcherName() {
      return "WebOS";
   }
}
