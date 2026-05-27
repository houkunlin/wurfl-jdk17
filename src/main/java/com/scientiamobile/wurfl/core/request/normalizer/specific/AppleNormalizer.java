package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppleNormalizer implements UserAgentNormalizer {
   private static final Pattern IOS_APP_UA_PATTERN = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
   private static final Pattern SERVER_BAG_PATTERN = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
   private static final Pattern IOS_DEVICE_MODEL_VERSION_PATTERN = Pattern.compile("^i(?:Phone|Pad|Pod)\\d+?,\\d+?/([\\d\\.]+)");
   private static final Pattern IOS_DEVICE_MODEL_IOS_VERSION_PATTERN = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
   private static final Pattern IOS_CLIENT_SDK_PATTERN = Pattern.compile("^iOSClientSDK/\\d+\\.+[0-9\\.]+ +?\\((Mozilla.+)\\)$");
   private static final Pattern CPU_IOS_PATTERN = Pattern.compile("CPU iOS \\d+?\\.\\d+?");
   private static final Pattern CPU_OS_LIKE_PATTERN = Pattern.compile("(CPU(?: iPhone)? OS [\\d\\.]+ like)");

   private static Matcher findMatcher(String userAgent, Pattern pattern) {
      if (userAgent != null && pattern != null) {
         Matcher matcher = pattern.matcher(userAgent);
         return matcher.find() ? matcher : null;
      } else {
         return null;
      }
   }

   @Override
   public String normalize(String userAgent) {
      Matcher versionMatcher;
      if ((versionMatcher = findMatcher(userAgent, IOS_APP_UA_PATTERN)) == null) {
         versionMatcher = findMatcher(userAgent, SERVER_BAG_PATTERN);
      }

      if (versionMatcher == null) {
         versionMatcher = findMatcher(userAgent, IOS_DEVICE_MODEL_VERSION_PATTERN);
      }

      if (versionMatcher == null) {
         versionMatcher = findMatcher(userAgent, IOS_DEVICE_MODEL_IOS_VERSION_PATTERN);
      }

      if (versionMatcher != null) {
         String iosVersion = versionMatcher.group(1).replace(".", "_");
         StringBuilder normalizedUserAgent = new StringBuilder(256);
         if (userAgent.contains("iPad")) {
            return normalizedUserAgent.append("Mozilla/5.0 (iPad; CPU OS ").append(iosVersion).append(" like Mac OS X) AppleWebKit/538.39.2 (KHTML, like Gecko) Version/7.0 Mobile/12A4297e Safari/9537.53 ").append(userAgent).toString();
         } else if (userAgent.contains("iPod touch")) {
            return normalizedUserAgent.append("Mozilla/5.0 (iPod touch; CPU iPhone OS ").append(iosVersion).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(userAgent).toString();
         } else {
            return userAgent.contains("iPod") ? normalizedUserAgent.append("Mozilla/5.0 (iPod; CPU iPhone OS ").append(iosVersion).append(" like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ").append(userAgent).toString() : normalizedUserAgent.append("Mozilla/5.0 (iPhone; CPU iPhone OS ").append(iosVersion).append(" like Mac OS X) AppleWebKit/601.1.10 (KHTML, like Gecko) Version/8.0 Mobile/12E155 Safari/600.1.4 ").append(userAgent).toString();
         }
      } else {
         if (userAgent != null) {
            Matcher iosClientSdkMatcher = IOS_CLIENT_SDK_PATTERN.matcher(userAgent);
            if (iosClientSdkMatcher.matches()) {
               return iosClientSdkMatcher.group(1);
            }
         }

         String rewrittenUserAgent;
         if (userAgent != null && findMatcher(userAgent, CPU_IOS_PATTERN) != null && (versionMatcher = findMatcher(rewrittenUserAgent = userAgent.contains("iPad") ? userAgent.replace("CPU iOS", "CPU OS") : userAgent.replace("CPU iOS", "CPU iPhone OS"), CPU_OS_LIKE_PATTERN)) != null) {
            String cpuOsLike = versionMatcher.group(1).replace(".", "_");
            return rewrittenUserAgent.replace(" U;", "").replaceAll("CPU(?: iPhone)? OS ([\\d\\.]+) like", cpuOsLike);
         } else {
            return userAgent;
         }
      }
   }
}
