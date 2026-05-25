package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class q extends a {
   private static String b = "windows_8_rt_ver1_subos81";
   private static String c = "generic_windows_8_rt";

   public q(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(c);
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return StringMatchUtils.containsAllOf(var1.getCleanedDeviceUserAgent(), "Windows NT ", " ARM;", "Trident/");
   }

   protected final String a(String var1) {
      if (var1.contains("like Gecko")) {
         int var2;
         if ((var2 = var1.indexOf(" Gecko")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 6);
         }
      } else {
         int var3;
         if ((var3 = var1.indexOf(" ARM;")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var3 + 5);
         }
      }

      return null;
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("like Gecko") ? b : c;
   }

   public final String getMatcherName() {
      return "WindowsRTMatcher";
   }

   public final String getBucketMatcherName() {
      return "WindowsRT";
   }
}
