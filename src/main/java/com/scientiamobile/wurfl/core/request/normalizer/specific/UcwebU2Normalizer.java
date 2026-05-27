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
   private static final Pattern SEMICOLON_WITHOUT_SPACE_PATTERN = Pattern.compile(";(?! )");
   private static final Pattern NOKIA_RM_MODEL_PATTERN = Pattern.compile("(NOKIA.RM-.+?)_.*");

   @Override
   public String normalize(String userAgent) {
      String ucBrowserVersion;
      ucBrowserVersion = UserAgentUtils.getUcBrowserVersion(userAgent, true);
      if (ucBrowserVersion == null) {
         return userAgent;
      } else {
         String normalizedPrefix = null;
         if (userAgent.contains("Adr")) {
            String model = UserAgentUtils.getUcAndroidModel(userAgent, false);
            String androidVersion = UserAgentUtils.getUcAndroidVersion(userAgent, false);
            if (model != null && androidVersion != null) {
               normalizedPrefix = androidVersion + " U2Android " + ucBrowserVersion + " " + model + "---";
            }
         } else if (userAgent.contains("iPh OS")) {
            Matcher matcher;
            matcher = IPHONE.matcher(userAgent);
      if (matcher.find()) {
               String iosVersion = matcher.group(1) + "." + matcher.group(2);
               String iphoneDeviceVersion = matcher.group(3) + "." + matcher.group(4);
               normalizedPrefix = iosVersion + " U2iPhone " + ucBrowserVersion + " " + iphoneDeviceVersion + "---";
            }
         } else if (userAgent.contains("wds")) {
            String fixedUserAgent = SEMICOLON_WITHOUT_SPACE_PATTERN.matcher(userAgent).replaceAll("; ");
            Matcher matcher;
            matcher = WINDOWS_PHONE.matcher(fixedUserAgent);
      if (matcher.find()) {
               String windowsPhoneVersion = matcher.group(1) + "." + matcher.group(2);
               String modelName = (matcher.group(3) + "." + matcher.group(4)).replace("_blocked", "");
               modelName = NOKIA_RM_MODEL_PATTERN.matcher(modelName).replaceFirst("$1");
               normalizedPrefix = windowsPhoneVersion + " U2WindowsPhone " + ucBrowserVersion + " " + modelName + "---";
            }
         } else if (userAgent.contains("Symbian")) {
            Matcher matcher;
            matcher = SYMBIAN.matcher(userAgent);
      if (matcher.find()) {
               String symbianVersion = "S60 V" + matcher.group(1);
               String modelName = matcher.group(2);
               normalizedPrefix = symbianVersion + " U2Symbian " + ucBrowserVersion + " " + modelName + "---";
            }
         } else {
            Matcher matcher;
            if (userAgent.contains("Java") && (matcher = JAVA.matcher(userAgent)).find()) {
               String modelName = matcher.group(1);
               normalizedPrefix = "Java U2JavaApp " + ucBrowserVersion + " " + modelName + "---";
            }
         }

         return normalizedPrefix == null ? userAgent : normalizedPrefix + userAgent;
      }
   }
}
