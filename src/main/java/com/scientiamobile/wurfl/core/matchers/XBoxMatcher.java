package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class XBoxMatcher extends AbstractMatcher {
   private static final String MICROSOFT_XBOXONE_VER1 = "microsoft_xboxone_ver1";
   private static final String MICROSOFT_XBOX360_VER1_SUBIE10 = "microsoft_xbox360_ver1_subie10";
   private static final String MICROSOFT_XBOX360_VER1 = "microsoft_xbox360_ver1";

   public XBoxMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(MICROSOFT_XBOXONE_VER1);
      var1.add(MICROSOFT_XBOX360_VER1_SUBIE10);
      var1.add(MICROSOFT_XBOX360_VER1);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Xbox");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("MSIE 10.0")) {
         return var2.contains("Xbox One") ? MICROSOFT_XBOXONE_VER1 : MICROSOFT_XBOX360_VER1_SUBIE10;
      } else {
         return MICROSOFT_XBOX360_VER1;
      }
   }

   public final String getMatcherName() {
      return "XBoxMatcher";
   }

   public final String getBucketMatcherName() {
      return "Xbox";
   }
}
