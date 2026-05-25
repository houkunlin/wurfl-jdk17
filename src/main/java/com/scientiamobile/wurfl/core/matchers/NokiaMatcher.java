package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class NokiaMatcher extends a {
   private static final String NOKIA_GENERIC_SERIES60 = "nokia_generic_series60";
   private static final String NOKIA_GENERIC_SERIES80 = "nokia_generic_series80";
   private static final String NOKIA_GENERIC_MEEGO = "nokia_generic_meego";

   public NokiaMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(NOKIA_GENERIC_SERIES60);
      var1.add(NOKIA_GENERIC_SERIES80);
      var1.add(NOKIA_GENERIC_MEEGO);
      var1.add("generic_mobile");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Nokia") && !StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Android", "iPhone");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfAnyOrLength(var1, new String[]{"/", " "}, var1.indexOf("Nokia"));
      if (StringMatchUtils.startsWithAnyOf(var1, "Nokia/", "Nokia ")) {
         var2 = var1.length();
      }

      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      if (StringUtils.contains(var2 = var1.getNormalizedDeviceUserAgent(), "Series60")) {
         return NOKIA_GENERIC_SERIES60;
      } else if (StringUtils.contains(var2, "Series80")) {
         return NOKIA_GENERIC_SERIES80;
      } else {
         return StringUtils.contains(var2, "MeeGo") ? NOKIA_GENERIC_MEEGO : "generic";
      }
   }

   public final String getMatcherName() {
      return "NokiaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Nokia";
   }
}
