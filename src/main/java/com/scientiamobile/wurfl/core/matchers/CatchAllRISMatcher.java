package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.Collection;
import org.apache.commons.lang3.StringUtils;

final class CatchAllRISMatcher extends AbstractMatcher {
   public final boolean canHandle(WURFLRequest var1) {
      return true;
   }

   protected final String risMatch(String var1) {
      Collection var2 = this.getFilter().getIndex().getUserAgents();
      if (StringUtils.startsWith(var1, "CFNetwork")) {
         int var3;
         if ((var3 = StringMatchUtils.firstSpace(var1)) != -1) {
            return StringMatchUtils.risMatch(var2, var1, var3);
         }
      } else {
         int var4;
         if ((var4 = StringMatchUtils.firstSlash(var1)) != -1) {
            return StringMatchUtils.risMatch(var2, var1, var4);
         }
      }

      return StringMatchUtils.NULL_STRING;
   }

   public final String getMatcherName() {
      return "CatchAllRISMatcher";
   }

   public final String getBucketMatcherName() {
      return "CatchAllRis";
   }
}

