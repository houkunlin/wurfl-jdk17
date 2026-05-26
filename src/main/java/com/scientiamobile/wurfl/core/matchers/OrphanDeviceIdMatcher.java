package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

public class OrphanDeviceIdMatcher extends MatcherBase {
   public OrphanDeviceIdMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   public boolean canHandle(WURFLRequest request) {
      return false;
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add("opwv_v6_generic");
      requiredDeviceIds.add("opwv_v7_generic");
      requiredDeviceIds.add("opwv_v72_generic");
      requiredDeviceIds.add("upgui_generic");
      requiredDeviceIds.add("uptext_generic");
      requiredDeviceIds.add("generic_netfront_ver3");
      requiredDeviceIds.add("generic_netfront_ver3_1");
      requiredDeviceIds.add("generic_netfront_ver3_2");
      requiredDeviceIds.add("generic_netfront_ver3_3");
      requiredDeviceIds.add("generic_netfront_ver3_4");
      requiredDeviceIds.add("generic_netfront_ver3_5");
      requiredDeviceIds.add("generic_netfront_ver4_0");
      return requiredDeviceIds;
   }
}
