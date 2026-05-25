package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

final class SonyEricssonMatcher extends a {
   public SonyEricssonMatcher(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && var1.getCleanedDeviceUserAgent().contains("Sony");
   }

   protected final String risMatch(String var1) {
      if (var1.startsWith("SonyEricsson")) {
         int var3 = StringMatchUtils.firstSlash(var1);
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var3 - 2);
      } else {
         int var2;
         return (var2 = StringMatchUtils.secondSlash(var1)) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : StringMatchUtils.NULL_STRING;
      }
   }

   public final String getMatcherName() {
      return "SonyEricssonMatcher";
   }

   public final String getBucketMatcherName() {
      return "SonyEricsson";
   }
}
