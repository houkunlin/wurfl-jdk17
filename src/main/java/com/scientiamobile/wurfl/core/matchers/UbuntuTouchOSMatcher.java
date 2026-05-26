package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

public class UbuntuTouchOSMatcher extends MatcherBase {
   private static String b = "generic_ubuntu_touch_os";
   private static String c = "generic_ubuntu_touch_os_tablet";

   public UbuntuTouchOSMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> a() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(b);
      var1.add(c);
      return var1;
   }

   public boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("Ubuntu") && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Mobile", "Tablet");
   }

   protected final String a(String var1) {
      int var2;
      if ((var2 = var1.indexOf("like Android")) >= 0) {
         var2 += 12;
      } else if ((var2 = var1.indexOf("WebKit/")) >= 0) {
         var2 += 7;
      }

      return var2 >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2) : null;
   }

   protected final String b(WURFLRequest var1) {
      return var1.getNormalizedDeviceUserAgent().contains("Tablet") ? c : b;
   }

   public String getMatcherName() {
      return "UbuntuTouchOSMatcher";
   }

   public String getBucketMatcherName() {
      return "UbuntuTouchOS";
   }
}
