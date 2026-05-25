package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UcwebU2Normalizer implements UserAgentNormalizer {
   public static final Pattern IPHONE = Pattern.compile("iPh OS (\\d)_?(\\d)?[ _\\d]?.+; iPh(\\d), ?(\\d)\\) U2");
   public static final Pattern WINDOWS_PHONE = Pattern.compile("^UCWEB.+; wds (\\d+)\\.([\\d]+);.+; ([ A-Za-z0-9_-]+); ([ A-Za-z0-9_-]+)\\) U2");
   public static final Pattern SYMBIAN = Pattern.compile("^UCWEB.+; S60 V(\\d); .+; (.+)\\) U2");
   public static final Pattern JAVA = Pattern.compile("^UCWEB[^\\(]+\\(Java; .+; (.+)\\) U2");
   private static final Pattern a = Pattern.compile(";(?! )");
   private static final Pattern b = Pattern.compile("(NOKIA.RM-.+?)_.*");

   public String normalize(String var1) {
      String var2;
      if ((var2 = UserAgentUtils.getUcBrowserVersion(var1, true)) == null) {
         return var1;
      } else {
         String var3 = null;
         if (var1.contains("Adr")) {
            String var4 = UserAgentUtils.getUcAndroidModel(var1, false);
            String var5 = UserAgentUtils.getUcAndroidVersion(var1, false);
            if (var4 != null && var5 != null) {
               var3 = var5 + " U2Android " + var2 + " " + var4 + "---";
            }
         } else if (var1.contains("iPh OS")) {
            Matcher var10;
            if ((var10 = IPHONE.matcher(var1)).find()) {
               String var16 = var10.group(1) + "." + var10.group(2);
               var3 = var10.group(3) + "." + var10.group(4);
               var3 = var16 + " U2iPhone " + var2 + " " + var3 + "---";
            }
         } else if (var1.contains("wds")) {
            String var11 = a.matcher(var1).replaceAll("; ");
            Matcher var17;
            if ((var17 = WINDOWS_PHONE.matcher(var11)).find()) {
               var3 = var17.group(1) + "." + var17.group(2);
               var11 = (var17.group(3) + "." + var17.group(4)).replace("_blocked", "");
               var11 = b.matcher(var11).replaceFirst("$1");
               var3 = var3 + " U2WindowsPhone " + var2 + " " + var11 + "---";
            }
         } else if (var1.contains("Symbian")) {
            Matcher var14;
            if ((var14 = SYMBIAN.matcher(var1)).find()) {
               String var18 = "S60 V" + var14.group(1);
               var3 = var14.group(2);
               var3 = var18 + " U2Symbian " + var2 + " " + var3 + "---";
            }
         } else {
            Matcher var15;
            if (var1.contains("Java") && (var15 = JAVA.matcher(var1)).find()) {
               String var19 = "Java";
               var3 = var15.group(1);
               var3 = var19 + " U2JavaApp " + var2 + " " + var3 + "---";
            }
         }

         return var3 == null ? var1 : var3 + var1;
      }
   }
}
