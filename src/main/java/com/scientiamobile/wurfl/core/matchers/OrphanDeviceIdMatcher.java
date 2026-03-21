package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.HashSet;
import java.util.Set;

public class OrphanDeviceIdMatcher extends a {
  public OrphanDeviceIdMatcher(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  public boolean canHandle(WURFLRequest paramWURFLRequest) {
    return false;
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("opwv_v6_generic");
    hashSet.add("opwv_v7_generic");
    hashSet.add("opwv_v72_generic");
    hashSet.add("upgui_generic");
    hashSet.add("uptext_generic");
    hashSet.add("generic_netfront_ver3");
    hashSet.add("generic_netfront_ver3_1");
    hashSet.add("generic_netfront_ver3_2");
    hashSet.add("generic_netfront_ver3_3");
    hashSet.add("generic_netfront_ver3_4");
    hashSet.add("generic_netfront_ver3_5");
    hashSet.add("generic_netfront_ver4_0");
    return hashSet;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\OrphanDeviceIdMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */