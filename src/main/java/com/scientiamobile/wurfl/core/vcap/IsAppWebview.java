package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.regex.Matcher;

public class IsAppWebview extends AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 165298984131843694L;

   public String eval(Device var1, WURFLRequest var2) {
      String var3 = var2.isUrlEncoded() ? var2.getCleanedDeviceUserAgent() : var2.getOriginalUserAgent();

      for(Object keywordObj : h) {
         String keyword = (String)keywordObj;
         if (var3.contains(keyword)) {
            return "false";
         }
      }

      String var11 = var1.getCapability("device_os");
      String var12 = var1.getCapability("device_os");
      if ("Android".equals(var12) && var3.contains("; wv) ")) {
         return "true";
      } else if ("Android".equals(var11) && var3.contains("Chrome") && !var3.contains("Version")) {
         return "false";
      } else if (!a(var11, var3) && !a(var1, var3, var2)) {
         var12 = var2.getHeader("X-Requested-With");
         if (a("Android", var11, var12)) {
            return "true";
         } else if (g.contains(var12)) {
            return "false";
         } else {
            if (c.matcher(var2.getDeviceUserAgent()).find()) {
               Matcher var7 = IsApp.a.matcher(var3);
               Matcher var10 = IsApp.b.matcher(var3);
               if (var7.find() || var10.find()) {
                  String var9;
                  if ((var7 = e.matcher(var3)).find() && (var9 = var7.group(1).replaceFirst("^0+(?!$)", "")).length() > 0 && var9.length() <= 2 && var9.charAt(0) < '3') {
                     return "false";
                  }

                  return "true";
               }

               if (f.matcher(var3).find() && !d.matcher(var3).matches()) {
                  return "true";
               }
            }

            return "false";
         }
      } else {
         return "true";
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_app_webview";
   }
}
