package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

public class UbuntuTouchOSMatcher extends MatcherBase {
   private static final String GENERIC_UBUNTU_TOUCH_OS = "generic_ubuntu_touch_os";
   private static final String GENERIC_UBUNTU_TOUCH_OS_TABLET = "generic_ubuntu_touch_os_tablet";

   public UbuntuTouchOSMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GENERIC_UBUNTU_TOUCH_OS);
      var1.add(GENERIC_UBUNTU_TOUCH_OS_TABLET);
      return var1;
   }

   public boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Ubuntu") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Mobile", "Tablet");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = var1.indexOf("like Android")) >= 0) {
         var2 += 12;
      } else if ((var2 = var1.indexOf("WebKit/")) >= 0) {
         var2 += 7;
      }

      return var2 >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Tablet") ? GENERIC_UBUNTU_TOUCH_OS_TABLET : GENERIC_UBUNTU_TOUCH_OS;
   }

   public String getMatcherName() {
      return "UbuntuTouchOSMatcher";
   }

   public String getBucketMatcherName() {
      return "UbuntuTouchOS";
   }
}
