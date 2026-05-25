package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class NetFrontOnAndroidMatcher extends a {
   private static final String GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER = "generic_android_ver2_0_netfrontlifebrowser";
   private static final Map ANDROID_VERSION_TO_DEVICE_ID;

   public NetFrontOnAndroidMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add(GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(var2, "Android", "NetFrontLifeBrowser/2.2");
   }

   protected final String risMatch(String var1) {
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, StringMatchUtils.indexOfOrLength(var1, "NetFrontLifeBrowser/2.2"));
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2 = UserAgentUtils.getAndroidVersion(var1.getNormalizedDeviceUserAgent(), true);
      String var3;
      return (var3 = (String)ANDROID_VERSION_TO_DEVICE_ID.get(var2)) != null ? var3 : GENERIC_ANDROID_VER2_0_NETFRONT_LIFEBROWSER;
   }

   public final String getMatcherName() {
      return "NetFrontOnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "NetFrontOnAndroid";
   }

   static {
      (ANDROID_VERSION_TO_DEVICE_ID = new HashMap()).put("2.1", "generic_android_ver2_1_netfrontlifebrowser");
      ANDROID_VERSION_TO_DEVICE_ID.put("2.2", "generic_android_ver2_2_netfrontlifebrowser");
      ANDROID_VERSION_TO_DEVICE_ID.put("2.3", "generic_android_ver2_3_netfrontlifebrowser");
   }
}
