package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class JavaMidletMatcher extends MatcherBase {
   private static String GENERIC_MIDP_MIDLET = "generic_midp_midlet";

   public JavaMidletMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GENERIC_MIDP_MIDLET);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("UNTRUSTED/1.0");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return GENERIC_MIDP_MIDLET;
   }

   public final String getMatcherName() {
      return "JavaMidletMatcher";
   }

   public final String getBucketMatcherName() {
      return "JavaMidlet";
   }
}
