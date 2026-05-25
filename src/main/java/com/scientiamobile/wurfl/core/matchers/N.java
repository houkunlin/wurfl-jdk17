package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class N extends a {
   private static String b = "generic_amazon_kindle";
   private static final Map c;

   public N(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.addAll(c.values());
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return (var2 = var1.getCleanedDeviceUserAgent()).contains("Android") && StringMatchUtils.containsAnyOf(var2, "/Kindle", "Silk") ? false : StringMatchUtils.containsAnyOf(var2, "Kindle", "Silk");
   }

   protected final String a(String var1) {
      int var2;
      if ((var2 = var1.indexOf("Build/")) != -1) {
         return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
      } else {
         if ((var2 = var1.indexOf("Kindle/")) >= 0) {
            var2 += 7;
            char var3;
            if ((var3 = var1.charAt(var2)) >= '1' && var3 <= '3') {
               return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 1);
            }
         }

         return (var2 = var1.indexOf("PlayStation Vita")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 16 + 1) : null;
      }
   }

   protected final String b(WURFLRequest var1) {
      String var4 = var1.getNormalizedDeviceUserAgent();

      for(Map.Entry var3 : c.entrySet()) {
         if (var4.contains((CharSequence)var3.getKey())) {
            return (String)var3.getValue();
         }
      }

      return b;
   }

   public final String getMatcherName() {
      return "KindleMatcher";
   }

   public final String getBucketMatcherName() {
      return "Kindle";
   }

   static {
      (c = new LinkedHashMap()).put("Kindle/1", "amazon_kindle_ver1");
      c.put("Kindle/2", "amazon_kindle2_ver1");
      c.put("Kindle/3", "amazon_kindle3_ver1");
      c.put("Kindle Fire", "amazon_kindle_fire_ver1");
      c.put("Silk", "amazon_kindle_fire_ver1");
   }
}
