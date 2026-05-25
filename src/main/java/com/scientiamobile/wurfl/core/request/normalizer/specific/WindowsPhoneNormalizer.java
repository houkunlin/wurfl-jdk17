package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowsPhoneNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile("Windows ?Phone ?Ad ?Client/[0-9\\.]+ ?\\(.+; ?Windows ?Phone(?: ?OS)? ?[0-9\\.]+; ?([^;\\)]+(; ?[^;\\)]+)?)");
   private static final Pattern b = Pattern.compile("^[^/]+/[0-9\\.-_]+ Windows Phone/([\\d\\.]+) (.+)$");

   public String normalize(String var1) {
      String var2 = null;
      String var3 = null;
      Matcher var4;
      if ((var4 = b.matcher(var1)).find()) {
         var1 = "Mozilla/5.0 (Mobile; Windows Phone " + var4.group(1) + "; Android 4.0; ARM; Trident/7.0; Touch; rv:11.0; IEMobile/11.0; " + var4.group(2) + ") like iPhone OS 7_0_3 Mac OS X AppleWebKit/537 (KHTML, like Gecko) Mobile Safari/537 " + var1;
      }

      if (!StringMatchUtils.containsAnyOf(var1, "WPDesktop", "ZuneWP7") && !StringMatchUtils.containsAllOf(var1, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
         if (UserAgentUtils.isWindowsPhoneAdClient(var1)) {
            var2 = UserAgentUtils.cleanAndReplaceWindowsPhoneModel(var1, a);
            var3 = UserAgentUtils.getWindowsPhoneVersion(var1);
         } else if (!var1.contains("NativeHost")) {
            var2 = UserAgentUtils.getWindowsPhoneModel(var1);
            var3 = UserAgentUtils.getWindowsPhoneVersion(var1);
         }
      } else {
         var2 = UserAgentUtils.getWindowsPhoneDesktopModel(var1);
         var3 = UserAgentUtils.getWindowsPhoneDesktopVersion(var1);
      }

      return var2 != null && var3 != null ? "WP" + var3 + " " + var2 + "---" + var1 : var1;
   }
}
