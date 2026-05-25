package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class XBoxMatcher extends AbstractMatcher {
   private static String b = "microsoft_xboxone_ver1";
   private static String c = "microsoft_xbox360_ver1_subie10";
   private static String d = "microsoft_xbox360_ver1";

   public XBoxMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add(c);
      var1.add(d);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Xbox");
   }

   protected final String a(WURFLRequest var1) {
      return null;
   }

   protected final String b(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("MSIE 10.0")) {
         return var2.contains("Xbox One") ? b : c;
      } else {
         return d;
      }
   }

   public final String getMatcherName() {
      return "XBoxMatcher";
   }

   public final String getBucketMatcherName() {
      return "Xbox";
   }
}
