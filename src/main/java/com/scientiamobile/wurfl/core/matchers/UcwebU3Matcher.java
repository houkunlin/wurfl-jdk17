package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

final class UcwebU3Matcher extends a {
   private static final String GENERIC_MS_PHONE_OS8_SUBUAWCWEB = "generic_ms_phone_os8_subuaucweb";
   private static final String GENERIC_UCWEB_ANDROID_VER1 = "generic_ucweb_android_ver1";
   private static final String APPLE_IPHONE_VER1_SUBUAWCWEB = "apple_iphone_ver1_subuaucweb";
   private static final String APPLE_IPAD_VER1_SUBUAWCWEB = "apple_ipad_ver1_subuaucweb";
   private static final Pattern IPHONE_IOS_VERSION = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
   private static final Pattern IPAD_IOS_VERSION = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
   private static final List SUPPORTED_DEVICE_IDS;

   public UcwebU3Matcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(SUPPORTED_DEVICE_IDS);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2.startsWith("Mozilla") && var2.contains("UCBrowser");
   }

   protected final String risMatch(String var1) {
      if (UserAgentUtils.getUcBrowserVersion(var1, false) == null) {
         return null;
      } else {
         int var2 = var1.indexOf("---") + 3;
         if (var1.contains("Windows Phone")) {
            String var3 = UserAgentUtils.getWindowsPhoneVersion(var1);
            if (UserAgentUtils.getWindowsPhoneModel(var1) != null && var3 != null) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
            }
         } else if (var1.contains("Android")) {
            String var5 = UserAgentUtils.getAndroidModel(var1);
            String var4 = UserAgentUtils.getAndroidVersion(var1, false);
            if (var5 != null && var4 != null) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
            }
         } else if (var1.contains("iPhone;")) {
            if (UcwebU3Normalizer.IPHONE.matcher(var1).find()) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
            }
         } else if (var1.contains("iPad") && UcwebU3Normalizer.IPAD.matcher(var1).find()) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
         }

         return null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2 = var1.getNormalizedDeviceUserAgent();
      if (var2.contains("Windows Phone")) {
         String var3 = UserAgentUtils.getWindowsPhoneVersion(var2);
         String var4 = null;
         if (StringUtils.isNotEmpty(var3)) {
            String[] var5 = var3.split("\\.");
            if (var5.length >= 2) {
               String var6 = var5[0];
               String var7 = var5[1];
               if (StringUtils.isEmpty(var7)) {
                  var4 = "generic_ms_phone_os" + var6 + "_subuaucweb";
               } else {
                  var4 = "generic_ms_phone_os" + var6 + "_" + var7 + "_subuaucweb";
               }
            }
         } else {
            LOG.debug("user agent " + var2 + " has no version information");
         }

         return SUPPORTED_DEVICE_IDS.contains(var4) ? var4 : GENERIC_MS_PHONE_OS8_SUBUAWCWEB;
      } else if (var2.contains("Android")) {
         String var9 = UserAgentUtils.getAndroidVersion(var2, false);
         String var10 = null;
         if (StringUtils.isNotEmpty(var9)) {
            String[] var11 = var9.split("\\.");
            if (var11.length > 0) {
               var10 = "generic_ucweb_android_ver" + var11[0];
            }
         }

         return SUPPORTED_DEVICE_IDS.contains(var10) ? var10 : GENERIC_UCWEB_ANDROID_VER1;
      } else if (var2.contains("iPhone")) {
         Matcher var12 = IPHONE_IOS_VERSION.matcher(var2);
         String var13 = null;
         if (var12.find() && var12.groupCount() > 0) {
            var13 = "apple_iphone_ver" + var12.group(1) + "_subuaucweb";
         }

         return SUPPORTED_DEVICE_IDS.contains(var13) ? var13 : APPLE_IPHONE_VER1_SUBUAWCWEB;
      } else if (var2.contains("iPad")) {
         Matcher var14 = IPAD_IOS_VERSION.matcher(var2);
         String var15 = null;
         if (var14.find() && var14.groupCount() > 0) {
            var15 = "apple_ipad_ver1_sub" + var14.group(1) + "_subuaucweb";
         }

         return SUPPORTED_DEVICE_IDS.contains(var15) ? var15 : APPLE_IPAD_VER1_SUBUAWCWEB;
      } else {
         return "generic_ucweb";
      }
   }

   public final String getMatcherName() {
      return "UcwebU3Matcher";
   }

   public final String getBucketMatcherName() {
      return "UcwebU3";
   }

   static {
      (SUPPORTED_DEVICE_IDS = new ArrayList()).add("generic_ucweb");
      SUPPORTED_DEVICE_IDS.add(GENERIC_UCWEB_ANDROID_VER1);
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver2");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver3");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver4");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver5");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver6");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver7");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver8");
      SUPPORTED_DEVICE_IDS.add("generic_ucweb_android_ver9");
      SUPPORTED_DEVICE_IDS.add(APPLE_IPHONE_VER1_SUBUAWCWEB);
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver2_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver3_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver4_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver5_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver6_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver7_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver8_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver9_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver10_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver11_subuaucweb");
      SUPPORTED_DEVICE_IDS.add(APPLE_IPAD_VER1_SUBUAWCWEB);
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub4_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub5_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub6_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub7_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub8_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub9_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub10_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub11_subuaucweb");
      SUPPORTED_DEVICE_IDS.add(GENERIC_MS_PHONE_OS8_SUBUAWCWEB);
      SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os8_1_subuaucweb");
      SUPPORTED_DEVICE_IDS.add("generic_ms_phone_os10_subuaucweb");
   }
}
