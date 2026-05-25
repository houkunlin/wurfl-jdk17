package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class CatchAllMozillaMatcher extends AbstractMatcher {
   public CatchAllMozillaMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add("generic");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return StringMatchUtils.startsWithAnyOf(var1.getCleanedDeviceUserAgent(), "Mozilla/3", "Mozilla/4", "Mozilla/5");
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      String var4 = var1.getNormalizedDeviceUserAgent();
      int var3;
      var4 = (var3 = StringMatchUtils.firstCloseParenthesis(var4)) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var4, var3) : StringMatchUtils.NULL_STRING;
      String var2 = "generic";
      if (var4 != null) {
         var2 = this.getFilter().getIndex().getDeviceIdByUserAgent(var4);
      }

      if (var2 == null) {
         var2 = "generic";
      }

      return var2;
   }

   public final String getMatcherName() {
      return "CatchAllMozillaMatcher";
   }

   public final String getBucketMatcherName() {
      return "CatchAllMozilla";
   }
}
