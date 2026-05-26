package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class OperaMiniOnAndroidMatcher extends MatcherBase {
   private static final String GENERIC_OPERA_MINI_ANDROID_VERSION5 = "generic_opera_mini_android_version5";
   private static final String[] OPERA_MINI_ANDROID_PREFIXES = new String[]{"Opera/9.80 (J2ME/MIDP; Opera Mini/5", "Opera/9.80 (Android; Opera Mini/5.0", "Opera/9.80 (Android; Opera Mini/5.1"};

   public OperaMiniOnAndroidMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GENERIC_OPERA_MINI_ANDROID_VERSION5);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(var1.getCleanedDeviceUserAgent(), "Android", "Opera Mini");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = var1.indexOf(" Build/")) < 0) {
         String[] var3;
         for(String var6 : var3 = OPERA_MINI_ANDROID_PREFIXES) {
            if (var1.startsWith(var6)) {
               var2 = var6.length();
               break;
            }
         }
      }

      return var2 >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return GENERIC_OPERA_MINI_ANDROID_VERSION5;
   }

   public final String getMatcherName() {
      return "OperaMiniOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMiniOnAndroid";
   }
}
