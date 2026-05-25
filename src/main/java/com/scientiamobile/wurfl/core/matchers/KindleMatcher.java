package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class KindleMatcher extends MatcherBase {
   private static String GENERIC_AMAZON_KINDLE = "generic_amazon_kindle";
   private static final Map DEVICE_BY_TOKEN;

   public KindleMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(GENERIC_AMAZON_KINDLE);
      var1.addAll(DEVICE_BY_TOKEN.values());
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return (var2 = var1.getCleanedDeviceUserAgent()).contains("Android") && StringMatchUtils.containsAnyOf(var2, "/Kindle", "Silk") ? false : StringMatchUtils.containsAnyOf(var2, "Kindle", "Silk");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = var1.indexOf("Build/")) != -1) {
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
      } else {
         if ((var2 = var1.indexOf("Kindle/")) >= 0) {
            var2 += 7;
            char var3;
            if ((var3 = var1.charAt(var2)) >= '1' && var3 <= '3') {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 1);
            }
         }

         return (var2 = var1.indexOf("PlayStation Vita")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 16 + 1) : null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var4 = var1.getNormalizedDeviceUserAgent();

      for(Map.Entry var3 : DEVICE_BY_TOKEN.entrySet()) {
         if (var4.contains((CharSequence)var3.getKey())) {
            return (String)var3.getValue();
         }
      }

      return GENERIC_AMAZON_KINDLE;
   }

   public final String getMatcherName() {
      return "KindleMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kindle";
   }

   static {
      (DEVICE_BY_TOKEN = new LinkedHashMap()).put("Kindle/1", "amazon_kindle_ver1");
      DEVICE_BY_TOKEN.put("Kindle/2", "amazon_kindle2_ver1");
      DEVICE_BY_TOKEN.put("Kindle/3", "amazon_kindle3_ver1");
      DEVICE_BY_TOKEN.put("Kindle Fire", "amazon_kindle_fire_ver1");
      DEVICE_BY_TOKEN.put("Silk", "amazon_kindle_fire_ver1");
   }
}
