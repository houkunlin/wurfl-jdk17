package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class SmartTvMatcher extends MatcherBase {
   private static String GOOGLE_TV_BROWSER = "generic_smarttv_googletv_browser";
   private static String APPLE_TV_BROWSER = "generic_smarttv_appletv_browser";
   private static String BOXEEBOX_BROWSER = "generic_smarttv_boxeebox_browser";
   private static String CHROMECAST = "generic_smarttv_chromecast";
   private static String TIZEN_3_0 = "generic_tizen_smarttv_3_0";
   private static String TIZEN_2_4 = "generic_tizen_smarttv_2_4";
   private static String TIZEN_2_3 = "generic_tizen_smarttv_2_3";
   private static String TIZEN_GENERIC = "generic_tizen_smarttv";

   public SmartTvMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("generic_smarttv_browser");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1._internalIsSmartTvBrowser();
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("Tizen 3.0")) {
         return TIZEN_3_0;
      } else if (var2.contains("Tizen 2.4")) {
         return TIZEN_2_4;
      } else if (var2.contains("Tizen 2.3")) {
         return TIZEN_2_3;
      } else if (var2.contains("Tizen")) {
         return TIZEN_GENERIC;
      } else if (var2.contains("SmartTV")) {
         return "generic_smarttv_browser";
      } else if (var2.contains("GoogleTV")) {
         return GOOGLE_TV_BROWSER;
      } else if (var2.contains("AppleTV")) {
         return APPLE_TV_BROWSER;
      } else if (var2.contains("Boxee")) {
         return BOXEEBOX_BROWSER;
      } else {
         return var2.contains("CrKey") ? CHROMECAST : "generic_smarttv_browser";
      }
   }

   public final String getMatcherName() {
      return "SmartTvMatcher";
   }

   public final String getBucketMatcherName() {
      return "SmartTV";
   }
}
