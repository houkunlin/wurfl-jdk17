package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

final class UCWEB7OnAndroidMatcher extends MatcherBase {
   private static final String GENERIC_ANDROID_VER2_0_UCWEB = "generic_android_ver2_0_ucweb";
   private static final Map<String, String> ANDROID_VERSION_TO_DEVICE_ID;

   public UCWEB7OnAndroidMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(ANDROID_VERSION_TO_DEVICE_ID.values());
      var1.add(GENERIC_ANDROID_VER2_0_UCWEB);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAllOf(var2, "Android", "UCWEB7");
   }

   protected final String risMatch(String var1) {
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, StringMatchUtils.indexOfOrLength(var1, "UCWEB7"));
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2 = UserAgentUtils.getAndroidVersion(var1.getNormalizedDeviceUserAgent(), true);
      String var3;
      return (var3 = ANDROID_VERSION_TO_DEVICE_ID.get(var2)) != null ? var3 : GENERIC_ANDROID_VER2_0_UCWEB;
   }

   public final String getMatcherName() {
      return "UCWEB7OnAndroidMatcher";
   }

   public final String getBucketMatcherName() {
      return "Ucweb7OnAndroid";
   }

   static {
      (ANDROID_VERSION_TO_DEVICE_ID = new HashMap<>()).put("1.6", "generic_android_ver1_6_ucweb");
      ANDROID_VERSION_TO_DEVICE_ID.put("2.1", "generic_android_ver2_1_ucweb");
      ANDROID_VERSION_TO_DEVICE_ID.put("2.2", "generic_android_ver2_2_ucweb");
      ANDROID_VERSION_TO_DEVICE_ID.put("2.3", "generic_android_ver2_3_ucweb");
   }
}
