package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class WindowsRTMatcher extends AbstractMatcher {
   private static final String WINDOWS_8_RT_VER1_SUBOS81 = "windows_8_rt_ver1_subos81";
   private static final String GENERIC_WINDOWS_8_RT = "generic_windows_8_rt";

   public WindowsRTMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add(GENERIC_WINDOWS_8_RT);
      requiredDeviceIds.add(WINDOWS_8_RT_VER1_SUBOS81);
      return requiredDeviceIds;
   }

   public final boolean canHandle(WURFLRequest request) {
      return StringMatchUtils.containsAllOf(request.getCleanedDeviceUserAgent(), "Windows NT ", " ARM;", "Trident/");
   }

   protected final String risMatch(String userAgent) {
      if (userAgent.contains("like Gecko")) {
         int geckoIndex;
         if ((geckoIndex = userAgent.indexOf(" Gecko")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, geckoIndex + 6);
         }
      } else {
         int armIndex;
         if ((armIndex = userAgent.indexOf(" ARM;")) >= 0) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, armIndex + 5);
         }
      }

      return null;
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().contains("like Gecko") ? WINDOWS_8_RT_VER1_SUBOS81 : GENERIC_WINDOWS_8_RT;
   }

   public final String getMatcherName() {
      return "WindowsRTMatcher";
   }

   public final String getBucketMatcherName() {
      return "WindowsRT";
   }
}
