package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcwebU3Normalizer implements UserAgentNormalizer {
   public static final Pattern IPHONE = Pattern.compile("iPhone OS (\\d+)_(\\d+)(?:_\\d+)* like");
   public static final Pattern IPAD = Pattern.compile("CPU OS (\\d+)_(\\d+)?.+like Mac.+; iPad([0-9,]+)\\) AppleWebKit");

   public String normalize(String var1) {
      String var2;
      if ((var2 = UserAgentUtils.getUcBrowserVersion(var1, false)) == null) {
         return var1;
      } else {
         String var3 = null;
         if (var1.contains("Windows Phone")) {
            String var4 = UserAgentUtils.getWindowsPhoneVersion(var1);
            String var5;
            if ((var5 = UserAgentUtils.getWindowsPhoneModel(var1)) != null && var4 != null) {
               var3 = var4 + " U3WP " + var2 + " " + var5 + "---";
            }
         } else if (var1.contains("Android")) {
            String var8 = UserAgentUtils.getAndroidModel(var1);
            String var12 = UserAgentUtils.getAndroidVersion(var1, false);
            if (var8 != null && var12 != null) {
               var3 = var12 + " U3Android " + var2 + " " + var8 + "---";
            }
         } else if (var1.contains("iPhone;")) {
            Matcher var9;
            if ((var9 = IPHONE.matcher(var1)).find()) {
               String var13 = var9.group(1) + "." + (var9.group(2) == null ? "" : var9.group(2));
               var3 = var13 + " U3iPhone " + var2 + "---";
            }
         } else {
            Matcher var10;
            if (var1.contains("iPad") && (var10 = IPAD.matcher(var1)).find()) {
               String var14 = var10.group(1);
               var3 = var10.group(2);
               var3 = var14 + "." + (var3 == null ? "" : var3);
               String var11 = var10.group(3);
               var3 = var3 + " U3iPad " + var2 + " " + var11 + "---";
            }
         }

         return var3 == null ? var1 : var3 + var1;
      }
   }
}
