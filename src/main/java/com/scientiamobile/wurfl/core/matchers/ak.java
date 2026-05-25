package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ak extends a {
   public ak(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add("nintendo_wii_u_ver1");
      var1.add("nintendo_wii_ver1");
      var1.add("nintendo_dsi_ver1");
      var1.add("nintendo_ds_ver1");
      var1.add("nintendo_3ds_ver1");
      var1.add("nintendo_new3ds_ver1");
      var1.add("nintendo_switch_ver1");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      if (var1._internalIsDesktopBrowser()) {
         return false;
      } else if (var2.contains("Nintendo")) {
         return true;
      } else {
         return var2.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(var2, "Nitro", "Opera");
      }
   }

   protected final String a(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getNormalizedDeviceUserAgent()).contains("New Nintendo 3DS")) {
         return "nintendo_new3ds_ver1";
      } else if (var2.contains("Nintendo 3DS")) {
         return "nintendo_3ds_ver1";
      } else if (var2.contains("Nintendo WiiU")) {
         return "nintendo_wii_u_ver1";
      } else if (var2.contains("Nintendo Wii")) {
         return "nintendo_wii_ver1";
      } else if (var2.contains("Nintendo DSi")) {
         return "nintendo_dsi_ver1";
      } else if (var2.contains("Nintendo Switch")) {
         return "nintendo_switch_ver1";
      } else {
         return var2.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(var2, "Nitro", "Opera") ? "nintendo_ds_ver1" : "nintendo_wii_ver1";
      }
   }

   public final String getMatcherName() {
      return "NintendoMatcher";
   }

   public final String getBucketMatcherName() {
      return "Nintendo";
   }
}
