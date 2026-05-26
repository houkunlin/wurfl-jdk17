package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class OperaMobiOrTabletOnAndroidMatcher extends MatcherBase {
   private static final String GENERIC_ANDROID_VER2_0_OPERA_MOBI = "generic_android_ver2_0_opera_mobi";
   private static final String GENERIC_ANDROID_VER2_1_OPERA_TABLET = "generic_android_ver2_1_opera_tablet";
   private static final Set<String> SUPPORTED_ANDROID_OPERA_DEVICE_IDS;

   public OperaMobiOrTabletOnAndroidMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      return new HashSet<>(SUPPORTED_ANDROID_OPERA_DEVICE_IDS);
   }

   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Opera Tablet", "Opera Mobi");
   }

   protected final String risMatch(String userAgent) {
      int matchLength;
      matchLength = (matchLength = userAgent.indexOf("---")) == -1 ? userAgent.length() : matchLength + 3;
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      boolean isOperaTablet = normalizedUserAgent.contains("Opera Tablet");
      String androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent, true);
      String deviceId = "generic_android_ver" + androidVersion.replaceAll("\\.", "_") + "_opera_" + (isOperaTablet ? "tablet" : "mobi");
      if (SUPPORTED_ANDROID_OPERA_DEVICE_IDS.contains(deviceId)) {
         return deviceId;
      } else {
         return isOperaTablet ? GENERIC_ANDROID_VER2_1_OPERA_TABLET : GENERIC_ANDROID_VER2_0_OPERA_MOBI;
      }
   }

   public final String getMatcherName() {
      return "OperaMobiOrTabletOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMobiOrTabletOnAndroid";
   }

   static {
      (SUPPORTED_ANDROID_OPERA_DEVICE_IDS = new HashSet<>()).add("generic_android_ver1_5_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver1_6_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_0_OPERA_MOBI);
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_1_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_mobi");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add(GENERIC_ANDROID_VER2_1_OPERA_TABLET);
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_2_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver2_3_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_0_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_1_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver3_2_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_0_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_1_opera_tablet");
      SUPPORTED_ANDROID_OPERA_DEVICE_IDS.add("generic_android_ver4_2_opera_tablet");
   }
}
