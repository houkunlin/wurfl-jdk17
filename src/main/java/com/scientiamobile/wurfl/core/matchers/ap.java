package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ap extends a {
   private static String b = "generic_opera_mini_android_version5";
   private static String[] c = new String[]{"Opera/9.80 (J2ME/MIDP; Opera Mini/5", "Opera/9.80 (Android; Opera Mini/5.0", "Opera/9.80 (Android; Opera Mini/5.1"};

   public ap(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(var1.getCleanedDeviceUserAgent(), "Android", "Opera Mini");
   }

   protected final String a(String var1) {
      int var2;
      if ((var2 = var1.indexOf(" Build/")) < 0) {
         String[] var3;
         for(String var6 : var3 = c) {
            if (var1.startsWith(var6)) {
               var2 = var6.length();
               break;
            }
         }
      }

      return var2 >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2) : null;
   }

   protected final String b(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "OperaMiniOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMiniOnAndroid";
   }
}
