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

final class m extends a {
   private static String b = "generic_ms_phone_os8_subuaucweb";
   private static String c = "generic_ucweb_android_ver1";
   private static String d = "apple_iphone_ver1_subuaucweb";
   private static String e = "apple_ipad_ver1_subuaucweb";
   private static final Pattern f = Pattern.compile("iPhone OS (\\d+)(?:_\\d+)?.+ like");
   private static final Pattern g = Pattern.compile("CPU OS (\\d+)(?:_\\d+)?.+like Mac");
   private static List h;

   public m(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(h);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2.startsWith("Mozilla") && var2.contains("UCBrowser");
   }

   protected final String a(String var1) {
      if (UserAgentUtils.getUcBrowserVersion(var1, false) == null) {
         return null;
      } else {
         if (var1.contains("Windows Phone")) {
            String var2 = UserAgentUtils.getWindowsPhoneVersion(var1);
            if (UserAgentUtils.getWindowsPhoneModel(var1) != null && var2 != null) {
               return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var1.indexOf("---") + 3);
            }
         } else if (var1.contains("Android")) {
            String var4 = UserAgentUtils.getAndroidModel(var1);
            String var3 = UserAgentUtils.getAndroidVersion(var1, false);
            if (var4 != null && var3 != null) {
               return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var1.indexOf("---") + 3);
            }
         } else if (var1.contains("iPhone;")) {
            if (UcwebU3Normalizer.IPHONE.matcher(var1).find()) {
               return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var1.indexOf("---") + 3);
            }
         } else if (var1.contains("iPad") && UcwebU3Normalizer.IPAD.matcher(var1).find()) {
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var1.indexOf("---") + 3);
         }

         return null;
      }
   }

   protected final String b(WURFLRequest var1) {
      String var2;
      String var5;
      String var10000;
      if (!(var2 = var5 = var1.getNormalizedDeviceUserAgent()).contains("Windows Phone")) {
         var10000 = null;
      } else {
         String var3 = UserAgentUtils.getWindowsPhoneVersion(var2);
         String var4 = null;
         if (StringUtils.isNotEmpty(var3)) {
            if (((Object[])(var2 = var3.split("\\."))).length >= 2) {
               var3 = ((Object[])var2)[0];
               if (StringUtils.isEmpty(var2 = ((Object[])var2)[1])) {
                  var4 = "generic_ms_phone_os" + var3 + "_subuaucweb";
               } else {
                  var4 = "generic_ms_phone_os" + var3 + "_" + var2 + "_subuaucweb";
               }
            }
         } else {
            a.debug("user agent " + var2 + "has no version information");
         }

         var10000 = h.contains(var4) ? var4 : b;
      }

      var2 = var10000;
      if (StringUtils.isNotEmpty(var10000)) {
         return var2;
      } else {
         if (!var5.contains("Android")) {
            var10000 = null;
         } else {
            String var16 = UserAgentUtils.getAndroidVersion(var5, false);
            String var19 = null;
            if (StringUtils.isNotEmpty(var16) && ((Object[])(var2 = var16.split("\\."))).length > 0) {
               var19 = "generic_ucweb_android_ver" + ((Object[])var2)[0];
            }

            var10000 = h.contains(var19) ? var19 : c;
         }

         var2 = var10000;
         if (StringUtils.isNotEmpty(var10000)) {
            return var2;
         } else {
            if (!var5.contains("iPhone")) {
               var10000 = null;
            } else {
               Matcher var17 = f.matcher(var5);
               String var20 = null;
               if (var17.find() && var17.groupCount() > 0) {
                  var2 = var17.group(1);
                  var20 = "apple_iphone_ver" + var2 + "_subuaucweb";
               }

               var10000 = h.contains(var20) ? var20 : d;
            }

            var2 = var10000;
            if (StringUtils.isNotEmpty(var10000)) {
               return var2;
            } else {
               if (!var5.contains("iPad")) {
                  var10000 = null;
               } else {
                  Matcher var18 = g.matcher(var5);
                  String var21 = null;
                  if (var18.find() && var18.groupCount() > 0) {
                     var2 = var18.group(1);
                     var21 = "apple_ipad_ver1_sub" + var2 + "_subuaucweb";
                  }

                  var10000 = h.contains(var21) ? var21 : e;
               }

               var2 = var10000;
               return StringUtils.isNotEmpty(var10000) ? var2 : "generic_ucweb";
            }
         }
      }
   }

   public final String getMatcherName() {
      return "UcwebU3Matcher";
   }

   public final String getBucketMatcherName() {
      return "UcwebU3";
   }

   static {
      (h = new ArrayList()).add("generic_ucweb");
      h.add(c);
      h.add("generic_ucweb_android_ver2");
      h.add("generic_ucweb_android_ver3");
      h.add("generic_ucweb_android_ver4");
      h.add("generic_ucweb_android_ver5");
      h.add("generic_ucweb_android_ver6");
      h.add("generic_ucweb_android_ver7");
      h.add("generic_ucweb_android_ver8");
      h.add("generic_ucweb_android_ver9");
      h.add(d);
      h.add("apple_iphone_ver2_subuaucweb");
      h.add("apple_iphone_ver3_subuaucweb");
      h.add("apple_iphone_ver4_subuaucweb");
      h.add("apple_iphone_ver5_subuaucweb");
      h.add("apple_iphone_ver6_subuaucweb");
      h.add("apple_iphone_ver7_subuaucweb");
      h.add("apple_iphone_ver8_subuaucweb");
      h.add("apple_iphone_ver9_subuaucweb");
      h.add("apple_iphone_ver10_subuaucweb");
      h.add("apple_iphone_ver11_subuaucweb");
      h.add(e);
      h.add("apple_ipad_ver1_sub4_subuaucweb");
      h.add("apple_ipad_ver1_sub5_subuaucweb");
      h.add("apple_ipad_ver1_sub6_subuaucweb");
      h.add("apple_ipad_ver1_sub7_subuaucweb");
      h.add("apple_ipad_ver1_sub8_subuaucweb");
      h.add("apple_ipad_ver1_sub9_subuaucweb");
      h.add("apple_ipad_ver1_sub10_subuaucweb");
      h.add("apple_ipad_ver1_sub11_subuaucweb");
      h.add(b);
      h.add("generic_ms_phone_os8_1_subuaucweb");
      h.add("generic_ms_phone_os10_subuaucweb");
   }
}
