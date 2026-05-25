package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class D extends a {
   private static String b = "docomo_generic_jap_ver2";
   private static String c = "docomo_generic_jap_ver1";

   public D(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(c);
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().startsWith("DoCoMo");
   }

   protected final String a(String var1) {
      int var2;
      if ((var2 = StringMatchUtils.secondSlash(var1)) == -1) {
         var2 = StringMatchUtils.firstOpenParenthesis(var1);
      }

      return var2 != -1 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? b : c;
   }

   public final String getMatcherName() {
      return "DoCoMoMatcher";
   }

   public final String getBucketMatcherName() {
      return "DoCoMo";
   }
}
