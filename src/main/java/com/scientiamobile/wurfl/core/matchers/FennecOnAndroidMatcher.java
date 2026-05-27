package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;

final class FennecOnAndroidMatcher extends MatcherBase {
   private static final Pattern VERSION_PREFIX = Pattern.compile("^.+?\\(.+?rv:\\d+(\\.)");
   private static final String GENERIC_ANDROID_FENNEC_2 = "generic_android_ver2_0_fennec";
   private static final String GENERIC_ANDROID_FENNEC_2_TABLET = "generic_android_ver2_0_fennec_tablet";
   private static final String GENERIC_ANDROID_FENNEC_2_DESKTOP = "generic_android_ver2_0_fennec_desktop";
   private Set<String> requiredDeviceIds = this.getRequiredDeviceIds();

   public FennecOnAndroidMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected Set<String> getRequiredDeviceIds() {
      if (this.requiredDeviceIds != null) {
         return this.requiredDeviceIds;
      } else {
         HashSet<String> requiredDeviceIds;
         (requiredDeviceIds = new HashSet<>()).add("generic");
         requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2);
         requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2_TABLET);
         requiredDeviceIds.add(GENERIC_ANDROID_FENNEC_2_DESKTOP);
         requiredDeviceIds.add("generic_android_ver4_fennec");
         requiredDeviceIds.add("generic_android_ver4_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver4_fennec_desktop");
         requiredDeviceIds.add("generic_android_ver5_0_fennec");
         requiredDeviceIds.add("generic_android_ver5_0_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver5_0_fennec_desktop");
         requiredDeviceIds.add("generic_android_ver6_0_fennec");
         requiredDeviceIds.add("generic_android_ver6_0_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver6_0_fennec_desktop");
         requiredDeviceIds.add("generic_android_ver7_0_fennec");
         requiredDeviceIds.add("generic_android_ver7_0_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver7_0_fennec_desktop");
         requiredDeviceIds.add("generic_android_ver8_0_fennec");
         requiredDeviceIds.add("generic_android_ver8_0_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver8_0_fennec_desktop");
         requiredDeviceIds.add("generic_android_ver9_0_fennec");
         requiredDeviceIds.add("generic_android_ver9_0_fennec_tablet");
         requiredDeviceIds.add("generic_android_ver9_0_fennec_desktop");
         this.requiredDeviceIds = requiredDeviceIds;
         return requiredDeviceIds;
      }
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Android") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Fennec", "Firefox");
   }

   @Override
   protected String risMatch(String normalizedUserAgent) {
      Matcher versionPrefixMatcher = VERSION_PREFIX.matcher(normalizedUserAgent);
      int matchLength;
      return versionPrefixMatcher.find() && (matchLength = versionPrefixMatcher.end()) < normalizedUserAgent.length()
         ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
         : null;
   }

   @Override
   protected String applyRecoveryMatch(WURFLRequest request) {
      String deviceId = null;
      int androidMajorVersion = 0;
      String androidVersion;
      String normalizedUserAgent;
      androidVersion = UserAgentUtils.getAndroidVersion(normalizedUserAgent = request.getNormalizedDeviceUserAgent(), false);
      if (androidVersion != null) {
         String[] versionParts = androidVersion.split("\\.");
         androidMajorVersion = ArrayUtils.isNotEmpty(versionParts) ? Integer.parseInt(versionParts[0]) : 0;
         deviceId = "generic_android_ver" + androidMajorVersion + "_0_fennec";
      }

      if (androidMajorVersion < 5) {
         deviceId = "generic_android_ver4_fennec";
      }

      if (StringMatchUtils.containsAllOf(normalizedUserAgent, "Firefox", "Tablet")) {
         deviceId = deviceId + "_tablet";
      } else if (StringMatchUtils.containsAllOf(normalizedUserAgent, "Firefox", "Desktop")) {
         deviceId = deviceId + "_desktop";
      }

      return this.requiredDeviceIds.contains(deviceId) ? deviceId : "generic_android_ver4_fennec";
   }

   @Override
   public String getMatcherName() {
      return "FennecOnAndroidMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "FennecOnAndroid";
   }
}
