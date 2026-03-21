package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class ak extends a {
  public ak(WURFLModel paramWURFLModel) {
    super(paramWURFLModel);
  }
  
  protected final Set a() {
    HashSet<String> hashSet;
    (hashSet = new HashSet<String>()).add("nintendo_wii_u_ver1");
    hashSet.add("nintendo_wii_ver1");
    hashSet.add("nintendo_dsi_ver1");
    hashSet.add("nintendo_ds_ver1");
    hashSet.add("nintendo_3ds_ver1");
    hashSet.add("nintendo_new3ds_ver1");
    hashSet.add("nintendo_switch_ver1");
    return hashSet;
  }
  
  public final boolean canHandle(WURFLRequest paramWURFLRequest) {
    String str = paramWURFLRequest.getCleanedDeviceUserAgent();
    return paramWURFLRequest._internalIsDesktopBrowser() ? false : (str.contains("Nintendo") ? true : ((str.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(str, new String[] { "Nitro", "Opera" }))));
  }
  
  protected final String a(WURFLRequest paramWURFLRequest) {
    String str;
    return (str = paramWURFLRequest.getNormalizedDeviceUserAgent()).contains("New Nintendo 3DS") ? "nintendo_new3ds_ver1" : (str.contains("Nintendo 3DS") ? "nintendo_3ds_ver1" : (str.contains("Nintendo WiiU") ? "nintendo_wii_u_ver1" : (str.contains("Nintendo Wii") ? "nintendo_wii_ver1" : (str.contains("Nintendo DSi") ? "nintendo_dsi_ver1" : (str.contains("Nintendo Switch") ? "nintendo_switch_ver1" : ((str.startsWith("Mozilla/") && StringMatchUtils.containsAllOf(str, new String[] { "Nitro", "Opera" })) ? "nintendo_ds_ver1" : "nintendo_wii_ver1"))))));
  }
  
  public final String getMatcherName() {
    return "NintendoMatcher";
  }
  
  public final String getBucketMatcherName() {
    return "Nintendo";
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\matchers\ak.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */