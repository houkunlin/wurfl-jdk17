package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

public class UbuntuTouchOSMatcher extends MatcherBase {
   private static final String GENERIC_UBUNTU_TOUCH_OS = "generic_ubuntu_touch_os";
   private static final String GENERIC_UBUNTU_TOUCH_OS_TABLET = "generic_ubuntu_touch_os_tablet";

   public UbuntuTouchOSMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS);
      requiredDeviceIds.add(GENERIC_UBUNTU_TOUCH_OS_TABLET);
      return requiredDeviceIds;
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return cleanedDeviceUserAgent.contains("Ubuntu") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Mobile", "Tablet");
   }

   @Override
   protected String risMatch(String userAgent) {
      int matchLength;
      if ((matchLength = userAgent.indexOf("like Android")) >= 0) {
         matchLength += 12;
      } else if ((matchLength = userAgent.indexOf("WebKit/")) >= 0) {
         matchLength += 7;
      }

      return matchLength >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength) : null;
   }

   @Override
   protected String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().contains("Tablet") ? GENERIC_UBUNTU_TOUCH_OS_TABLET : GENERIC_UBUNTU_TOUCH_OS;
   }

   @Override
   public String getMatcherName() {
      return "UbuntuTouchOSMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "UbuntuTouchOS";
   }
}
