package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class L extends a {
   private static String b = "generic_midp_midlet";

   public L(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("UNTRUSTED/1.0");
   }

   protected final String a(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "JavaMidletMatcher";
   }

   public final String getBucketMatcherName() {
      return "JavaMidlet";
   }
}
