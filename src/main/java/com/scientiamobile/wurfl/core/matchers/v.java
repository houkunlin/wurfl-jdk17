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
import org.apache.commons.lang3.StringUtils;

final class AppleMatcher extends AbstractMatcher {
   private static String b = "apple_iphone_coremedia_ver1";
   private static final String c = "apple_iphone_ver".concat("1");
   private static final String[] d = new String[]{"iPhone", "iPod", "iPad"};
   private static final Pattern e = Pattern.compile(" (\\d+)_\\d+[ _]");
   private static final Pattern f = Pattern.compile("(?:iPhone|iPad|iPod) ?(\\d+,\\d+)");
   private static final List<String> g = new ArrayList<>();
   private static final Map<String, String> h = new HashMap<>();
   private static final Map<String, String> i = new HashMap<>();
   private static final Map<String, String> j = new HashMap<>();
   private static final List<String> k = new ArrayList<>();

   public AppleMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(g);
      var1.addAll(k);
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), d) && !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Symbian", "Nintendo");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      String var4 = var1.getNormalizedDeviceUserAgent();
      String var2 = null;
      Matcher var3;
      if ((var3 = f.matcher(var4)).find()) {
         String var8 = var3.group(1);
         if (var4.contains("iPod")) {
            var2 = (String)j.get(var8);
         } else if (var4.contains("iPad")) {
            var2 = (String)i.get(var8);
         } else if (var4.contains("iPhone")) {
            var2 = (String)h.get(var8);
         }
      }

      int var9;
      if ((var9 = StringMatchUtils.firstChar(var4, '_')) < 0) {
         if ((var9 = var4.indexOf("like Mac OS X;")) >= 0) {
            var9 += 14;
         } else {
            var9 = var4.length();
         }
      } else {
         ++var9;
      }

      if ((var4 = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var4, var9)) != null) {
         var4 = this.getFilter().getIndex().getDeviceIdByUserAgent(var4);
         if (var2 != null && var4 != null) {
            var2 = var4 + "_subhw" + var2;
            if (k.contains(var2)) {
               return var2;
            }
         }

         return var4;
      } else {
         return null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var4 = var1.getNormalizedDeviceUserAgent();
      Matcher var2 = e.matcher(var4);
      String var3 = "-1";
      if (var2.find()) {
         var3 = var2.group(1);
      }

      if (var4.contains("CoreMedia")) {
         return b;
      } else if (var4.contains("iPod")) {
         var4 = "apple_ipod_touch_ver".concat(var3);
         return g.contains(var4) ? var4 : "apple_ipod_touch_ver".concat("1");
      } else if (var4.contains("iPad")) {
         if ("3".equals(var3)) {
            return "apple_ipad_ver1".concat("_subua32");
         } else if ("4".equals(var3)) {
            return "apple_ipad_ver1".concat("_sub42");
         } else {
            var4 = "apple_ipad_ver1".concat("_sub").concat(var3);
            return g.contains(var4) ? var4 : "apple_ipad_ver1";
         }
      } else {
         var4 = "apple_iphone_ver".concat(var3);
         return g.contains(var4) ? var4 : c;
      }
   }

   public final String getMatcherName() {
      return "AppleMatcher";
   }

   public final String getBucketMatcherName() {
      return "Apple";
   }

   static {
      g.add("apple_ipod_touch_ver1");
      g.add("apple_ipod_touch_ver2");
      g.add("apple_ipod_touch_ver3");
      g.add("apple_ipod_touch_ver4");
      g.add("apple_ipod_touch_ver5");
      g.add("apple_ipod_touch_ver6");
      g.add("apple_ipod_touch_ver7");
      g.add("apple_ipod_touch_ver8");
      g.add("apple_ipod_touch_ver9");
      g.add("apple_ipod_touch_ver10");
      g.add("apple_ipod_touch_ver11");
      g.add("apple_ipad_ver1");
      g.add("apple_ipad_ver1_subua32");
      g.add("apple_ipad_ver1_sub42");
      g.add("apple_ipad_ver1_sub5");
      g.add("apple_ipad_ver1_sub6");
      g.add("apple_ipad_ver1_sub7");
      g.add("apple_ipad_ver1_sub8");
      g.add("apple_ipad_ver1_sub9");
      g.add("apple_ipad_ver1_sub10");
      g.add("apple_ipad_ver1_sub11");
      g.add(c);
      g.add("apple_iphone_ver2");
      g.add("apple_iphone_ver3");
      g.add("apple_iphone_ver4");
      g.add("apple_iphone_ver5");
      g.add("apple_iphone_ver6");
      g.add("apple_iphone_ver7");
      g.add("apple_iphone_ver8");
      g.add("apple_iphone_ver9");
      g.add("apple_iphone_ver10");
      g.add("apple_iphone_ver11");
      h.put("1,1", "2g");
      h.put("1,2", "3g");
      h.put("2,1", "3gs");
      h.put("3,1", "4");
      h.put("3,2", "4");
      h.put("3,3", "4");
      h.put("4,1", "4s");
      h.put("5,1", "5");
      h.put("5,2", "5");
      h.put("5,3", "5c");
      h.put("5,4", "5c");
      h.put("6,1", "5s");
      h.put("6,2", "5s");
      h.put("7,1", "6plus");
      h.put("7,2", "6");
      h.put("8,2", "6splus");
      h.put("8,1", "6s");
      h.put("8,4", "se");
      h.put("9,1", "7");
      h.put("9,2", "7plus");
      h.put("9,3", "7");
      h.put("9,4", "7plus");
      h.put("10,1", "8");
      h.put("10,2", "8plus");
      h.put("10,3", "x");
      h.put("10,4", "8");
      h.put("10,5", "8plus");
      h.put("10,6", "x");
      i.put("1,1", "1");
      i.put("2,1", "2");
      i.put("2,2", "2");
      i.put("2,3", "2");
      i.put("2,4", "2");
      i.put("2,5", "mini1");
      i.put("2,6", "mini1");
      i.put("2,7", "mini1");
      i.put("3,1", "3");
      i.put("3,2", "3");
      i.put("3,3", "3");
      i.put("3,4", "4");
      i.put("3,5", "4");
      i.put("3,6", "4");
      i.put("4,1", "air");
      i.put("4,2", "air");
      i.put("4,3", "air");
      i.put("4,4", "mini2");
      i.put("4,5", "mini2");
      i.put("4,6", "mini2");
      i.put("4,7", "mini3");
      i.put("4,8", "mini3");
      i.put("4,9", "mini3");
      i.put("5,3", "air2");
      i.put("5,4", "air2");
      i.put("5,1", "mini4");
      i.put("5,2", "mini4");
      i.put("6,7", "pro");
      i.put("6,8", "pro");
      i.put("6,3", "pro97");
      i.put("6,4", "pro97");
      i.put("6,11", "5");
      i.put("6,12", "5");
      i.put("7,1", "pro2");
      i.put("7,2", "pro2");
      i.put("7,3", "pro2105");
      i.put("7,4", "pro2105");
      j.put("1,1", "1");
      j.put("2,1", "2");
      j.put("3,1", "3");
      j.put("4,1", "4");
      j.put("5,1", "5");
      j.put("7,1", "6");
      k.add("apple_ipad_ver1_subhw1");
      k.add("apple_ipad_ver1_sub42_subhw1");
      k.add("apple_ipad_ver1_sub43_subhw1");
      k.add("apple_ipad_ver1_sub43_subhw2");
      k.add("apple_ipad_ver1_sub5_subhw1");
      k.add("apple_ipad_ver1_sub5_subhw2");
      k.add("apple_ipad_ver1_sub51_subhw1");
      k.add("apple_ipad_ver1_sub51_subhw2");
      k.add("apple_ipad_ver1_sub51_subhw3");
      k.add("apple_ipad_ver1_sub6_subhw2");
      k.add("apple_ipad_ver1_sub6_subhw3");
      k.add("apple_ipad_ver1_sub6_subhw4");
      k.add("apple_ipad_ver1_sub6_subhwmini1");
      k.add("apple_ipad_ver1_sub61_subhw2");
      k.add("apple_ipad_ver1_sub61_subhw3");
      k.add("apple_ipad_ver1_sub61_subhw4");
      k.add("apple_ipad_ver1_sub61_subhwmini1");
      k.add("apple_ipad_ver1_sub7_subhw2");
      k.add("apple_ipad_ver1_sub7_subhw3");
      k.add("apple_ipad_ver1_sub7_subhw4");
      k.add("apple_ipad_ver1_sub7_subhwmini1");
      k.add("apple_ipad_ver1_sub7_subhwmini2");
      k.add("apple_ipad_ver1_sub7_subhwair");
      k.add("apple_ipad_ver1_sub71_subhw2");
      k.add("apple_ipad_ver1_sub71_subhw3");
      k.add("apple_ipad_ver1_sub71_subhw4");
      k.add("apple_ipad_ver1_sub71_subhwmini1");
      k.add("apple_ipad_ver1_sub71_subhwmini2");
      k.add("apple_ipad_ver1_sub71_subhwair");
      k.add("apple_ipad_ver1_sub8_subhw2");
      k.add("apple_ipad_ver1_sub8_subhw3");
      k.add("apple_ipad_ver1_sub8_subhw4");
      k.add("apple_ipad_ver1_sub8_subhwmini1");
      k.add("apple_ipad_ver1_sub8_subhwmini2");
      k.add("apple_ipad_ver1_sub8_subhwair");
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
