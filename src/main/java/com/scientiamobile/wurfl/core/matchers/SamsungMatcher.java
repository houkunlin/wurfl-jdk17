package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class SamsungMatcher extends a {
   private static final String[] b = new String[]{"SEC-", "SAMSUNG-", "SCH"};
   private static final String[] c = new String[]{"Samsung", "SPH", "SGH"};
   private static final String[] d = new String[]{"SEC-", "SPH", "SGH", "SCH"};

   public SamsungMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add("generic");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      if (var1.getOriginalUserAgent().contains("SamsungBrowser")) {
         return false;
      } else {
         return !var1._internalIsDesktopBrowser() && (StringMatchUtils.startsWithAnyOf(var2, d) || var2.toLowerCase().contains("samsung"));
      }
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = StringMatchUtils.startsWithAnyOf(var1, b) ? StringMatchUtils.firstSlash(var1) : (StringMatchUtils.startsWithAnyOf(var1, c) ? StringMatchUtils.firstSpace(var1) : StringMatchUtils.secondSlash(var1))) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var3;
      int var2 = StringMatchUtils.indexOf(var3 = var1.getNormalizedDeviceUserAgent(), "Samsung");
      var2 = StringMatchUtils.indexOfOrLength(var3, "/", var2);
      String var4;
      return !StringUtils.isBlank(var4 = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var3, var2)) ? this.getFilter().getIndex().getDeviceIdByUserAgent(var4) : "generic";
   }

   public final String getMatcherName() {
      return "SamsungMatcher";
   }

   public final String getBucketMatcherName() {
      return "Samsung";
   }
}

