package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class NintendoMatcher extends MatcherBase {
   public NintendoMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add("nintendo_wii_u_ver1");
      requiredDeviceIds.add("nintendo_wii_ver1");
      requiredDeviceIds.add("nintendo_dsi_ver1");
      requiredDeviceIds.add("nintendo_ds_ver1");
      requiredDeviceIds.add("nintendo_3ds_ver1");
      requiredDeviceIds.add("nintendo_new3ds_ver1");
      requiredDeviceIds.add("nintendo_switch_ver1");
      return requiredDeviceIds;
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      if (request._internalIsDesktopBrowser()) {
         return false;
      } else if (cleanedDeviceUserAgent.contains("Nintendo")) {
         return true;
      } else {
         return cleanedDeviceUserAgent.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Nitro", "Opera");
      }
   }

   @Override
   protected String applyConclusiveMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      if (normalizedUserAgent.contains("New Nintendo 3DS")) {
         return "nintendo_new3ds_ver1";
      } else if (normalizedUserAgent.contains("Nintendo 3DS")) {
         return "nintendo_3ds_ver1";
      } else if (normalizedUserAgent.contains("Nintendo WiiU")) {
         return "nintendo_wii_u_ver1";
      } else if (normalizedUserAgent.contains("Nintendo Wii")) {
         return "nintendo_wii_ver1";
      } else if (normalizedUserAgent.contains("Nintendo DSi")) {
         return "nintendo_dsi_ver1";
      } else if (normalizedUserAgent.contains("Nintendo Switch")) {
         return "nintendo_switch_ver1";
      } else {
         return normalizedUserAgent.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(normalizedUserAgent, "Nitro", "Opera") ? "nintendo_ds_ver1" : "nintendo_wii_ver1";
      }
   }

   @Override
   public String getMatcherName() {
      return "NintendoMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "Nintendo";
   }
}
