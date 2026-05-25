package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class h extends a {
   private static String b = "generic_skyfire_version2";
   private static String c = "generic_skyfire_version1";

   public h(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(c);
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Skyfire");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOf(var1, "Skyfire");
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, StringMatchUtils.indexOfOrLength(var1, ".", var2));
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? b : c;
   }

   public final String getMatcherName() {
      return "SkyfireMatcher";
   }

   public final String getBucketMatcherName() {
      return "Skyfire";
   }
}
