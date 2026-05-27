package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class WindowsPhoneMatcher extends AbstractMatcher {
   private static final String GENERIC_MS_PHONE_OS7 = "generic_ms_phone_os7";
   private static final String GENERIC_MS_PHONE_OS7_DESKTOPMODE = "generic_ms_phone_os7_desktopmode";
   private static final String GENERIC_MS_PHONE_OS7_5_DESKTOPMODE = "generic_ms_phone_os7_5_desktopmode";
   private static final String GENERIC_MS_PHONE_OS8_DESKTOPMODE = "generic_ms_phone_os8_desktopmode";
   private static final String GENERIC_MS_PHONE_OS10_DESKTOPMODE = "generic_ms_phone_os10_desktopmode";
   private static final Map<String, String> VERSION_TO_DEVICE_ID;

   public WindowsPhoneMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   @Override
   protected Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.addAll(VERSION_TO_DEVICE_ID.values());
      requiredDeviceIds.add(GENERIC_MS_PHONE_OS7_DESKTOPMODE);
      requiredDeviceIds.add(GENERIC_MS_PHONE_OS7_5_DESKTOPMODE);
      requiredDeviceIds.add(GENERIC_MS_PHONE_OS8_DESKTOPMODE);
      requiredDeviceIds.add(GENERIC_MS_PHONE_OS10_DESKTOPMODE);
      requiredDeviceIds.add("generic");
      return requiredDeviceIds;
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && (StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "WPDesktop", "ZuneWP7") || StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") || StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Windows Phone", "WindowsPhone", "NativeHost"));
   }

   @Override
   protected String applyConclusiveMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      boolean containsSeparator = normalizedUserAgent.contains("---");
      if (!containsSeparator || !StringMatchUtils.containsAnyOf(normalizedUserAgent, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(normalizedUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") && !UserAgentUtils.isWindowsPhoneAdClient(request.getCleanedDeviceUserAgent())) {
         return !containsSeparator && normalizedUserAgent.contains("NativeHost") ? GENERIC_MS_PHONE_OS7 : super.applyConclusiveMatch(request);
      } else {
         return super.applyConclusiveMatch(request);
      }
   }

   @Override
   protected String risMatch(String userAgent) {
      int index;
      return (index = userAgent.indexOf("---")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, index + 3) : null;
   }

   @Override
   protected String applyRecoveryMatch(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      boolean isDesktopMode = StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "WPDesktop", "ZuneWP7");
      boolean isArmEdge = false;
      if (!isDesktopMode) {
         isArmEdge = StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/");
      }

      if (!isDesktopMode && !isArmEdge) {
         String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(cleanedDeviceUserAgent);
         String deviceId;
         deviceId = VERSION_TO_DEVICE_ID.get(windowsPhoneVersion);
         if (deviceId != null) {
            return deviceId;
         } else {
            return UserAgentUtils.isWindowsPhoneAdClient(cleanedDeviceUserAgent) ? GENERIC_MS_PHONE_OS7 : "generic";
         }
      } else if (isArmEdge) {
         return GENERIC_MS_PHONE_OS10_DESKTOPMODE;
      } else if (cleanedDeviceUserAgent.contains("WPDesktop")) {
         return GENERIC_MS_PHONE_OS8_DESKTOPMODE;
      } else {
         return cleanedDeviceUserAgent.contains("Trident/5.0") ? GENERIC_MS_PHONE_OS7_5_DESKTOPMODE : GENERIC_MS_PHONE_OS7_DESKTOPMODE;
      }
   }

   @Override
   public String getMatcherName() {
      return "WindowsPhoneMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "WindowsPhone";
   }

   static {
      (VERSION_TO_DEVICE_ID = new HashMap<>()).put("10.0", "generic_ms_phone_os10");
      VERSION_TO_DEVICE_ID.put("8.1", "generic_ms_phone_os8_1");
      VERSION_TO_DEVICE_ID.put("8.0", "generic_ms_phone_os8");
      VERSION_TO_DEVICE_ID.put("7.8", "generic_ms_phone_os7_8");
      VERSION_TO_DEVICE_ID.put("7.5", "generic_ms_phone_os7_5");
      VERSION_TO_DEVICE_ID.put("7.0", GENERIC_MS_PHONE_OS7);
      VERSION_TO_DEVICE_ID.put("6.5", "generic_ms_winmo6_5");
   }
}
