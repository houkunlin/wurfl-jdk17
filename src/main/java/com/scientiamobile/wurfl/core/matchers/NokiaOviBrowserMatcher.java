package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class NokiaOviBrowserMatcher extends MatcherBase {
   private static final String NOKIA_GENERIC_SERIES30PLUS = "nokia_generic_series30plus";
   private static final String NOKIA_GENERIC_SERIES40_OVIBROSR = "nokia_generic_series40_ovibrosr";

   public NokiaOviBrowserMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(NOKIA_GENERIC_SERIES30PLUS);
      var1.add(NOKIA_GENERIC_SERIES40_OVIBROSR);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("S40OviBrowser");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfAnyOrLength(var1, new String[]{"/", " "}, var1.indexOf("Nokia"));
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Series30Plus") ? NOKIA_GENERIC_SERIES30PLUS : NOKIA_GENERIC_SERIES40_OVIBROSR;
   }

   public final String getMatcherName() {
      return "NokiaOviBrowserMatcher";
   }

   public final String getBucketMatcherName() {
      return "NokiaOviBrowser";
   }
}
