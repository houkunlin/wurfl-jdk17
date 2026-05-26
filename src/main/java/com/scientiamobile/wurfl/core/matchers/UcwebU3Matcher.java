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
import org.apache.commons.lang3.StringUtils;

final class UcwebU3Matcher extends MatcherBase {
   private static final String GENERIC_MS_PHONE_OS8_SUBUAWCWEB = "generic_ms_phone_os8_subuaucweb";
   private static final String GENERIC_UCWEB_ANDROID_VER1 = "generic_ucweb_android_ver1";
   private static final String APPLE_IPHONE_VER1_SUBUAWCWEB = "apple_iphone_ver1_subuaucweb";
   private static final String APPLE_IPAD_VER1_SUBUAWCWEB = "apple_ipad_ver1_subuaucweb";
   private static final Pattern IPHONE_IOS_VERSION = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
   private static final Pattern IPAD_IOS_VERSION = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
   private static final List<String> SUPPORTED_DEVICE_IDS;

   public UcwebU3Matcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      return new HashSet<>(SUPPORTED_DEVICE_IDS);
   }

   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("Mozilla") && cleanedDeviceUserAgent.contains("UCBrowser");
   }

   protected final String risMatch(String userAgent) {
      if (UserAgentUtils.getUcBrowserVersion(userAgent, false) == null) {
         return null;
      } else {
         int matchLength = userAgent.indexOf("---") + 3;
         if (userAgent.contains("Windows Phone")) {
            String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(userAgent);
            if (UserAgentUtils.getWindowsPhoneModel(userAgent) != null && windowsPhoneVersion != null) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
            }
         } else if (userAgent.contains("Android")) {
            String androidModel = UserAgentUtils.getAndroidModel(userAgent);
            String androidVersion = UserAgentUtils.getAndroidVersion(userAgent, false);
            if (androidModel != null && androidVersion != null) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
            }
         } else if (userAgent.contains("iPhone;")) {
            if (UcwebU3Normalizer.IPHONE.matcher(userAgent).find()) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
            }
         } else if (userAgent.contains("iPad") && UcwebU3Normalizer.IPAD.matcher(userAgent).find()) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
         }

         return null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      if (normalizedUserAgent.contains("Windows Phone")) {
         String windowsPhoneVersion = UserAgentUtils.getWindowsPhoneVersion(normalizedUserAgent);
         String windowsPhoneDeviceId = null;
         if (StringUtils.isNotEmpty(windowsPhoneVersion)) {
            String[] versionParts = windowsPhoneVersion.split("\\.");
            if (versionParts.length >= 2) {
               String major = versionParts[0];
               String minor = versionParts[1];
               if (StringUtils.isEmpty(minor)) {
                  windowsPhoneDeviceId = "generic_ms_phone_os" + major + "_subuaucweb";
               } else {
                  windowsPhoneDeviceId = "generic_ms_phone_os" + major + "_" + minor + "_subuaucweb";
               }
            }
         } else {
            LOG.debug("user agent {} has no version information", normalizedUserAgent);
         }

         return SUPPORTED_DEVICE_IDS.contains(windowsPhoneDeviceId) ? windowsPhoneDeviceId : GENERIC_MS_PHONE_OS8_SUBUAWCWEB;
      } else if (normalizedUserAgent.contains("Android")) {
         String androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent, false);
         String androidDeviceId = null;
         if (StringUtils.isNotEmpty(androidVersion)) {
            String[] versionParts = androidVersion.split("\\.");
            if (versionParts.length > 0) {
               androidDeviceId = "generic_ucweb_android_ver" + versionParts[0];
            }
         }

         return SUPPORTED_DEVICE_IDS.contains(androidDeviceId) ? androidDeviceId : GENERIC_UCWEB_ANDROID_VER1;
      } else if (normalizedUserAgent.contains("iPhone")) {
         Matcher iphoneVersionMatcher = IPHONE_IOS_VERSION.matcher(normalizedUserAgent);
         String iphoneDeviceId = null;
         if (iphoneVersionMatcher.find() && iphoneVersionMatcher.groupCount() > 0) {
            iphoneDeviceId = "apple_iphone_ver" + iphoneVersionMatcher.group(1) + "_subuaucweb";
         }

         return SUPPORTED_DEVICE_IDS.contains(iphoneDeviceId) ? iphoneDeviceId : APPLE_IPHONE_VER1_SUBUAWCWEB;
      } else if (normalizedUserAgent.contains("iPad")) {
         Matcher ipadVersionMatcher = IPAD_IOS_VERSION.matcher(normalizedUserAgent);
         String ipadDeviceId = null;
         if (ipadVersionMatcher.find() && ipadVersionMatcher.groupCount() > 0) {
            ipadDeviceId = "apple_ipad_ver1_sub" + ipadVersionMatcher.group(1) + "_subuaucweb";
         }

         return SUPPORTED_DEVICE_IDS.contains(ipadDeviceId) ? ipadDeviceId : APPLE_IPAD_VER1_SUBUAWCWEB;
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
      (SUPPORTED_DEVICE_IDS = new ArrayList<>()).add("generic_ucweb");
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
