package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TizenMatcher extends MatcherBase {
   private static final String GENERIC_TIZEN = "generic_tizen";
   private static final Pattern TIZEN_VERSION_PATTERN = Pattern.compile("Tizen (\\d+?\\.\\d+?)");
   private static final List<String> SUPPORTED_DEVICE_IDS = new ArrayList<>();
   private static final List<String> SUPPORTED_VERSIONS = new ArrayList<>();

   public TizenMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>(SUPPORTED_DEVICE_IDS);
      requiredDeviceIds.add(GENERIC_TIZEN);
      return requiredDeviceIds;
   }

   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return cleanedDeviceUserAgent.startsWith("Mozilla") && cleanedDeviceUserAgent.contains("Tizen");
   }

   protected final String risMatch(String userAgent) {
      int appleWebKitIndex = userAgent.indexOf("AppleWebKit/");
      return appleWebKitIndex >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, appleWebKitIndex + 12) : null;
   }

   protected final String applyRecoveryMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      Matcher versionMatcher = TIZEN_VERSION_PATTERN.matcher(normalizedUserAgent);
      String deviceIdSuffix = versionMatcher.find() && SUPPORTED_VERSIONS.contains(versionMatcher.group(1)) ? versionMatcher.group(1).replace('.', '_') : "1_0";
      String tizenDeviceId = (new StringBuilder("generic_tizen_ver")).append(deviceIdSuffix).toString();
      return SUPPORTED_DEVICE_IDS.contains(tizenDeviceId) ? tizenDeviceId : GENERIC_TIZEN;
   }

   public String getMatcherName() {
      return "TizenMatcher";
   }

   public String getBucketMatcherName() {
      return "Tizen";
   }

   static {
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver1_0");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_0");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_1");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_2");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_3");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_4");
      SUPPORTED_DEVICE_IDS.add("generic_tizen_ver3_0");
      SUPPORTED_VERSIONS.add("1.0");
      SUPPORTED_VERSIONS.add("2.0");
      SUPPORTED_VERSIONS.add("2.1");
      SUPPORTED_VERSIONS.add("2.2");
      SUPPORTED_VERSIONS.add("2.3");
      SUPPORTED_VERSIONS.add("2.4");
      SUPPORTED_VERSIONS.add("3.0");
   }
}
