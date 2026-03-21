package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ah extends a {
  private static String b = "mot_mib22_generic";
  
  public ah(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("generic");
    hashSet.add(b);
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return ((!paramWURFLRequest._internalIsDesktopBrowser() && StringMatchUtils.startsWithAnyOf(str, new String[] { "Mot-", "MOT-", "MOTO", "moto" })) || str.contains("Motorola"));
  }
  
  protected final String b(WURFLRequest paramWURFLRequest) {
    return StringMatchUtils.containsAnyOf(paramWURFLRequest.getNormalizedDeviceUserAgent(), new String[] { "MIB/2.2", "MIB/BER2.2" }) ? b : "generic";
  }
  
  public final String getMatcherName() {
    return "MotorolaMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Motorola";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ah.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */