package com.scientiamobile.wurfl.core.vcap;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsApp extends AbstractVirtualCapabilityEvaluator {
   private static final long serialVersionUID = -2020126634302389944L;
   private static final Map<String, Pattern> HASH_APP_INDICATOR_PATTERNS = new HashMap<>(3);
   private static final Pattern IOS_DEVICE_SIGNATURE_PATTERN = Pattern.compile("iP(hone|od|ad)[\\d],[\\d]");
   private static final Pattern JAVA_PACKAGE_SIGNATURE_PATTERN = Pattern.compile("com(?:\\.[a-z]+){2,}");
   private static final Pattern DOT_NET_PACKAGE_SIGNATURE_PATTERN = Pattern.compile("net(?:\\.[a-z]+){2,}");
   static final Pattern ANDROID_UA_PREFIX_PATTERN = Pattern.compile("^.+Mozilla/5.0 \\(Linux; Android ");
   static final Pattern ANDROID_SAFARI_SUFFIX_PATTERN = Pattern.compile(" (?:Mobile )?Safari/[\\d\\.+]+[^\\d\\.+]+");

   public String eval(Device device, WURFLRequest request) {
      String userAgent = request.isUrlEncoded() ? request.getCleanedDeviceUserAgent() : request.getOriginalUserAgent();
      if (StringMatchUtils.containsAnyOf(userAgent, NON_APP_BROWSER_KEYWORDS.toArray(new String[0]))) {
         return "false";
      } else {
         String deviceOs = device.getCapability("device_os");
         if ("Android".equals(deviceOs) && userAgent.contains("; wv) ")) {
            return "true";
         } else if (!isIosNonSafari(deviceOs, userAgent) && !isMacOsNonSafari(device, userAgent, request)) {
            if (isRequestedWithAppPackage("Android", deviceOs, request)) {
               return "true";
            } else {
               if (ANDROID_WEBKIT_KHTML_PATTERN.matcher(userAgent).find()) {
                  Matcher androidUaPrefixMatcher = ANDROID_UA_PREFIX_PATTERN.matcher(userAgent);
                  Matcher androidSafariSuffixMatcher = ANDROID_SAFARI_SUFFIX_PATTERN.matcher(userAgent);
                  if (androidUaPrefixMatcher.find() || androidSafariSuffixMatcher.find()) {
                     Matcher chromeVersionMatcher;
                     return (chromeVersionMatcher = CHROME_MAJOR_VERSION_PATTERN.matcher(userAgent)).find() && parseIntOrMinusOne(chromeVersionMatcher.group(1)) < 30 ? "false" : "true";
                  }
               }

               for(String indicator : APP_INDICATOR_PATTERNS) {
                  if (indicator.startsWith("#")) {
                     if (HASH_APP_INDICATOR_PATTERNS.get(indicator).matcher(userAgent).find()) {
                        return "true";
                     }
                  } else {
                     int indicatorLength = indicator.length();
                     if (indicator.startsWith("^")) {
                        if (userAgent.startsWith(indicator.substring(1))) {
                           return "true";
                        }
                     } else if (indicator.charAt(indicatorLength - 1) == '$') {
                        --indicatorLength;
                        if (userAgent.indexOf(indicator.substring(0, indicatorLength)) == userAgent.length() - indicatorLength) {
                           return "true";
                        }
                     } else if (userAgent.contains(indicator)) {
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

   private static int parseIntOrMinusOne(String value) {
      try {
         return Integer.parseInt(value);
      } catch (NumberFormatException var1) {
         return -1;
      }
   }

   public String getHandledVirtualCapabilityName() {
      return "is_app";
   }

   static {
      HASH_APP_INDICATOR_PATTERNS.put("#iP(hone|od|ad)[\\d],[\\d]", IOS_DEVICE_SIGNATURE_PATTERN);
      HASH_APP_INDICATOR_PATTERNS.put("#com(?:\\.[a-z]+){2,}", JAVA_PACKAGE_SIGNATURE_PATTERN);
      HASH_APP_INDICATOR_PATTERNS.put("#net(?:\\.[a-z]+){2,}", DOT_NET_PACKAGE_SIGNATURE_PATTERN);
   }
}
