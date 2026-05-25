package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class MaemoMatcher extends a {
   private static String GENERIC_OPERA_MOBI_MAEMO = "generic_opera_mobi_maemo";
   private static String NOKIA_GENERIC_MAEMO_WITH_FIREFOX = "nokia_generic_maemo_with_firefox";
   private static String NOKIA_GENERIC_MAEMO = "nokia_generic_maemo";

   public MaemoMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(GENERIC_OPERA_MOBI_MAEMO);
      var1.add(NOKIA_GENERIC_MAEMO_WITH_FIREFOX);
      var1.add(NOKIA_GENERIC_MAEMO);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Maemo");
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("Opera Mobi")) {
         return GENERIC_OPERA_MOBI_MAEMO;
      } else {
         return var2.contains("Firefox") ? NOKIA_GENERIC_MAEMO_WITH_FIREFOX : NOKIA_GENERIC_MAEMO;
      }
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = var1.indexOf("---")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 3) : super.risMatch(var1);
   }

   public final String getMatcherName() {
      return "MaemoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Maemo";
   }
}

