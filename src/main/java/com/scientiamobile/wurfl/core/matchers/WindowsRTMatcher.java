package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class WindowsRTMatcher extends AbstractMatcher {
   private static final String WINDOWS_8_RT_VER1_SUBOS81 = "windows_8_rt_ver1_subos81";
   private static final String GENERIC_WINDOWS_8_RT = "generic_windows_8_rt";

   public WindowsRTMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(GENERIC_WINDOWS_8_RT);
      var1.add(WINDOWS_8_RT_VER1_SUBOS81);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return StringMatchUtils.containsAllOf(var1.getCleanedDeviceUserAgent(), "Windows NT ", " ARM;", "Trident/");
   }

   protected final String risMatch(String var1) {
      if (var1.contains("like Gecko")) {
         int var2;
         if ((var2 = var1.indexOf(" Gecko")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 6);
         }
      } else {
         int var3;
         if ((var3 = var1.indexOf(" ARM;")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var3 + 5);
         }
      }

      return null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("like Gecko") ? WINDOWS_8_RT_VER1_SUBOS81 : GENERIC_WINDOWS_8_RT;
   }

   public final String getMatcherName() {
      return "WindowsRTMatcher";
   }

   public final String getBucketMatcherName() {
      return "WindowsRT";
   }
}
