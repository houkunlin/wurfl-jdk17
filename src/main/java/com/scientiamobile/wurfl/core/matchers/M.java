package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class M extends a {
   private static String b = "opwv_v62_generic";

   public M(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("KDDI-");
   }

   protected final String a(String var1) {
      int var2;
      return (var2 = var1.startsWith("KDDI/") ? StringMatchUtils.secondSlash(var1) : StringMatchUtils.firstSlash(var1)) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "KDDIMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kddi";
   }
}
