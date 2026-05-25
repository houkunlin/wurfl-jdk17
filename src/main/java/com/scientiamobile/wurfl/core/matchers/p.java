package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class WindowsPhoneMatcher extends AbstractMatcher {
   private static String b = "generic_ms_phone_os7";
   private static String c = "generic_ms_phone_os7_desktopmode";
   private static String d = "generic_ms_phone_os7_5_desktopmode";
   private static String e = "generic_ms_phone_os8_desktopmode";
   private static String f = "generic_ms_phone_os10_desktopmode";
   private static final Map g;

   public WindowsPhoneMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(g.values());
      var1.add(c);
      var1.add(d);
      var1.add(e);
      var1.add(f);
      var1.add("generic");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && (StringMatchUtils.containsAnyOf(var2, "WPDesktop", "ZuneWP7") || StringMatchUtils.containsAllOf(var2, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") || StringMatchUtils.containsAnyOf(var2, "Windows Phone", "WindowsPhone", "NativeHost"));
   }

   protected final String a(WURFLRequest var1) {
      String var2;
      boolean var3;
      if (!(var3 = (var2 = var1.getNormalizedDeviceUserAgent()).contains("---")) || !StringMatchUtils.containsAnyOf(var2, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(var2, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") && !UserAgentUtils.isWindowsPhoneAdClient(var1.getCleanedDeviceUserAgent())) {
         return !var3 && var2.contains("NativeHost") ? b : super.a(var1);
      } else {
         return super.a(var1);
      }
   }

   protected final String a(String var1) {
      int var2;
      return (var2 = var1.indexOf("---")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 3) : null;
   }

   protected final String b(WURFLRequest var1) {
      String var4;
      boolean var2 = StringMatchUtils.containsAnyOf(var4 = var1.getCleanedDeviceUserAgent(), "WPDesktop", "ZuneWP7");
      boolean var3 = false;
      if (!var2) {
         var3 = StringMatchUtils.containsAllOf(var4, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/");
      }

      if (!var2 && !var3) {
         String var5 = UserAgentUtils.getWindowsPhoneVersion(var4);
         if ((var5 = (String)g.get(var5)) != null) {
            return var5;
         } else {
            return UserAgentUtils.isWindowsPhoneAdClient(var4) ? b : "generic";
         }
      } else if (var3) {
         return f;
      } else if (var4.contains("WPDesktop")) {
         return e;
      } else {
         return var4.contains("Trident/5.0") ? d : c;
      }
   }

   public final String getMatcherName() {
      return "WindowsPhoneMatcher";
   }

   public final String getBucketMatcherName() {
      return "WindowsPhone";
   }

   static {
      (g = new HashMap()).put("10.0", "generic_ms_phone_os10");
      g.put("8.1", "generic_ms_phone_os8_1");
      g.put("8.0", "generic_ms_phone_os8");
      g.put("7.8", "generic_ms_phone_os7_8");
      g.put("7.5", "generic_ms_phone_os7_5");
      g.put("7.0", b);
      g.put("6.5", "generic_ms_winmo6_5");
   }
}
