package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EmailClientUserAgentMatcher extends MatcherBase {
   private static final String MOZILLA_THUNDERBIRD = "mozilla_thunderbird";
   private static final String MS_OUTLOOK = "ms_outlook";
   private static final Pattern MICROSOFT_OUTLOOK_PATTERN = Pattern.compile("Microsoft Outlook ([0-9]+).");
   private static final Pattern MAC_OUTLOOK_PATTERN = Pattern.compile("^MacOutlook ([0-9]+).");
   private static final String[] MOBILE_KEYWORDS;
   private static final List<String> REQUIRED_DEVICE_IDS;
   public static final List<String> EMAIL_CLIENTS;

   public EmailClientUserAgentMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
      super(userAgentNormalizer, wurflModel);
   }

   @Override
   public boolean canHandle(WURFLRequest request) {
      String deviceUserAgent = request.getDeviceUserAgent();
      return request._internalIsEmailClient() && !StringMatchUtils.containsAnyOf(deviceUserAgent, "Office", "office") || deviceUserAgent.contains("Spark/") && deviceUserAgent.contains("CFNetwork/");
   }

   @Override
   public String getMatcherName() {
      return "EmailClientMatcher";
   }

   @Override
   public String getBucketMatcherName() {
      return "EmailClient";
   }

   @Override
   protected String risMatch(String userAgent) {
      if (userAgent.contains("Thunderbird")) {
         userAgent = userAgent.substring(userAgent.indexOf("Thunderbird"));
      }

      if (MICROSOFT_OUTLOOK_PATTERN.matcher(userAgent).find()) {
         userAgent = userAgent.substring(userAgent.indexOf("Microsoft Outlook"));
      }

      if (MAC_OUTLOOK_PATTERN.matcher(userAgent).find()) {
         userAgent = userAgent.substring(userAgent.indexOf("MacOutlook"));
      }

      int dotIndex = userAgent.indexOf(".");
      return dotIndex != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, dotIndex) : "generic";
   }

   @Override
   protected Set<String> getRequiredDeviceIds() {
      return new HashSet<>(REQUIRED_DEVICE_IDS);
   }

   @Override
   protected String applyRecoveryMatch(WURFLRequest request) {
      String deviceUserAgent = request.getDeviceUserAgent();
      if (deviceUserAgent.contains("Thunderbird")) {
         return MOZILLA_THUNDERBIRD;
      } else if (deviceUserAgent.contains("Outlook-Express")) {
         return "outlook_express";
      } else if (deviceUserAgent.contains("MacOutlook")) {
         return "mac_outlook";
      } else if (deviceUserAgent.contains("Outlook") && deviceUserAgent.contains("CFNetwork")) {
         return "ms_outlook_ios_ver1";
      } else if (deviceUserAgent.contains("Outlook")) {
         return MS_OUTLOOK;
      } else if (deviceUserAgent.contains("Lotus-Notes")) {
         return "lotus_notes_ver1";
      } else if (deviceUserAgent.contains("Eudora/")) {
         return "eudora_ver1";
      } else if (deviceUserAgent.contains("Evolution/")) {
         return "evolution_ver1";
      } else if (deviceUserAgent.contains("PocoMail/")) {
         return "pocomail_ver1";
      } else if (deviceUserAgent.contains("The Bat!")) {
         return "thebat_ver1";
      } else if (deviceUserAgent.contains("Postbox/")) {
         return "postbox_ver1";
      } else if (deviceUserAgent.contains("Airmail") && deviceUserAgent.contains("CFNetwork") && !deviceUserAgent.contains("x86_64")) {
         return "airmail_ios_ver1";
      } else if (deviceUserAgent.contains("Airmail")) {
         return "airmail_ver1";
      } else if (deviceUserAgent.contains("Spark/") && deviceUserAgent.contains("CFNetwork")) {
         return "spark_ios_ver1";
      } else {
         return StringMatchUtils.containsAnyOf(deviceUserAgent, MOBILE_KEYWORDS) ? "generic_mobile_email_client" : "generic_email_client";
      }
   }

   static {
      List<String> mobileKeywords = UserAgentUtils.getMobileKeywords();
      MOBILE_KEYWORDS = mobileKeywords.toArray(new String[0]);
REQUIRED_DEVICE_IDS = new ArrayList<>();
REQUIRED_DEVICE_IDS.add(MOZILLA_THUNDERBIRD);
      REQUIRED_DEVICE_IDS.add(MS_OUTLOOK);
      REQUIRED_DEVICE_IDS.add("mac_outlook");
      REQUIRED_DEVICE_IDS.add("ms_outlook_ios_ver1");
      REQUIRED_DEVICE_IDS.add("outlook_express");
      REQUIRED_DEVICE_IDS.add("generic_email_client");
      REQUIRED_DEVICE_IDS.add("generic_mobile_email_client");
      REQUIRED_DEVICE_IDS.add("lotus_notes_ver1");
      REQUIRED_DEVICE_IDS.add("eudora_ver1");
      REQUIRED_DEVICE_IDS.add("evolution_ver1");
      REQUIRED_DEVICE_IDS.add("pocomail_ver1");
      REQUIRED_DEVICE_IDS.add("thebat_ver1");
      REQUIRED_DEVICE_IDS.add("postbox_ver1");
      REQUIRED_DEVICE_IDS.add("airmail_ver1");
      REQUIRED_DEVICE_IDS.add("airmail_ios_ver1");
      REQUIRED_DEVICE_IDS.add("spark_ios_ver1");
      EMAIL_CLIENTS = Collections.unmodifiableList(Arrays.asList("Thunderbird", "Outlook", "Lotus-Notes", "Eudora/", "Evolution/", "PocoMail/", "The Bat!", "Postbox/", "Airmail"));
   }
}
