package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DesktopApplicationMatcher extends MatcherBase {
   private static final String GENERIC_WEB_BROWSER = "generic_web_browser";
   private static final String GENERIC_DESKTOP_APPLICATION = "generic_desktop_application";
   private static final String MS_OFFICE = "ms_office";
   private static final Set<String> SUPPORTED_DEVICE_IDS = new HashSet<>();
   private static final Pattern MSOFFICE_PATTERN = Pattern.compile("MSOffice ([0-9]+)");
   private static final Pattern MICROSOFT_OFFICE_PATTERN = Pattern.compile("Microsoft Office/([0-9.]+)");

   public DesktopApplicationMatcher(WURFLModel wurflModel) {
      super(wurflModel);
   }

   @Override
   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> requiredDeviceIds = new HashSet<>(SUPPORTED_DEVICE_IDS);
      requiredDeviceIds.add(GENERIC_WEB_BROWSER);
      return requiredDeviceIds;
   }

   @Override
   public final boolean canHandle(WURFLRequest request) {
      return !request._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "Microsoft Office", "MSOffice", "office", "DesktopApp ");
   }

   @Override
   protected final String risMatch(String userAgent) {
      Matcher msOfficeMatcher = MSOFFICE_PATTERN.matcher(userAgent);
      Matcher microsoftOfficeMatcher = MICROSOFT_OFFICE_PATTERN.matcher(userAgent);
      if (msOfficeMatcher.find()) {
         userAgent = userAgent.substring(userAgent.indexOf("MSOffice"));
         int matchLength = StringMatchUtils.firstCloseParenthesis(userAgent);
         if (matchLength != -1) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
         }
      } else {
         if (microsoftOfficeMatcher.find()) {
            userAgent = userAgent.substring(userAgent.indexOf("Microsoft Office"));
            int dotIndex = userAgent.indexOf(46);
            if (dotIndex != -1) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, dotIndex);
            }
         }
      }

      return "generic";
   }

   @Override
   protected final String applyRecoveryMatch(WURFLRequest request) {
      String deviceUserAgent = request.getDeviceUserAgent();
      if (StringMatchUtils.containsAnyOf(deviceUserAgent, "Office", "office")) {
         return MS_OFFICE;
      } else {
         return deviceUserAgent.contains("DesktopApp ") ? GENERIC_DESKTOP_APPLICATION : GENERIC_WEB_BROWSER;
      }
   }

   @Override
   public final String getMatcherName() {
      return "DesktopApplicationMatcher";
   }

   @Override
   public final String getBucketMatcherName() {
      return "DesktopApplication";
   }

   static {
      SUPPORTED_DEVICE_IDS.add(GENERIC_DESKTOP_APPLICATION);
      SUPPORTED_DEVICE_IDS.add(MS_OFFICE);
   }
}
