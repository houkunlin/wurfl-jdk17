package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.ArrayUtils;

final class E extends a {
   private static final Pattern b = Pattern.compile("^.+?\\(.+?rv:\\d+(\\.)");
   private static String c = "generic_android_ver2_0_fennec";
   private static String d = "generic_android_ver2_0_fennec_tablet";
   private static String e = "generic_android_ver2_0_fennec_desktop";
   private Set f = this.a();

   public E(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      if (this.f != null) {
         return this.f;
      } else {
         HashSet var1;
         (var1 = new HashSet()).add("generic");
         var1.add(c);
         var1.add(d);
         var1.add(e);
         var1.add("generic_android_ver4_fennec");
         var1.add("generic_android_ver4_fennec_tablet");
         var1.add("generic_android_ver4_fennec_desktop");
         var1.add("generic_android_ver5_0_fennec");
         var1.add("generic_android_ver5_0_fennec_tablet");
         var1.add("generic_android_ver5_0_fennec_desktop");
         var1.add("generic_android_ver6_0_fennec");
         var1.add("generic_android_ver6_0_fennec_tablet");
         var1.add("generic_android_ver6_0_fennec_desktop");
         var1.add("generic_android_ver7_0_fennec");
         var1.add("generic_android_ver7_0_fennec_tablet");
         var1.add("generic_android_ver7_0_fennec_desktop");
         var1.add("generic_android_ver8_0_fennec");
         var1.add("generic_android_ver8_0_fennec_tablet");
         var1.add("generic_android_ver8_0_fennec_desktop");
         var1.add("generic_android_ver9_0_fennec");
         var1.add("generic_android_ver9_0_fennec_tablet");
         var1.add("generic_android_ver9_0_fennec_desktop");
         return var1;
      }
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Android") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Fennec", "Firefox");
   }

   protected final String a(String var1) {
      Matcher var2;
      int var3;
      return (var2 = b.matcher(var1)).find() && (var3 = var2.end()) < var1.length() ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var3) : null;
   }

   protected final String b(WURFLRequest var1) {
      String var2 = null;
      int var3 = 0;
      String var4;
      String var5;
      if ((var4 = UserAgentUtils.getAndroidVersion(var5 = var1.getNormalizedDeviceUserAgent(), false)) != null) {
         var3 = ArrayUtils.isNotEmpty(var2 = var4.split("\\.")) ? Integer.parseInt(((Object[])var2)[0]) : 0;
         var2 = "generic_android_ver" + var3 + "_0_fennec";
      }

      if (var3 < 5) {
         var2 = "generic_android_ver4_fennec";
      }

      if (StringMatchUtils.containsAllOf(var5, "Firefox", "Tablet")) {
         var2 = var2 + "_tablet";
      } else if (StringMatchUtils.containsAllOf(var5, "Firefox", "Desktop")) {
         var2 = var2 + "_desktop";
      }

      return this.f.contains(var2) ? var2 : "generic_android_ver4_fennec";
   }

   public final String getMatcherName() {
      return "FennecOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "FennecOnAndroid";
   }
}
