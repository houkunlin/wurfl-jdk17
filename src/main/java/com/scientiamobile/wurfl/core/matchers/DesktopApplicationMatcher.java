package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public final class DesktopApplicationMatcher extends a {
   private static String b = "ms_office";
   private static final Set c = new HashSet();
   private static final Pattern d = Pattern.compile("MSOffice ([0-9]+)");
   private static final Pattern e = Pattern.compile("Microsoft Office/([0-9.]+)");

   public DesktopApplicationMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(c);
      var1.add("generic_web_browser");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Microsoft Office", "MSOffice", "office", "DesktopApp ");
   }

   protected final String a(String var1) {
      Matcher var2 = d.matcher(var1);
      Matcher var3 = e.matcher(var1);
      if (var2.find()) {
         int var6;
         if ((var6 = StringMatchUtils.firstCloseParenthesis(var1 = var1.substring(var1.indexOf("MSOffice")))) != -1) {
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var6);
         }
      } else {
         int var7;
         if (var3.find() && (var7 = (var1 = var1.substring(var1.indexOf("Microsoft Office"))).indexOf(46)) != -1) {
            return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var7);
         }
      }

      return "generic";
   }

   protected final String b(WURFLRequest var1) {
      if (StringMatchUtils.containsAnyOf(var1.getDeviceUserAgent(), "Office", "office")) {
         return b;
      } else {
         return StringUtils.contains(var1.getDeviceUserAgent(), "DesktopApp ") ? "generic_desktop_application" : "generic_web_browser";
      }
   }

   public final String getMatcherName() {
      return "DesktopApplicationMatcher";
   }

   public final String getBucketMatcherName() {
      return "DesktopApplication";
   }

   static {
      c.add("generic_desktop_application");
      c.add(b);
   }
}
