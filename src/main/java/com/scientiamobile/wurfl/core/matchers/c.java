package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class c extends a {
   private static String b = "generic_reksio";

   public c(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWith(var1.getCleanedDeviceUserAgent(), "Reksio");
   }

   protected final String a(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "ReksioMatcher";
   }

   public final String getBucketMatcherName() {
      return "Reksio";
   }
}
