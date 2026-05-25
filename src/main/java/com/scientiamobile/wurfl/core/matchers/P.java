package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class P extends a {
   private static String b = "generic_lguplus";
   private static final Map c;

   public P(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(c.keySet());
      var1.add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "lgtelecom", "LGUPLUS");
   }

   protected final String a(WURFLRequest var1) {
      return null;
   }

   protected final String b(WURFLRequest var1) {
      for(Map.Entry var3 : c.entrySet()) {
         if (StringMatchUtils.containsAllOf(var1.getNormalizedDeviceUserAgent(), (String[])var3.getValue())) {
            return (String)var3.getKey();
         }
      }

      return b;
   }

   public final String getMatcherName() {
      return "LGUPLUSMatcher";
   }

   public final String getBucketMatcherName() {
      return "LGUPLUS";
   }

   static {
      (c = new LinkedHashMap()).put("generic_lguplus_rexos_facebook_browser", new String[]{"Windows NT 5", "POLARIS"});
      c.put("generic_lguplus_rexos_webviewer_browser", new String[]{"Windows NT 5"});
      c.put("generic_lguplus_winmo_facebook_browser", new String[]{"Windows CE", "POLARIS"});
      c.put("generic_lguplus_android_webkit_browser", new String[]{"Android", "AppleWebKit"});
   }
}
