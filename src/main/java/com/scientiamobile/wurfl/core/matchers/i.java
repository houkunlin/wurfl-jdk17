package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

final class i extends a {
   private static String b = "generic_smarttv_googletv_browser";
   private static String c = "generic_smarttv_appletv_browser";
   private static String d = "generic_smarttv_boxeebox_browser";
   private static String e = "generic_smarttv_chromecast";
   private static String f = "generic_tizen_smarttv_3_0";
   private static String g = "generic_tizen_smarttv_2_4";
   private static String h = "generic_tizen_smarttv_2_3";
   private static String i = "generic_tizen_smarttv";

   public i(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add("generic_smarttv_browser");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1._internalIsSmartTvBrowser();
   }

   protected final String a(WURFLRequest var1) {
      return null;
   }

   protected final String b(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("Tizen 3.0")) {
         return f;
      } else if (var2.contains("Tizen 2.4")) {
         return g;
      } else if (var2.contains("Tizen 2.3")) {
         return h;
      } else if (var2.contains("Tizen")) {
         return i;
      } else if (var2.contains("SmartTV")) {
         return "generic_smarttv_browser";
      } else if (var2.contains("GoogleTV")) {
         return b;
      } else if (var2.contains("AppleTV")) {
         return c;
      } else if (var2.contains("Boxee")) {
         return d;
      } else {
         return var2.contains("CrKey") ? e : "generic_smarttv_browser";
      }
   }

   public final String getMatcherName() {
      return "SmartTvMatcher";
   }

   public final String getBucketMatcherName() {
      return "SmartTV";
   }
}
