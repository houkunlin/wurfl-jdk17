package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsApp extends a {
   private static final long serialVersionUID = -2020126634302389944L;
   private static final Map j = new HashMap(2);
   private static final Pattern k = Pattern.compile("iP(hone|od|ad)[\\d],[\\d]");
   private static final Pattern l = Pattern.compile("com(?:\\.[a-z]+){2,}");
   private static final Pattern m = Pattern.compile("net(?:\\.[a-z]+){2,}");
   static final Pattern a = Pattern.compile("^.+Mozilla/5.0 \\(Linux; Android ");
   static final Pattern b = Pattern.compile(" (?:Mobile )?Safari/[\\d\\.+]+[^\\d\\.+]+");

   public String eval(Device var1, WURFLRequest var2) {
      String var3;
      if (StringMatchUtils.containsAnyOf(var3 = var2.isUrlEncoded() ? var2.getCleanedDeviceUserAgent() : var2.getOriginalUserAgent(), (String[])h.toArray(new String[h.size()]))) {
         return "false";
      } else {
         String var4;
         String var5 = var4 = var1.getCapability("device_os");
         if ("Android".equals(var5) && var3.contains("; wv) ")) {
            return "true";
         } else if (!a(var4, var3) && !a(var1, var3, var2)) {
            if (a("Android", var4, var2)) {
               return "true";
            } else {
               if (c.matcher(var3).find()) {
                  Matcher var7 = a.matcher(var3);
                  Matcher var9 = b.matcher(var3);
                  if (var7.find() || var9.find()) {
                     Matcher var13;
                     return (var13 = e.matcher(var3)).find() && a(var13.group(1)) < 30 ? "false" : "true";
                  }
               }

               Iterator var8 = i.iterator();

               while(var8.hasNext()) {
                  String var10;
                  if ((var10 = (String)var8.next()).startsWith("#")) {
                     if (((Pattern)j.get(var10)).matcher(var3).find()) {
                        return "true";
                     }
                  } else {
                     int var11 = var10.length();
                     if (var10.startsWith("^")) {
                        if (var3.startsWith(var10.substring(1))) {
                           return "true";
                        }
                     } else if (var10.charAt(var11 - 1) == '$') {
                        --var11;
                        if (var3.indexOf(var10.substring(0, var11)) == var3.length() - var11) {
                           return "true";
                        }
                     } else if (var3.contains(var10)) {
                        return "true";
                     }
                  }
               }

               return "false";
            }
         } else {
            return "true";
         }
      }
   }

   private static int a(String var0) {
      try {
         return Integer.parseInt(var0);
      } catch (NumberFormatException var1) {
         return -1;
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_app";
   }

   static {
      j.put("#iP(hone|od|ad)[\\d],[\\d]", k);
      j.put("#com(?:\\.[a-z]+){2,}", l);
      j.put("#net(?:\\.[a-z]+){2,}", m);
   }
}
