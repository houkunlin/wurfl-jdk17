package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang.StringUtils;

final class SPVMatcher extends a {
   public SPVMatcher(WURFLModel var1) {
      super(var1);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.contains(var1.getCleanedDeviceUserAgent(), "SPV");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, ";", StringMatchUtils.indexOfOrLength(var1, "SPV"));
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   public final String getMatcherName() {
      return "SPVMatcher";
   }

   public final String getBucketMatcherName() {
      return "SPV";
   }
}

