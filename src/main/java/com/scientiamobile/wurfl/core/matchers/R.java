package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class R extends a {
   private static String b = "generic_opera_mobi_maemo";
   private static String c = "nokia_generic_maemo_with_firefox";
   private static String d = "nokia_generic_maemo";

   public R(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add(c);
      var1.add(d);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Maemo");
   }

   protected final String b(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("Opera Mobi")) {
         return b;
      } else {
         return var2.contains("Firefox") ? c : d;
      }
   }

   protected final String a(String var1) {
      int var2;
      return (var2 = var1.indexOf("---")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 3) : super.a(var1);
   }

   public final String getMatcherName() {
      return "MaemoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Maemo";
   }
}
