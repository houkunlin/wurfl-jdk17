package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class aq extends a {
   private static String b = "generic_android_ver2_0_opera_mobi";
   private static String c = "generic_android_ver2_1_opera_tablet";
   private static Set d;

   public aq(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      return new HashSet(d);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Android") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Opera Tablet", "Opera Mobi");
   }

   protected final String a(String var1) {
      int var2;
      var2 = (var2 = var1.indexOf("---")) == -1 ? var1.length() : var2 + 3;
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      String var3;
      boolean var2 = (var3 = var1.getNormalizedDeviceUserAgent()).contains("Opera Tablet");
      String var4 = UserAgentUtils.getAndroidVersion(var3, true);
      var4 = "generic_android_ver" + var4.replaceAll("\\.", "_") + "_opera_" + (var2 ? "tablet" : "mobi");
      if (d.contains(var4)) {
         return var4;
      } else {
         return var2 ? c : b;
      }
   }

   public final String getMatcherName() {
      return "OperaMobiOrTabletOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMobiOrTabletOnAndroid";
   }

   static {
      (d = new HashSet()).add("generic_android_ver1_5_opera_mobi");
      d.add("generic_android_ver1_6_opera_mobi");
      d.add(b);
      d.add("generic_android_ver2_1_opera_mobi");
      d.add("generic_android_ver2_2_opera_mobi");
      d.add("generic_android_ver2_3_opera_mobi");
      d.add("generic_android_ver4_0_opera_mobi");
      d.add("generic_android_ver4_1_opera_mobi");
      d.add("generic_android_ver4_2_opera_mobi");
      d.add(c);
      d.add("generic_android_ver2_2_opera_tablet");
      d.add("generic_android_ver2_3_opera_tablet");
      d.add("generic_android_ver3_0_opera_tablet");
      d.add("generic_android_ver3_1_opera_tablet");
      d.add("generic_android_ver3_2_opera_tablet");
      d.add("generic_android_ver4_0_opera_tablet");
      d.add("generic_android_ver4_1_opera_tablet");
      d.add("generic_android_ver4_2_opera_tablet");
   }
}
