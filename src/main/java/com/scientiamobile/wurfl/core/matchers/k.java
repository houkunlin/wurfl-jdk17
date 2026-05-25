package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class k extends a {
   private static String b = "generic_android_ver2_0_ucweb";
   private static Map c;

   public k(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(c.values());
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(var2, "Android", "UCWEB7");
   }

   protected final String a(String var1) {
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, StringMatchUtils.indexOfOrLength(var1, "UCWEB7"));
   }

   protected final String b(WURFLRequest var1) {
      String var2 = UserAgentUtils.getAndroidVersion(var1.getNormalizedDeviceUserAgent(), true);
      String var3;
      return (var3 = (String)c.get(var2)) != null ? var3 : b;
   }

   public final String getMatcherName() {
      return "UCWEB7OnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "Ucweb7OnAndroid";
   }

   static {
      (c = new HashMap()).put("1.6", "generic_android_ver1_6_ucweb");
      c.put("2.1", "generic_android_ver2_1_ucweb");
      c.put("2.2", "generic_android_ver2_2_ucweb");
      c.put("2.3", "generic_android_ver2_3_ucweb");
   }
}
