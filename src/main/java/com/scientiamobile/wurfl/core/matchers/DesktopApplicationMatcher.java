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

   public DesktopApplicationMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(SUPPORTED_DEVICE_IDS);
      var1.add(GENERIC_WEB_BROWSER);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Microsoft Office", "MSOffice", "office", "DesktopApp ");
   }

   protected final String risMatch(String var1) {
      Matcher var2 = MSOFFICE_PATTERN.matcher(var1);
      Matcher var3 = MICROSOFT_OFFICE_PATTERN.matcher(var1);
      if (var2.find()) {
         int var6;
         if ((var6 = StringMatchUtils.firstCloseParenthesis(var1 = var1.substring(var1.indexOf("MSOffice")))) != -1) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var6);
         }
      } else {
         int var7;
         if (var3.find() && (var7 = (var1 = var1.substring(var1.indexOf("Microsoft Office"))).indexOf(46)) != -1) {
            return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var7);
         }
      }

      return "generic";
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      if (StringMatchUtils.containsAnyOf(var1.getDeviceUserAgent(), "Office", "office")) {
         return MS_OFFICE;
      } else {
         return var1.getDeviceUserAgent().contains("DesktopApp ") ? GENERIC_DESKTOP_APPLICATION : GENERIC_WEB_BROWSER;
      }
   }

   public final String getMatcherName() {
      return "DesktopApplicationMatcher";
   }

   public final String getBucketMatcherName() {
      return "DesktopApplication";
   }

   static {
      SUPPORTED_DEVICE_IDS.add(GENERIC_DESKTOP_APPLICATION);
      SUPPORTED_DEVICE_IDS.add(MS_OFFICE);
   }
}
