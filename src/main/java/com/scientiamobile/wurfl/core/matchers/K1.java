package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class K extends a {
   private static final Pattern b = Pattern.compile("^.*?HTC.+?[/ ;]");

   public K(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add("generic");
      var1.add("generic_ms_mobile");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "HTC", "XV6875");
   }

   protected final String a(String var1) {
      int var2 = var1.length();
      Matcher var3;
      if ((var3 = b.matcher(var1)).find()) {
         var2 = var3.group(0).length();
      }

      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
   }

   public final String getMatcherName() {
      return "HTCMatcher";
   }

   public final String getBucketMatcherName() {
      return "HTC";
   }
}
