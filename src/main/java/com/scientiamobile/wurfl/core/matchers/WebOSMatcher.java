package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class WebOSMatcher extends AbstractMatcher {
   private static final String HP_TABLET_WEBOS_GENERIC = "hp_tablet_webos_generic";
   private static final String HP_WEBOS_GENERIC = "hp_webos_generic";

   public WebOSMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add(HP_TABLET_WEBOS_GENERIC);
      requiredDeviceIds.add(HP_WEBOS_GENERIC);
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "webOS", "hpwOS");
   }

   @Override
   protected final String risMatch(String userAgent) {
      int matchLength = StringMatchUtils.indexOfOrLength(userAgent, "---");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().contains("hpwOS/3") ? HP_TABLET_WEBOS_GENERIC : HP_WEBOS_GENERIC;
   }

   @Override
   public final String getMatcherName() {
      return "WebOSMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "WebOS";
   }
}
