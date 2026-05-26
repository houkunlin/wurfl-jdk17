package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

final class SamsungMatcher extends MatcherBase {
   private static final String[] LEADING_SLASH_PREFIXES = new String[]{"SEC-", "SAMSUNG-", "SCH"};
   private static final String[] LEADING_SPACE_PREFIXES = new String[]{"Samsung", "SPH", "SGH"};
   private static final String[] CAN_HANDLE_PREFIXES = new String[]{"SEC-", "SPH", "SGH", "SCH"};

   public SamsungMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add("generic");
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      if (request.getOriginalUserAgent().contains("SamsungBrowser")) {
         return false;
      } else {
         return !request._internalIsDesktopBrowser() && (StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, CAN_HANDLE_PREFIXES) || cleanedDeviceUserAgent.toLowerCase().contains("samsung"));
      }
   }

   @Override
   protected final String risMatch(String userAgent) {
      int matchLength = StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SLASH_PREFIXES) ? StringMatchUtils.firstSlash(userAgent) : (StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SPACE_PREFIXES) ? StringMatchUtils.firstSpace(userAgent) : StringMatchUtils.secondSlash(userAgent));
      return matchLength == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
      int samsungIndex = StringMatchUtils.indexOf(normalizedUserAgent, "Samsung");
      int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", samsungIndex);
      String matchedUserAgent = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
      return !StringUtils.isBlank(matchedUserAgent) ? this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent) : "generic";
   }

   @Override
   public final String getMatcherName() {
      return "SamsungMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "Samsung";
   }
}
