package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class DoCoMoMatcher extends a {
   private static String DOCOMO_VER2 = "docomo_generic_jap_ver2";
   private static String DOCOMO_VER1 = "docomo_generic_jap_ver1";

   public DoCoMoMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(DOCOMO_VER1);
      var1.add(DOCOMO_VER2);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("DoCoMo");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = StringMatchUtils.secondSlash(var1)) == -1) {
         var2 = StringMatchUtils.firstOpenParenthesis(var1);
      }

      return var2 != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? DOCOMO_VER2 : DOCOMO_VER1;
   }

   public final String getMatcherName() {
      return "DoCoMoMatcher";
   }

   public final String getBucketMatcherName() {
      return "DoCoMo";
   }
}

