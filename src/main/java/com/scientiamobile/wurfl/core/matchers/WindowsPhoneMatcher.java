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
   private static final String GENERIC_MS_PHONE_OS7 = "generic_ms_phone_os7";
   private static final String GENERIC_MS_PHONE_OS7_DESKTOPMODE = "generic_ms_phone_os7_desktopmode";
   private static final String GENERIC_MS_PHONE_OS7_5_DESKTOPMODE = "generic_ms_phone_os7_5_desktopmode";
   private static final String GENERIC_MS_PHONE_OS8_DESKTOPMODE = "generic_ms_phone_os8_desktopmode";
   private static final String GENERIC_MS_PHONE_OS10_DESKTOPMODE = "generic_ms_phone_os10_desktopmode";
   private static final Map VERSION_TO_DEVICE_ID;

   public WindowsPhoneMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(VERSION_TO_DEVICE_ID.values());
      var1.add(GENERIC_MS_PHONE_OS7_DESKTOPMODE);
      var1.add(GENERIC_MS_PHONE_OS7_5_DESKTOPMODE);
      var1.add(GENERIC_MS_PHONE_OS8_DESKTOPMODE);
      var1.add(GENERIC_MS_PHONE_OS10_DESKTOPMODE);
      var1.add("generic");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && (StringMatchUtils.containsAnyOf(var2, "WPDesktop", "ZuneWP7") || StringMatchUtils.containsAllOf(var2, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") || StringMatchUtils.containsAnyOf(var2, "Windows Phone", "WindowsPhone", "NativeHost"));
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      String var2;
      boolean var3;
      if (!(var3 = (var2 = var1.getNormalizedDeviceUserAgent()).contains("---")) || !StringMatchUtils.containsAnyOf(var2, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(var2, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/") && !UserAgentUtils.isWindowsPhoneAdClient(var1.getCleanedDeviceUserAgent())) {
         return !var3 && var2.contains("NativeHost") ? GENERIC_MS_PHONE_OS7 : super.applyConclusiveMatch(var1);
      } else {
         return super.applyConclusiveMatch(var1);
      }
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = var1.indexOf("---")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 3) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var4;
      boolean var2 = StringMatchUtils.containsAnyOf(var4 = var1.getCleanedDeviceUserAgent(), "WPDesktop", "ZuneWP7");
      boolean var3 = false;
      if (!var2) {
         var3 = StringMatchUtils.containsAllOf(var4, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/");
      }

      if (!var2 && !var3) {
         String var5 = UserAgentUtils.getWindowsPhoneVersion(var4);
         if ((var5 = (String)VERSION_TO_DEVICE_ID.get(var5)) != null) {
            return var5;
         } else {
            return UserAgentUtils.isWindowsPhoneAdClient(var4) ? GENERIC_MS_PHONE_OS7 : "generic";
         }
      } else if (var3) {
         return GENERIC_MS_PHONE_OS10_DESKTOPMODE;
      } else if (var4.contains("WPDesktop")) {
         return GENERIC_MS_PHONE_OS8_DESKTOPMODE;
      } else {
         return var4.contains("Trident/5.0") ? GENERIC_MS_PHONE_OS7_5_DESKTOPMODE : GENERIC_MS_PHONE_OS7_DESKTOPMODE;
      }
   }

   public final String getMatcherName() {
      return "WindowsPhoneMatcher";
   }

   public final String getBucketMatcherName() {
      return "WindowsPhone";
   }

   static {
      (VERSION_TO_DEVICE_ID = new HashMap()).put("10.0", "generic_ms_phone_os10");
      VERSION_TO_DEVICE_ID.put("8.1", "generic_ms_phone_os8_1");
      VERSION_TO_DEVICE_ID.put("8.0", "generic_ms_phone_os8");
      VERSION_TO_DEVICE_ID.put("7.8", "generic_ms_phone_os7_8");
      VERSION_TO_DEVICE_ID.put("7.5", "generic_ms_phone_os7_5");
      VERSION_TO_DEVICE_ID.put("7.0", GENERIC_MS_PHONE_OS7);
      VERSION_TO_DEVICE_ID.put("6.5", "generic_ms_winmo6_5");
   }
}
