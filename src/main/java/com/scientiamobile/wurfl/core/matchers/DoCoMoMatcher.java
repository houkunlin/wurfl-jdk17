package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class DoCoMoMatcher extends MatcherBase {
   private static final String DOCOMO_VER2 = "docomo_generic_jap_ver2";
   private static final String DOCOMO_VER1 = "docomo_generic_jap_ver1";

   public DoCoMoMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds;
      (requiredDeviceIds = new HashSet<>()).add(DOCOMO_VER1);
      requiredDeviceIds.add(DOCOMO_VER2);
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().startsWith("DoCoMo");
   }

   @Override
   protected final String risMatch(String normalizedUserAgent) {
      int matchLength;
      if ((matchLength = StringMatchUtils.secondSlash(normalizedUserAgent)) == -1) {
         matchLength = StringMatchUtils.firstOpenParenthesis(normalizedUserAgent);
      }

      return matchLength != -1
         ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
         : StringMatchUtils.NULL_STRING;
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      return request.getNormalizedDeviceUserAgent().startsWith("DoCoMo/2") ? DOCOMO_VER2 : DOCOMO_VER1;
   }

   @Override
   public final String getMatcherName() {
      return "DoCoMoMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "DoCoMo";
   }
}
