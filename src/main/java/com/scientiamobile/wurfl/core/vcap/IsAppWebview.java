package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import java.io.Serializable;
import java.util.regex.Matcher;

public class IsAppWebview extends AbstractVirtualCapabilityEvaluator implements VirtualCapabilityEvaluator, Serializable {
   private static final long serialVersionUID = 165298984131843694L;

   @Override
   public String eval(Device device, WURFLRequest request) {
      String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();

      for(String keyword : NON_APP_BROWSER_KEYWORDS) {
         if (userAgent.contains(keyword)) {
            return "false";
         }
      }

      String deviceOs = device.getCapability("device_os");
      if ("Android".equals(deviceOs) && userAgent.contains("; wv) ")) {
         return "true";
      } else if ("Android".equals(deviceOs) && userAgent.contains("Chrome") && !userAgent.contains("Version")) {
         return "false";
      } else if (!isIosNonSafari(deviceOs, userAgent) && !isMacOsNonSafari(device, userAgent, request)) {
         String requestedWith = request.getHeader("X-Requested-With");
         if (isRequestedWithAppPackage("Android", deviceOs, requestedWith)) {
            return "true";
         } else if (ANDROID_BROWSER_PACKAGE_NAMES.contains(requestedWith)) {
            return "false";
         } else {
            if (ANDROID_WEBKIT_KHTML_PATTERN.matcher(request.getDeviceUserAgent()).find()) {
               Matcher androidUaPrefixMatcher = IsApp.ANDROID_UA_PREFIX_PATTERN.matcher(userAgent);
               Matcher androidSafariSuffixMatcher = IsApp.ANDROID_SAFARI_SUFFIX_PATTERN.matcher(userAgent);
               if (androidUaPrefixMatcher.find() || androidSafariSuffixMatcher.find()) {
                  String chromeMajorVersion;
                  Matcher chromeVersionMatcher;
                   chromeVersionMatcher = CHROME_MAJOR_VERSION_PATTERN.matcher(userAgent);
                   if (chromeVersionMatcher.find()) {
                      chromeMajorVersion = chromeVersionMatcher.group(1).replaceFirst("^0+(?!$)", "");
                      if (chromeMajorVersion.length() > 0 && chromeMajorVersion.length() <= 2 && chromeMajorVersion.charAt(0) < '3') {
                         return "false";
                      }
                   }

                  return "true";
               }

               if (ANDROID_LEGACY_VERSION_PATTERN.matcher(userAgent).find() && !ANDROID_LEGACY_SAFARI_UA_PATTERN.matcher(userAgent).matches()) {
                  return "true";
               }
            }

            return "false";
         }
      } else {
         return "true";
      }
   }

   @Override
   public String getHandledVirtualCapabilityName() {
      return "is_app_webview";
   }
}
