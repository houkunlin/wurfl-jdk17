package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

public class OrphanDeviceIdMatcher extends MatcherBase {
   public OrphanDeviceIdMatcher(WURFLModel var1) {
      super(var1);
   }

   public boolean canHandle(WURFLRequest var1) {
      return false;
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add("opwv_v6_generic");
      var1.add("opwv_v7_generic");
      var1.add("opwv_v72_generic");
      var1.add("upgui_generic");
      var1.add("uptext_generic");
      var1.add("generic_netfront_ver3");
      var1.add("generic_netfront_ver3_1");
      var1.add("generic_netfront_ver3_2");
      var1.add("generic_netfront_ver3_3");
      var1.add("generic_netfront_ver3_4");
      var1.add("generic_netfront_ver3_5");
      var1.add("generic_netfront_ver4_0");
      return var1;
   }
}
