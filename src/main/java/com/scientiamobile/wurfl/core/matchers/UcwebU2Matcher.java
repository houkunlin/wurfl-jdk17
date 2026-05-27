package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class UcwebU2Matcher extends MatcherBase {
   @Override
   protected Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>();
      requiredDeviceIds.add("generic_ucweb");
      return requiredDeviceIds;
   }

   public UcwebU2Matcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
      return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.startsWith("UCWEB") && cleanedDeviceUserAgent.contains("UCBrowser");
   }

   @Override
   protected String risMatch(String userAgent) {
      if (UserAgentUtils.getUcBrowserVersion(userAgent, true) == null) {
         return null;
      } else {
         int matchLength;
         matchLength = userAgent.indexOf("---");
         if (matchLength > 0) {
            matchLength += 3;
            String subUserAgent = userAgent.substring(matchLength);
            if (userAgent.contains("Adr")) {
               String androidModel = UserAgentUtils.getUcAndroidModel(userAgent, false);
               String androidVersion = UserAgentUtils.getUcAndroidVersion(userAgent, false);
               if (androidModel != null && androidVersion != null) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
               }
            } else if (userAgent.contains("iPh OS")) {
               if (UcwebU2Normalizer.IPHONE.matcher(subUserAgent).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
               }
            } else if (userAgent.contains("wds")) {
               if (UcwebU2Normalizer.WINDOWS_PHONE.matcher(subUserAgent).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
               }
            } else if (userAgent.contains("Symbian")) {
               if (UcwebU2Normalizer.SYMBIAN.matcher(subUserAgent).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
               }
            } else if (userAgent.contains("Java") && UcwebU2Normalizer.JAVA.matcher(subUserAgent).find()) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
            }
         }

         return null;
      }
   }

   @Override
   protected String applyRecoveryMatch(WURFLRequest request) {
      return "generic_ucweb";
   }

   @Override
   public String getMatcherName() {
      return "UcwebU2Matcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "UcwebU2";
   }
}
