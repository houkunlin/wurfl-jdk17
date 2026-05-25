package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

final class LGUPLUSMatcher extends a {
   private static String GENERIC_LGUPLUS = "generic_lguplus";
   private static final Map DEVICE_BY_TOKENS;

   public LGUPLUSMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(DEVICE_BY_TOKENS.keySet());
      var1.add(GENERIC_LGUPLUS);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "lgtelecom", "LGUPLUS");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      for(Map.Entry var3 : DEVICE_BY_TOKENS.entrySet()) {
         if (StringMatchUtils.containsAllOf(var1.getNormalizedDeviceUserAgent(), (String[])var3.getValue())) {
            return (String)var3.getKey();
         }
      }

      return GENERIC_LGUPLUS;
   }

   public final String getMatcherName() {
      return "LGUPLUSMatcher";
   }

   public final String getBucketMatcherName() {
      return "LGUPLUS";
   }

   static {
      (DEVICE_BY_TOKENS = new LinkedHashMap()).put("generic_lguplus_rexos_facebook_browser", new String[]{"Windows NT 5", "POLARIS"});
      DEVICE_BY_TOKENS.put("generic_lguplus_rexos_webviewer_browser", new String[]{"Windows NT 5"});
      DEVICE_BY_TOKENS.put("generic_lguplus_winmo_facebook_browser", new String[]{"Windows CE", "POLARIS"});
      DEVICE_BY_TOKENS.put("generic_lguplus_android_webkit_browser", new String[]{"Android", "AppleWebKit"});
   }
}

