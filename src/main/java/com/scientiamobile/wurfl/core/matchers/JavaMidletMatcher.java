package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class JavaMidletMatcher extends MatcherBase {
   private static final String GENERIC_MIDP_MIDLET = "generic_midp_midlet";

   public JavaMidletMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(GENERIC_MIDP_MIDLET);
      return requiredDeviceIds;
   }

   public final boolean canHandle(WURFLRequest request) {
      return request.getCleanedDeviceUserAgent().contains("UNTRUSTED/1.0");
   }

   protected final String applyConclusiveMatch(WURFLRequest request) {
      return GENERIC_MIDP_MIDLET;
   }

   public final String getMatcherName() {
      return "JavaMidletMatcher";
   }

   public final String getBucketMatcherName() {
      return "JavaMidlet";
   }
}
