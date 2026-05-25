package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class am extends a {
   private static String b = "nokia_generic_series30plus";
   private static String c = "nokia_generic_series40_ovibrosr";

   public am(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add(c);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("S40OviBrowser");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOfAnyOrLength(var1, new String[]{"/", " "}, var1.indexOf("Nokia"));
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Series30Plus") ? b : c;
   }

   public final String getMatcherName() {
      return "NokiaOviBrowserMatcher";
   }

   public final String getBucketMatcherName() {
      return "NokiaOviBrowser";
   }
}
