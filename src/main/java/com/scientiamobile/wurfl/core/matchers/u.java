package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class AndroidMatcher extends AbstractMatcher {
   private static String b = "generic_android";
   private static String c = "generic_android_ver2_2";
   private static String d = "generic_android_ver1_5_tablet";
   private static final Set e = new HashSet();
   private static final Set f = new HashSet();

   public AndroidMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(e);
      var1.addAll(f);
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "like Android", "Symbian") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Android", "android");
   }

   protected final String a(String var1) {
      int var2;
      if ((var2 = var1.indexOf("---")) >= 0) {
         var2 += 3;
         return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
      } else if (!StringMatchUtils.startsWithAnyOf(var1, "Mozilla", "Dalvik")) {
         return null;
      } else {
         String var3;
         if ((var3 = UserAgentUtils.getAndroidModel(var1)) != null && var3.length() != 0) {
            int var5 = Math.min(StringMatchUtils.indexOfOrLength(var1, " Build/"), StringMatchUtils.indexOfOrLength(var1, " AppleWebKit"));
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var5);
         } else {
            int var4 = var1.length();
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var4);
         }
      }
   }

   protected final String b(WURFLRequest var1) {
      String var3;
      String var2 = UserAgentUtils.getAndroidVersion(var3 = var1.getNormalizedDeviceUserAgent(), true).replaceAll("\\.", "_");
      if ((var2 = "generic_android_ver" + var2).endsWith("2_0") || var2.endsWith("4_0")) {
         var2 = var2.substring(0, var2.length() - 2);
      }

      if (!var2.startsWith("generic_android_ver3_") && !var3.contains("Mobile") && var3.contains("Safari")) {
         var2 = var2.concat("_tablet");
         return f.contains(var2) ? var2 : d;
      } else {
         return e.contains(var2) ? var2 : b;
      }
   }

   public final String getMatcherName() {
      return "AndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "Android";
   }

   static {
      e.add("generic_android_ver1_5");
      e.add("generic_android_ver1_6");
      e.add("generic_android_ver2");
      e.add("generic_android_ver2_1");
      e.add(c);
      e.add("generic_android_ver2_3");
      e.add("generic_android_ver3_0");
      e.add("generic_android_ver3_1");
      e.add("generic_android_ver3_2");
      e.add("generic_android_ver3_3");
      e.add("generic_android_ver4");
      e.add("generic_android_ver4_1");
      e.add("generic_android_ver4_2");
      e.add("generic_android_ver4_3");
      e.add("generic_android_ver4_4");
      e.add("generic_android_ver4_5");
      e.add("generic_android_ver5_0");
      e.add("generic_android_ver5_1");
      e.add("generic_android_ver5_2");
      e.add("generic_android_ver5_3");
      e.add("generic_android_ver6_0");
      e.add("generic_android_ver6_1");
      e.add("generic_android_ver7_0");
      e.add("generic_android_ver7_1");
      e.add("generic_android_ver7_2");
      e.add("generic_android_ver8_0");
      e.add("generic_android_ver8_1");
      e.add("generic_android_ver9_0");
      f.add(d);
      f.add("generic_android_ver1_6_tablet");
      f.add("generic_android_ver2_tablet");
      f.add("generic_android_ver2_1_tablet");
      f.add("generic_android_ver2_2_tablet");
      f.add("generic_android_ver2_3_tablet");
      f.add("generic_android_ver4_tablet");
      f.add("generic_android_ver4_1_tablet");
      f.add("generic_android_ver4_2_tablet");
      f.add("generic_android_ver4_3_tablet");
      f.add("generic_android_ver4_4_tablet");
      f.add("generic_android_ver4_5_tablet");
      f.add("generic_android_ver5_0_tablet");
      f.add("generic_android_ver5_1_tablet");
      f.add("generic_android_ver5_2_tablet");
      f.add("generic_android_ver5_3_tablet");
      f.add("generic_android_ver6_0_tablet");
      f.add("generic_android_ver6_1_tablet");
      f.add("generic_android_ver7_0_tablet");
      f.add("generic_android_ver7_1_tablet");
      f.add("generic_android_ver7_2_tablet");
      f.add("generic_android_ver8_0_tablet");
      f.add("generic_android_ver8_1_tablet");
      f.add("generic_android_ver9_0_tablet");
   }
}
