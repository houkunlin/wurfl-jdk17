package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class HTCMatcher extends MatcherBase {
   private static final Pattern HTC_PREFIX = Pattern.compile("^.*?HTC.+?[/ ;]");

   public HTCMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add("generic");
      requiredDeviceIds.add("generic_ms_mobile");
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "HTC", "XV6875");
   }

   @Override
   protected final String risMatch(String normalizedUserAgent) {
      int matchLength = normalizedUserAgent.length();
      Matcher prefixMatcher;
      if ((prefixMatcher = HTC_PREFIX.matcher(normalizedUserAgent)).find()) {
         matchLength = prefixMatcher.group(0).length();
      }

      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().contains("Windows CE;") ? "generic_ms_mobile" : "generic";
   }

   @Override
   public final String getMatcherName() {
      return "HTCMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "HTC";
   }
}
