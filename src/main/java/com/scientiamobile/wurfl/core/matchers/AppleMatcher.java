package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class AppleMatcher extends AbstractMatcher {
   private static final String COREMEDIA_DEVICE_ID = "apple_iphone_coremedia_ver1";
   private static final String DEFAULT_IPHONE_DEVICE_ID = "apple_iphone_ver".concat("1");
   private static final String[] APPLE_DEVICE_KEYWORDS = new String[]{"iPhone", "iPod", "iPad"};
   private static final Pattern IOS_MAJOR_VERSION_PATTERN = Pattern.compile(" (\\d+)_\\d+[ _]");
   private static final Pattern APPLE_HARDWARE_ID_PATTERN = Pattern.compile("(?:iPhone|iPad|iPod) ?(\\d+,\\d+)");
   private static final List<String> SUPPORTED_DEVICE_IDS = new ArrayList<>();
   private static final Map<String, String> IPHONE_HW_TO_SUBHW = new HashMap<>();
   private static final Map<String, String> IPAD_HW_TO_SUBHW = new HashMap<>();
   private static final Map<String, String> IPOD_HW_TO_SUBHW = new HashMap<>();
   private static final List<String> SUPPORTED_SUBHW_DEVICE_IDS = new ArrayList<>();
   private static final List<String> k = SUPPORTED_SUBHW_DEVICE_IDS;

   public AppleMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(SUPPORTED_DEVICE_IDS);
      var1.addAll(SUPPORTED_SUBHW_DEVICE_IDS);
      var1.add(COREMEDIA_DEVICE_ID);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), APPLE_DEVICE_KEYWORDS) && !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Symbian", "Nintendo");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      String userAgent = var1.getNormalizedDeviceUserAgent();
      String subHw = null;
      Matcher hwMatcher;
      if ((hwMatcher = APPLE_HARDWARE_ID_PATTERN.matcher(userAgent)).find()) {
         String hwId = hwMatcher.group(1);
         if (userAgent.contains("iPod")) {
            subHw = IPOD_HW_TO_SUBHW.get(hwId);
         } else if (userAgent.contains("iPad")) {
            subHw = IPAD_HW_TO_SUBHW.get(hwId);
         } else if (userAgent.contains("iPhone")) {
            subHw = IPHONE_HW_TO_SUBHW.get(hwId);
         }
      }

      int var9;
      if ((var9 = StringMatchUtils.firstChar(userAgent, '_')) < 0) {
         if ((var9 = userAgent.indexOf("like Mac OS X;")) >= 0) {
            var9 += 14;
         } else {
            var9 = userAgent.length();
         }
      } else {
         ++var9;
      }

      String matchedUserAgent = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, var9);
      if (matchedUserAgent != null) {
         String matchedDeviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent);
         if (subHw != null && matchedDeviceId != null) {
            String subHwDeviceId = matchedDeviceId + "_subhw" + subHw;
            if (SUPPORTED_SUBHW_DEVICE_IDS.contains(subHwDeviceId)) {
               return subHwDeviceId;
            }
         }

         return matchedDeviceId;
      } else {
         return null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String userAgent = var1.getNormalizedDeviceUserAgent();
      Matcher versionMatcher = IOS_MAJOR_VERSION_PATTERN.matcher(userAgent);
      String majorVersion = "-1";
      if (versionMatcher.find()) {
         majorVersion = versionMatcher.group(1);
      }

      if (userAgent.contains("CoreMedia")) {
         return COREMEDIA_DEVICE_ID;
      } else if (userAgent.contains("iPod")) {
         userAgent = "apple_ipod_touch_ver".concat(majorVersion);
         return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : "apple_ipod_touch_ver".concat("1");
      } else if (userAgent.contains("iPad")) {
         if ("3".equals(majorVersion)) {
            return "apple_ipad_ver1".concat("_subua32");
         } else if ("4".equals(majorVersion)) {
            return "apple_ipad_ver1".concat("_sub42");
         } else {
            userAgent = "apple_ipad_ver1".concat("_sub").concat(majorVersion);
            return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : "apple_ipad_ver1";
         }
      } else {
         userAgent = "apple_iphone_ver".concat(majorVersion);
         return SUPPORTED_DEVICE_IDS.contains(userAgent) ? userAgent : DEFAULT_IPHONE_DEVICE_ID;
      }
   }

   public final String getMatcherName() {
      return "AppleMatcher";
   }

   public final String getBucketMatcherName() {
      return "Apple";
   }

   static {
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver1");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver2");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver3");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver4");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver5");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver6");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver7");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver8");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver9");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver10");
      SUPPORTED_DEVICE_IDS.add("apple_ipod_touch_ver11");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_subua32");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub42");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub5");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub6");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub7");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub8");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub9");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub10");
      SUPPORTED_DEVICE_IDS.add("apple_ipad_ver1_sub11");
      SUPPORTED_DEVICE_IDS.add(DEFAULT_IPHONE_DEVICE_ID);
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver2");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver3");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver4");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver5");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver6");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver7");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver8");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver9");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver10");
      SUPPORTED_DEVICE_IDS.add("apple_iphone_ver11");
      IPHONE_HW_TO_SUBHW.put("1,1", "2g");
      IPHONE_HW_TO_SUBHW.put("1,2", "3g");
      IPHONE_HW_TO_SUBHW.put("2,1", "3gs");
      IPHONE_HW_TO_SUBHW.put("3,1", "4");
      IPHONE_HW_TO_SUBHW.put("3,2", "4");
      IPHONE_HW_TO_SUBHW.put("3,3", "4");
      IPHONE_HW_TO_SUBHW.put("4,1", "4s");
      IPHONE_HW_TO_SUBHW.put("5,1", "5");
      IPHONE_HW_TO_SUBHW.put("5,2", "5");
      IPHONE_HW_TO_SUBHW.put("5,3", "5c");
      IPHONE_HW_TO_SUBHW.put("5,4", "5c");
      IPHONE_HW_TO_SUBHW.put("6,1", "5s");
      IPHONE_HW_TO_SUBHW.put("6,2", "5s");
      IPHONE_HW_TO_SUBHW.put("7,1", "6plus");
      IPHONE_HW_TO_SUBHW.put("7,2", "6");
      IPHONE_HW_TO_SUBHW.put("8,2", "6splus");
      IPHONE_HW_TO_SUBHW.put("8,1", "6s");
      IPHONE_HW_TO_SUBHW.put("8,4", "se");
      IPHONE_HW_TO_SUBHW.put("9,1", "7");
      IPHONE_HW_TO_SUBHW.put("9,2", "7plus");
      IPHONE_HW_TO_SUBHW.put("9,3", "7");
      IPHONE_HW_TO_SUBHW.put("9,4", "7plus");
      IPHONE_HW_TO_SUBHW.put("10,1", "8");
      IPHONE_HW_TO_SUBHW.put("10,2", "8plus");
      IPHONE_HW_TO_SUBHW.put("10,3", "x");
      IPHONE_HW_TO_SUBHW.put("10,4", "8");
      IPHONE_HW_TO_SUBHW.put("10,5", "8plus");
      IPHONE_HW_TO_SUBHW.put("10,6", "x");
      IPAD_HW_TO_SUBHW.put("1,1", "1");
      IPAD_HW_TO_SUBHW.put("2,1", "2");
      IPAD_HW_TO_SUBHW.put("2,2", "2");
      IPAD_HW_TO_SUBHW.put("2,3", "2");
      IPAD_HW_TO_SUBHW.put("2,4", "2");
      IPAD_HW_TO_SUBHW.put("2,5", "mini1");
      IPAD_HW_TO_SUBHW.put("2,6", "mini1");
      IPAD_HW_TO_SUBHW.put("2,7", "mini1");
      IPAD_HW_TO_SUBHW.put("3,1", "3");
      IPAD_HW_TO_SUBHW.put("3,2", "3");
      IPAD_HW_TO_SUBHW.put("3,3", "3");
      IPAD_HW_TO_SUBHW.put("3,4", "4");
      IPAD_HW_TO_SUBHW.put("3,5", "4");
      IPAD_HW_TO_SUBHW.put("3,6", "4");
      IPAD_HW_TO_SUBHW.put("4,1", "air");
      IPAD_HW_TO_SUBHW.put("4,2", "air");
      IPAD_HW_TO_SUBHW.put("4,3", "air");
      IPAD_HW_TO_SUBHW.put("4,4", "mini2");
      IPAD_HW_TO_SUBHW.put("4,5", "mini2");
      IPAD_HW_TO_SUBHW.put("4,6", "mini2");
      IPAD_HW_TO_SUBHW.put("4,7", "mini3");
      IPAD_HW_TO_SUBHW.put("4,8", "mini3");
      IPAD_HW_TO_SUBHW.put("4,9", "mini3");
      IPAD_HW_TO_SUBHW.put("5,3", "air2");
      IPAD_HW_TO_SUBHW.put("5,4", "air2");
      IPAD_HW_TO_SUBHW.put("5,1", "mini4");
      IPAD_HW_TO_SUBHW.put("5,2", "mini4");
      IPAD_HW_TO_SUBHW.put("6,7", "pro");
      IPAD_HW_TO_SUBHW.put("6,8", "pro");
      IPAD_HW_TO_SUBHW.put("6,3", "pro97");
      IPAD_HW_TO_SUBHW.put("6,4", "pro97");
      IPAD_HW_TO_SUBHW.put("6,11", "5");
      IPAD_HW_TO_SUBHW.put("6,12", "5");
      IPAD_HW_TO_SUBHW.put("7,1", "pro2");
      IPAD_HW_TO_SUBHW.put("7,2", "pro2");
      IPAD_HW_TO_SUBHW.put("7,3", "pro2105");
      IPAD_HW_TO_SUBHW.put("7,4", "pro2105");
      IPOD_HW_TO_SUBHW.put("1,1", "1");
      IPOD_HW_TO_SUBHW.put("2,1", "2");
      IPOD_HW_TO_SUBHW.put("3,1", "3");
      IPOD_HW_TO_SUBHW.put("4,1", "4");
      IPOD_HW_TO_SUBHW.put("5,1", "5");
      IPOD_HW_TO_SUBHW.put("7,1", "6");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_subhw1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub42_subhw1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub43_subhw1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub43_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub5_subhw1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub5_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub51_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhw4");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub6_subhwmini1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhw4");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub61_subhwmini1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhw4");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwmini1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwmini2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub7_subhwair");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhw4");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwmini1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwmini2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub71_subhwair");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw3");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhw4");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwmini1");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwmini2");
      SUPPORTED_SUBHW_DEVICE_IDS.add("apple_ipad_ver1_sub8_subhwair");
      k.add("apple_ipad_ver1_sub8_1_subhw2");
      k.add("apple_ipad_ver1_sub8_1_subhw3");
      k.add("apple_ipad_ver1_sub8_1_subhw4");
      k.add("apple_ipad_ver1_sub8_1_subhwair");
      k.add("apple_ipad_ver1_sub8_1_subhwair2");
      k.add("apple_ipad_ver1_sub8_1_subhwmini1");
      k.add("apple_ipad_ver1_sub8_1_subhwmini2");
      k.add("apple_ipad_ver1_sub8_1_subhwmini3");
      k.add("apple_ipad_ver1_sub8_2_subhw2");
      k.add("apple_ipad_ver1_sub8_2_subhw3");
      k.add("apple_ipad_ver1_sub8_2_subhw4");
      k.add("apple_ipad_ver1_sub8_2_subhwair");
      k.add("apple_ipad_ver1_sub8_2_subhwair2");
      k.add("apple_ipad_ver1_sub8_2_subhwmini1");
      k.add("apple_ipad_ver1_sub8_2_subhwmini2");
      k.add("apple_ipad_ver1_sub8_2_subhwmini3");
      k.add("apple_ipad_ver1_sub8_3_subhw2");
      k.add("apple_ipad_ver1_sub8_3_subhw3");
      k.add("apple_ipad_ver1_sub8_3_subhw4");
      k.add("apple_ipad_ver1_sub8_3_subhwair");
      k.add("apple_ipad_ver1_sub8_3_subhwair2");
      k.add("apple_ipad_ver1_sub8_3_subhwmini1");
      k.add("apple_ipad_ver1_sub8_3_subhwmini2");
      k.add("apple_ipad_ver1_sub8_3_subhwmini3");
      k.add("apple_ipad_ver1_sub8_4_subhw2");
      k.add("apple_ipad_ver1_sub8_4_subhw3");
      k.add("apple_ipad_ver1_sub8_4_subhw4");
      k.add("apple_ipad_ver1_sub8_4_subhwair");
      k.add("apple_ipad_ver1_sub8_4_subhwair2");
      k.add("apple_ipad_ver1_sub8_4_subhwmini1");
      k.add("apple_ipad_ver1_sub8_4_subhwmini2");
      k.add("apple_ipad_ver1_sub8_4_subhwmini3");
      k.add("apple_ipad_ver1_sub9_subhw2");
      k.add("apple_ipad_ver1_sub9_subhw3");
      k.add("apple_ipad_ver1_sub9_subhw4");
      k.add("apple_ipad_ver1_sub9_subhwair");
      k.add("apple_ipad_ver1_sub9_subhwair2");
      k.add("apple_ipad_ver1_sub9_subhwmini1");
      k.add("apple_ipad_ver1_sub9_subhwmini2");
      k.add("apple_ipad_ver1_sub9_subhwmini3");
      k.add("apple_ipad_ver1_sub9_subhwmini4");
      k.add("apple_ipad_ver1_sub9_1_subhw2");
      k.add("apple_ipad_ver1_sub9_1_subhw3");
      k.add("apple_ipad_ver1_sub9_1_subhw4");
      k.add("apple_ipad_ver1_sub9_1_subhwair");
      k.add("apple_ipad_ver1_sub9_1_subhwair2");
      k.add("apple_ipad_ver1_sub9_1_subhwmini1");
      k.add("apple_ipad_ver1_sub9_1_subhwmini2");
      k.add("apple_ipad_ver1_sub9_1_subhwmini3");
      k.add("apple_ipad_ver1_sub9_1_subhwmini4");
      k.add("apple_ipad_ver1_sub9_1_subhwpro");
      k.add("apple_ipad_ver1_sub9_2_subhw2");
      k.add("apple_ipad_ver1_sub9_2_subhw3");
      k.add("apple_ipad_ver1_sub9_2_subhw4");
      k.add("apple_ipad_ver1_sub9_2_subhwair");
      k.add("apple_ipad_ver1_sub9_2_subhwair2");
      k.add("apple_ipad_ver1_sub9_2_subhwmini1");
      k.add("apple_ipad_ver1_sub9_2_subhwmini2");
      k.add("apple_ipad_ver1_sub9_2_subhwmini3");
      k.add("apple_ipad_ver1_sub9_2_subhwmini4");
      k.add("apple_ipad_ver1_sub9_2_subhwpro");
      k.add("apple_ipad_ver1_sub9_3_subhw2");
      k.add("apple_ipad_ver1_sub9_3_subhw3");
      k.add("apple_ipad_ver1_sub9_3_subhw4");
      k.add("apple_ipad_ver1_sub9_3_subhwair");
      k.add("apple_ipad_ver1_sub9_3_subhwair2");
      k.add("apple_ipad_ver1_sub9_3_subhwmini1");
      k.add("apple_ipad_ver1_sub9_3_subhwmini2");
      k.add("apple_ipad_ver1_sub9_3_subhwmini3");
      k.add("apple_ipad_ver1_sub9_3_subhwmini4");
      k.add("apple_ipad_ver1_sub9_3_subhwpro");
      k.add("apple_ipad_ver1_sub9_3_subhwpro97");
      k.add("apple_ipad_ver1_sub10_subhw4");
      k.add("apple_ipad_ver1_sub10_subhwair");
      k.add("apple_ipad_ver1_sub10_subhwair2");
      k.add("apple_ipad_ver1_sub10_subhwmini2");
      k.add("apple_ipad_ver1_sub10_subhwmini3");
      k.add("apple_ipad_ver1_sub10_subhwmini4");
      k.add("apple_ipad_ver1_sub10_subhwpro");
      k.add("apple_ipad_ver1_sub10_subhwpro97");
      k.add("apple_ipad_ver1_sub11_1_subhwmini2");
      k.add("apple_ipad_ver1_sub11_1_subhwmini3");
      k.add("apple_ipad_ver1_sub11_1_subhwmini4");
      k.add("apple_ipad_ver1_sub11_1_subhw5");
      k.add("apple_ipad_ver1_sub11_1_subhwair");
      k.add("apple_ipad_ver1_sub11_1_subhwair2");
      k.add("apple_ipad_ver1_sub11_1_subhwpro");
      k.add("apple_ipad_ver1_sub11_1_subhwpro97");
      k.add("apple_ipad_ver1_sub11_1_subhwpro2");
      k.add("apple_ipad_ver1_sub11_1_subhwpro2105");
      k.add("apple_iphone_ver1_subhw2g");
      k.add("apple_iphone_ver2_subhw2g");
      k.add("apple_iphone_ver2_subhw3g");
      k.add("apple_iphone_ver2_1_subhw2g");
      k.add("apple_iphone_ver2_1_subhw3g");
      k.add("apple_iphone_ver2_2_subhw2g");
      k.add("apple_iphone_ver2_2_subhw3g");
      k.add("apple_iphone_ver3_subhw2g");
      k.add("apple_iphone_ver3_subhw3g");
      k.add("apple_iphone_ver3_subhw3gs");
      k.add("apple_iphone_ver3_1_subhw3g");
      k.add("apple_iphone_ver3_1_subhw3gs");
      k.add("apple_iphone_ver4_subhw3g");
      k.add("apple_iphone_ver4_subhw3gs");
      k.add("apple_iphone_ver4_subhw4");
      k.add("apple_iphone_ver4_1_subhw3g");
      k.add("apple_iphone_ver4_1_subhw3gs");
      k.add("apple_iphone_ver4_1_subhw4");
      k.add("apple_iphone_ver4_2_subhw3g");
      k.add("apple_iphone_ver4_2_subhw3gs");
      k.add("apple_iphone_ver4_2_subhw4");
      k.add("apple_iphone_ver4_3_subhw3gs");
      k.add("apple_iphone_ver4_3_subhw4");
      k.add("apple_iphone_ver5_subhw3gs");
      k.add("apple_iphone_ver5_subhw4");
      k.add("apple_iphone_ver5_subhw4s");
      k.add("apple_iphone_ver5_1_subhw3gs");
      k.add("apple_iphone_ver5_1_subhw4");
      k.add("apple_iphone_ver5_1_subhw4s");
      k.add("apple_iphone_ver6_subhw3gs");
      k.add("apple_iphone_ver6_subhw4");
      k.add("apple_iphone_ver6_subhw4s");
      k.add("apple_iphone_ver6_subhw5");
      k.add("apple_iphone_ver6_1_subhw3gs");
      k.add("apple_iphone_ver6_1_subhw4");
      k.add("apple_iphone_ver6_1_subhw4s");
      k.add("apple_iphone_ver6_1_subhw5");
      k.add("apple_iphone_ver7_subhw4");
      k.add("apple_iphone_ver7_subhw4s");
      k.add("apple_iphone_ver7_subhw5");
      k.add("apple_iphone_ver7_subhw5c");
      k.add("apple_iphone_ver7_subhw5s");
      k.add("apple_iphone_ver7_1_subhw4");
      k.add("apple_iphone_ver7_1_subhw4s");
      k.add("apple_iphone_ver7_1_subhw5");
      k.add("apple_iphone_ver7_1_subhw5c");
      k.add("apple_iphone_ver7_1_subhw5s");
      k.add("apple_iphone_ver8_subhw4s");
      k.add("apple_iphone_ver8_subhw5");
      k.add("apple_iphone_ver8_subhw5c");
      k.add("apple_iphone_ver8_subhw5s");
      k.add("apple_iphone_ver8_subhw6");
      k.add("apple_iphone_ver8_subhw6plus");
      k.add("apple_iphone_ver8_subua802_subhw4s");
      k.add("apple_iphone_ver8_subua802_subhw5");
      k.add("apple_iphone_ver8_subua802_subhw5c");
      k.add("apple_iphone_ver8_subua802_subhw5s");
      k.add("apple_iphone_ver8_subua802_subhw6");
      k.add("apple_iphone_ver8_subua802_subhw6plus");
      k.add("apple_iphone_ver8_1_subhw4s");
      k.add("apple_iphone_ver8_1_subhw5");
      k.add("apple_iphone_ver8_1_subhw5c");
      k.add("apple_iphone_ver8_1_subhw5s");
      k.add("apple_iphone_ver8_1_subhw6");
      k.add("apple_iphone_ver8_1_subhw6plus");
      k.add("apple_iphone_ver8_2_subhw4s");
      k.add("apple_iphone_ver8_2_subhw5");
      k.add("apple_iphone_ver8_2_subhw5c");
      k.add("apple_iphone_ver8_2_subhw5s");
      k.add("apple_iphone_ver8_2_subhw6");
      k.add("apple_iphone_ver8_2_subhw6plus");
      k.add("apple_iphone_ver8_3_subhw4s");
      k.add("apple_iphone_ver8_3_subhw5");
      k.add("apple_iphone_ver8_3_subhw5c");
      k.add("apple_iphone_ver8_3_subhw5s");
      k.add("apple_iphone_ver8_3_subhw6");
      k.add("apple_iphone_ver8_3_subhw6plus");
      k.add("apple_iphone_ver8_4_subhw4s");
      k.add("apple_iphone_ver8_4_subhw5");
      k.add("apple_iphone_ver8_4_subhw5c");
      k.add("apple_iphone_ver8_4_subhw5s");
      k.add("apple_iphone_ver8_4_subhw6");
      k.add("apple_iphone_ver8_4_subhw6plus");
      k.add("apple_iphone_ver9_subhw4s");
      k.add("apple_iphone_ver9_subhw5");
      k.add("apple_iphone_ver9_subhw5c");
      k.add("apple_iphone_ver9_subhw5s");
      k.add("apple_iphone_ver9_subhw6");
      k.add("apple_iphone_ver9_subhw6plus");
      k.add("apple_iphone_ver9_subhw6s");
      k.add("apple_iphone_ver9_subhw6splus");
      k.add("apple_iphone_ver9_1_subhw4s");
      k.add("apple_iphone_ver9_1_subhw5");
      k.add("apple_iphone_ver9_1_subhw5c");
      k.add("apple_iphone_ver9_1_subhw5s");
      k.add("apple_iphone_ver9_1_subhw6");
      k.add("apple_iphone_ver9_1_subhw6plus");
      k.add("apple_iphone_ver9_1_subhw6s");
      k.add("apple_iphone_ver9_1_subhw6splus");
      k.add("apple_iphone_ver9_2_subhw4s");
      k.add("apple_iphone_ver9_2_subhw5");
      k.add("apple_iphone_ver9_2_subhw5c");
      k.add("apple_iphone_ver9_2_subhw5s");
      k.add("apple_iphone_ver9_2_subhw6");
      k.add("apple_iphone_ver9_2_subhw6plus");
      k.add("apple_iphone_ver9_2_subhw6s");
      k.add("apple_iphone_ver9_2_subhw6splus");
      k.add("apple_iphone_ver9_3_subhw4s");
      k.add("apple_iphone_ver9_3_subhw5");
      k.add("apple_iphone_ver9_3_subhw5s");
      k.add("apple_iphone_ver9_3_subhw5c");
      k.add("apple_iphone_ver9_3_subhw6");
      k.add("apple_iphone_ver9_3_subhw6plus");
      k.add("apple_iphone_ver9_3_subhw6s");
      k.add("apple_iphone_ver9_3_subhw6splus");
      k.add("apple_iphone_ver9_3_subhwse");
      k.add("apple_iphone_ver10_subhw5");
      k.add("apple_iphone_ver10_subhw5c");
      k.add("apple_iphone_ver10_subhw5s");
      k.add("apple_iphone_ver10_subhw6");
      k.add("apple_iphone_ver10_subhw6plus");
      k.add("apple_iphone_ver10_subhw6s");
      k.add("apple_iphone_ver10_subhw6splus");
      k.add("apple_iphone_ver10_subhwse");
      k.add("apple_iphone_ver10_subhw7");
      k.add("apple_iphone_ver10_subhw7plus");
      k.add("apple_iphone_ver10_1_subhw5");
      k.add("apple_iphone_ver10_1_subhw5c");
      k.add("apple_iphone_ver10_1_subhw5s");
      k.add("apple_iphone_ver10_1_subhw6");
      k.add("apple_iphone_ver10_1_subhw6plus");
      k.add("apple_iphone_ver10_1_subhw6s");
      k.add("apple_iphone_ver10_1_subhw6splus");
      k.add("apple_iphone_ver10_1_subhwse");
      k.add("apple_iphone_ver10_1_subhw7");
      k.add("apple_iphone_ver10_1_subhw7plus");
      k.add("apple_iphone_ver10_2_subhw5");
      k.add("apple_iphone_ver10_2_subhw5c");
      k.add("apple_iphone_ver10_2_subhw5s");
      k.add("apple_iphone_ver10_2_subhw6");
      k.add("apple_iphone_ver10_2_subhw6plus");
      k.add("apple_iphone_ver10_2_subhw6s");
      k.add("apple_iphone_ver10_2_subhw6splus");
      k.add("apple_iphone_ver10_2_subhwse");
      k.add("apple_iphone_ver10_2_subhw7");
      k.add("apple_iphone_ver10_2_subhw7plus");
      k.add("apple_iphone_ver10_3_subhw5");
      k.add("apple_iphone_ver10_3_subhw5c");
      k.add("apple_iphone_ver10_3_subhw5s");
      k.add("apple_iphone_ver10_3_subhwse");
      k.add("apple_iphone_ver10_3_subhw6");
      k.add("apple_iphone_ver10_3_subhw6plus");
      k.add("apple_iphone_ver10_3_subhw6s");
      k.add("apple_iphone_ver10_3_subhw6splus");
      k.add("apple_iphone_ver10_3_subhw7");
      k.add("apple_iphone_ver10_3_subhw7plus");
      k.add("apple_iphone_ver11_subhw5s");
      k.add("apple_iphone_ver11_subhwse");
      k.add("apple_iphone_ver11_subhw6");
      k.add("apple_iphone_ver11_subhw6plus");
      k.add("apple_iphone_ver11_subhw6s");
      k.add("apple_iphone_ver11_subhw6splus");
      k.add("apple_iphone_ver11_subhw7");
      k.add("apple_iphone_ver11_subhw7plus");
      k.add("apple_iphone_ver11_subhw8");
      k.add("apple_iphone_ver11_subhw8plus");
      k.add("apple_iphone_ver11_subhwx");
      k.add("apple_iphone_ver11_1_subhw5s");
      k.add("apple_iphone_ver11_1_subhwse");
      k.add("apple_iphone_ver11_1_subhw6");
      k.add("apple_iphone_ver11_1_subhw6plus");
      k.add("apple_iphone_ver11_1_subhw6s");
      k.add("apple_iphone_ver11_1_subhw6splus");
      k.add("apple_iphone_ver11_1_subhw7");
      k.add("apple_iphone_ver11_1_subhw7plus");
      k.add("apple_iphone_ver11_1_subhw8");
      k.add("apple_iphone_ver11_1_subhw8plus");
      k.add("apple_iphone_ver11_1_subhwx");
      k.add("apple_ipad_ver1_sub10_1_subhw4");
      k.add("apple_ipad_ver1_sub10_1_subhwair");
      k.add("apple_ipad_ver1_sub10_1_subhwair2");
      k.add("apple_ipad_ver1_sub10_1_subhwmini2");
      k.add("apple_ipad_ver1_sub10_1_subhwmini3");
      k.add("apple_ipad_ver1_sub10_1_subhwmini4");
      k.add("apple_ipad_ver1_sub10_1_subhwpro");
      k.add("apple_ipad_ver1_sub10_1_subhwpro97");
      k.add("apple_ipad_ver1_sub10_2_subhw4");
      k.add("apple_ipad_ver1_sub10_2_subhwair");
      k.add("apple_ipad_ver1_sub10_2_subhwair2");
      k.add("apple_ipad_ver1_sub10_2_subhwmini2");
      k.add("apple_ipad_ver1_sub10_2_subhwmini3");
      k.add("apple_ipad_ver1_sub10_2_subhwmini4");
      k.add("apple_ipad_ver1_sub10_2_subhwpro");
      k.add("apple_ipad_ver1_sub10_2_subhwpro97");
      k.add("apple_ipad_ver1_sub10_3_subhwmini2");
      k.add("apple_ipad_ver1_sub10_3_subhwmini3");
      k.add("apple_ipad_ver1_sub10_3_subhwmini4");
      k.add("apple_ipad_ver1_sub10_3_subhw4");
      k.add("apple_ipad_ver1_sub10_3_subhwair");
      k.add("apple_ipad_ver1_sub10_3_subhwair2");
      k.add("apple_ipad_ver1_sub10_3_subhwpro97");
      k.add("apple_ipad_ver1_sub10_3_subhwpro");
      k.add("apple_ipad_ver1_sub10_3_subhw5");
      k.add("apple_ipad_ver1_sub10_3_subhwpro2");
      k.add("apple_ipad_ver1_sub10_3_subhwpro2105");
      k.add("apple_ipad_ver1_sub11_subhwmini2");
      k.add("apple_ipad_ver1_sub11_subhwmini3");
      k.add("apple_ipad_ver1_sub11_subhwmini4");
      k.add("apple_ipad_ver1_sub11_subhw5");
      k.add("apple_ipad_ver1_sub11_subhwair");
      k.add("apple_ipad_ver1_sub11_subhwair2");
      k.add("apple_ipad_ver1_sub11_subhwpro");
      k.add("apple_ipad_ver1_sub11_subhwpro97");
      k.add("apple_ipad_ver1_sub11_subhwpro2");
      k.add("apple_ipad_ver1_sub11_subhwpro2105");
      k.add("apple_ipod_touch_ver1_subhw1");
      k.add("apple_ipod_touch_ver2_subhw1");
      k.add("apple_ipod_touch_ver2_1_subhw1");
      k.add("apple_ipod_touch_ver2_1_subhw2");
      k.add("apple_ipod_touch_ver2_2_subhw1");
      k.add("apple_ipod_touch_ver2_2_subhw2");
      k.add("apple_ipod_touch_ver3_subhw1");
      k.add("apple_ipod_touch_ver3_subhw2");
      k.add("apple_ipod_touch_ver3_1_subhw1");
      k.add("apple_ipod_touch_ver3_1_subhw2");
      k.add("apple_ipod_touch_ver3_1_subhw3");
      k.add("apple_ipod_touch_ver4_subhw2");
      k.add("apple_ipod_touch_ver4_subhw3");
      k.add("apple_ipod_touch_ver4_1_subhw2");
      k.add("apple_ipod_touch_ver4_1_subhw3");
      k.add("apple_ipod_touch_ver4_1_subhw4");
      k.add("apple_ipod_touch_ver4_2_subhw2");
      k.add("apple_ipod_touch_ver4_2_subhw3");
      k.add("apple_ipod_touch_ver4_2_subhw4");
      k.add("apple_ipod_touch_ver4_3_subhw3");
      k.add("apple_ipod_touch_ver4_3_subhw4");
      k.add("apple_ipod_touch_ver5_subhw3");
      k.add("apple_ipod_touch_ver5_subhw4");
      k.add("apple_ipod_touch_ver5_1_subhw3");
      k.add("apple_ipod_touch_ver5_1_subhw4");
      k.add("apple_ipod_touch_ver6_subhw3");
      k.add("apple_ipod_touch_ver6_subhw4");
      k.add("apple_ipod_touch_ver6_subhw5");
      k.add("apple_ipod_touch_ver6_1_subhw4");
      k.add("apple_ipod_touch_ver6_1_subhw5");
      k.add("apple_ipod_touch_ver7_subhw5");
      k.add("apple_ipod_touch_ver7_1_subhw5");
      k.add("apple_ipod_touch_ver8_subhw5");
      k.add("apple_ipod_touch_ver8_1_subhw5");
      k.add("apple_ipod_touch_ver8_2_subhw5");
      k.add("apple_ipod_touch_ver8_3_subhw5");
      k.add("apple_ipod_touch_ver8_4_subhw5");
      k.add("apple_ipod_touch_ver9_subhw5");
      k.add("apple_ipod_touch_ver9_subhw6");
      k.add("apple_ipod_touch_ver9_1_subhw5");
      k.add("apple_ipod_touch_ver9_1_subhw6");
      k.add("apple_ipod_touch_ver9_2_subhw5");
      k.add("apple_ipod_touch_ver9_2_subhw6");
      k.add("apple_ipod_touch_ver9_3_subhw5");
      k.add("apple_ipod_touch_ver9_3_subhw6");
      k.add("apple_ipod_touch_ver10_subhw6");
      k.add("apple_ipod_touch_ver10_1_subhw6");
      k.add("apple_ipod_touch_ver10_2_subhw6");
      k.add("apple_ipod_touch_ver10_3_subhw6");
      k.add("apple_ipod_touch_ver11_subhw6");
      k.add("apple_ipod_touch_ver11_1_subhw6");
   }
}
