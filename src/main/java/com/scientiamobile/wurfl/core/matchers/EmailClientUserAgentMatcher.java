package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class EmailClientUserAgentMatcher extends MatcherBase {
   private static final String MOZILLA_THUNDERBIRD = "mozilla_thunderbird";
   private static final String MS_OUTLOOK = "ms_outlook";
   private static final Pattern MICROSOFT_OUTLOOK_PATTERN = Pattern.compile("Microsoft Outlook ([0-9]+).");
   private static final Pattern MAC_OUTLOOK_PATTERN = Pattern.compile("^MacOutlook ([0-9]+).");
   private static final String[] f;
   private static final List<String> g;
   public static final String[] EMAIL_CLIENTS;

   public EmailClientUserAgentMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public boolean canHandle(WURFLRequest var1) {
      return var1._internalIsEmailClient() && !StringMatchUtils.containsAnyOf(var1.getDeviceUserAgent(), "Office", "office") || var1.getDeviceUserAgent().contains("Spark/") && var1.getDeviceUserAgent().contains("CFNetwork/");
   }

   public String getMatcherName() {
      return "EmailClientMatcher";
   }

   public String getBucketMatcherName() {
      return "EmailClient";
   }

   protected final String risMatch(String var1) {
      if (var1.contains("Thunderbird")) {
         var1 = var1.substring(var1.indexOf("Thunderbird"));
      }

      if (MICROSOFT_OUTLOOK_PATTERN.matcher(var1).find()) {
         var1 = var1.substring(var1.indexOf("Microsoft Outlook"));
      }

      if (MAC_OUTLOOK_PATTERN.matcher(var1).find()) {
         var1 = var1.substring(var1.indexOf("MacOutlook"));
      }

      int var2;
      return (var2 = var1.indexOf(".")) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : "generic";
   }

   protected final Set<String> getRequiredDeviceIds() {
      return new HashSet<>(g);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var2;
      if ((var2 = var1.getDeviceUserAgent()).contains("Thunderbird")) {
         return MOZILLA_THUNDERBIRD;
      } else if (var2.contains("Outlook-Express")) {
         return "outlook_express";
      } else if (var2.contains("MacOutlook")) {
         return "mac_outlook";
      } else if (var2.contains("Outlook") && var2.contains("CFNetwork")) {
         return "ms_outlook_ios_ver1";
      } else if (var2.contains("Outlook")) {
         return MS_OUTLOOK;
      } else if (var2.contains("Lotus-Notes")) {
         return "lotus_notes_ver1";
      } else if (var2.contains("Eudora/")) {
         return "eudora_ver1";
      } else if (var2.contains("Evolution/")) {
         return "evolution_ver1";
      } else if (var2.contains("PocoMail/")) {
         return "pocomail_ver1";
      } else if (var2.contains("The Bat!")) {
         return "thebat_ver1";
      } else if (var2.contains("Postbox/")) {
         return "postbox_ver1";
      } else if (var2.contains("Airmail") && var2.contains("CFNetwork") && !var2.contains("x86_64")) {
         return "airmail_ios_ver1";
      } else if (var2.contains("Airmail")) {
         return "airmail_ver1";
      } else if (var2.contains("Spark/") && var2.contains("CFNetwork")) {
         return "spark_ios_ver1";
      } else {
         return StringMatchUtils.containsAnyOf(var2, f) ? "generic_mobile_email_client" : "generic_email_client";
      }
   }

   static {
      List<String> var10000 = UserAgentUtils.getMobileKeywords();
      f = var10000.toArray(new String[0]);
      (g = new ArrayList<>()).add(MOZILLA_THUNDERBIRD);
      g.add(MS_OUTLOOK);
      g.add("mac_outlook");
      g.add("ms_outlook_ios_ver1");
      g.add("outlook_express");
      g.add("generic_email_client");
      g.add("generic_mobile_email_client");
      g.add("lotus_notes_ver1");
      g.add("eudora_ver1");
      g.add("evolution_ver1");
      g.add("pocomail_ver1");
      g.add("thebat_ver1");
      g.add("postbox_ver1");
      g.add("airmail_ver1");
      g.add("airmail_ios_ver1");
      g.add("spark_ios_ver1");
      EMAIL_CLIENTS = new String[]{"Thunderbird", "Outlook", "Lotus-Notes", "Eudora/", "Evolution/", "PocoMail/", "The Bat!", "Postbox/", "Airmail"};
   }
}
