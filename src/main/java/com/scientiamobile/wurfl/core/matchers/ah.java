package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ah extends a {
   private static String b = "mot_mib22_generic";

   public ah(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add("generic");
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(var2, "Mot-", "MOT-", "MOTO", "moto") || var2.contains("Motorola");
   }

   protected final String b(WURFLRequest var1) {
      return StringMatchUtils.containsAnyOf(var1.getNormalizedDeviceUserAgent(), "MIB/2.2", "MIB/BER2.2") ? b : "generic";
   }

   public final String getMatcherName() {
      return "MotorolaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Motorola";
   }
}
