package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class al extends a {
   private static String b = "nokia_generic_series60";
   private static String c = "nokia_generic_series80";
   private static String d = "nokia_generic_meego";

   public al(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add(c);
      var1.add(d);
      var1.add("generic_mobile");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Nokia") && !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Android", "iPhone");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOfAnyOrLength(var1, new String[]{"/", " "}, var1.indexOf("Nokia"));
      if (StringMatchUtils.startsWithAnyOf(var1, "Nokia/", "Nokia ")) {
         var2 = var1.length();
      }

      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      String var2;
      if (StringUtils.contains(var2 = var1.getNormalizedDeviceUserAgent(), "Series60")) {
         return b;
      } else if (StringUtils.contains(var2, "Series80")) {
         return c;
      } else {
         return StringUtils.contains(var2, "MeeGo") ? d : "generic";
      }
   }

   public final String getMatcherName() {
      return "NokiaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Nokia";
   }
}
