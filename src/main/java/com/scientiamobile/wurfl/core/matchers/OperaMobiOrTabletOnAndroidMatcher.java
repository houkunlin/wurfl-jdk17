package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class OperaMobiOrTabletOnAndroidMatcher extends a {
   private static final String GENERIC_ANDROID_VER2_0_OPERA_MOBI = "generic_android_ver2_0_opera_mobi";
   private static final String GENERIC_ANDROID_VER2_1_OPERA_TABLET = "generic_android_ver2_1_opera_tablet";
   private static final Set SUPPORTED_ANDROID_OPERA_DEVICE_IDS;

   public OperaMobiOrTabletOnAndroidMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      return new HashSet(SUPPORTED_ANDROID_OPERA_DEVICE_IDS);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Android") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Opera Tablet", "Opera Mobi");
   }

   protected final String risMatch(String var1) {
      int var2;
      var2 = (var2 = var1.indexOf("---")) == -1 ? var1.length() : var2 + 3;
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var3;
      boolean var2 = (var3 = var1.getNormalizedDeviceUserAgent()).contains("Opera Tablet");
      String var4 = UserAgentUtils.getAndroidVersion(var3, true);
      var4 = "generic_android_ver" + var4.replaceAll("\\.", "_") + "_opera_" + (var2 ? "tablet" : "mobi");
      if (SUPPORTED_ANDROID_OPERA_DEVICE_IDS.contains(var4)) {
         return var4;
      } else {
         return var2 ? GENERIC_ANDROID_VER2_1_OPERA_TABLET : GENERIC_ANDROID_VER2_0_OPERA_MOBI;
      }
   }

   public final String getMatcherName() {
      return "OperaMobiOrTabletOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMobiOrTabletOnAndroid";
   }

   static {
      (SUPPORTED_ANDROID_OPERA_DEVICE_IDS = new HashSet()).add("generic_android_ver1_5_opera_mobi");
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
