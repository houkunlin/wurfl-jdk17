package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class AndroidMatcher extends AbstractMatcher {
   private static final String GENERIC_ANDROID = "generic_android";
   private static final String GENERIC_ANDROID_VER2_2 = "generic_android_ver2_2";
   private static final String GENERIC_ANDROID_VER1_5_TABLET = "generic_android_ver1_5_tablet";
   private static final Set<String> SUPPORTED_MOBILE_DEVICE_IDS = new HashSet<>();
   private static final Set<String> SUPPORTED_TABLET_DEVICE_IDS = new HashSet<>();

   public AndroidMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(SUPPORTED_MOBILE_DEVICE_IDS);
      var1.addAll(SUPPORTED_TABLET_DEVICE_IDS);
      var1.add(GENERIC_ANDROID);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "like Android", "Symbian") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Android", "android");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = var1.indexOf("---")) >= 0) {
         var2 += 3;
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
      } else if (!StringMatchUtils.startsWithAnyOf(var1, "Mozilla", "Dalvik")) {
         return null;
      } else {
         String var3;
         if ((var3 = UserAgentUtils.getAndroidModel(var1)) != null && var3.length() != 0) {
            int var5 = Math.min(StringMatchUtils.indexOfOrLength(var1, " Build/"), StringMatchUtils.indexOfOrLength(var1, " AppleWebKit"));
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var5);
         } else {
            int var4 = var1.length();
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var4);
         }
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var3;
      String var2 = UserAgentUtils.getAndroidVersion(var3 = var1.getNormalizedDeviceUserAgent(), true).replaceAll("\\.", "_");
      if ((var2 = "generic_android_ver" + var2).endsWith("2_0") || var2.endsWith("4_0")) {
         var2 = var2.substring(0, var2.length() - 2);
      }

      if (!var2.startsWith("generic_android_ver3_") && !var3.contains("Mobile") && var3.contains("Safari")) {
         var2 = var2.concat("_tablet");
         return SUPPORTED_TABLET_DEVICE_IDS.contains(var2) ? var2 : GENERIC_ANDROID_VER1_5_TABLET;
      } else {
         return SUPPORTED_MOBILE_DEVICE_IDS.contains(var2) ? var2 : GENERIC_ANDROID;
      }
   }

   public final String getMatcherName() {
      return "AndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "Android";
   }

   static {
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver1_5");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver1_6");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add(GENERIC_ANDROID_VER2_2);
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver2_3");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_0");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_2");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver3_3");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_2");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_3");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_4");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver4_5");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_0");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_2");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver5_3");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver6_0");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver6_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_0");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver7_2");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver8_0");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver8_1");
      SUPPORTED_MOBILE_DEVICE_IDS.add("generic_android_ver9_0");
      SUPPORTED_TABLET_DEVICE_IDS.add(GENERIC_ANDROID_VER1_5_TABLET);
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver1_6_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_2_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver2_3_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_2_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_3_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_4_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver4_5_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_0_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_2_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver5_3_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver6_0_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver6_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_0_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver7_2_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver8_0_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver8_1_tablet");
      SUPPORTED_TABLET_DEVICE_IDS.add("generic_android_ver9_0_tablet");
   }
}
