package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

final class BlackBerryMatcher extends AbstractMatcher {
   private static final String BLACKBERRY_GENERIC_VER10 = "blackberry_generic_ver10";
   private static final String BLACKBERRY_GENERIC_VER10_TABLET = "blackberry_generic_ver10_tablet";
   private static final String RIM_PLAYBOOK_VER1 = "rim_playbook_ver1";
   private static final Map OS_VERSION_TO_DEVICE_ID;
   private static final Pattern BLACKBERRY_OS_VERSION;

   public BlackBerryMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(OS_VERSION_TO_DEVICE_ID.values());
      var1.add("generic_mobile");
      var1.add(RIM_PLAYBOOK_VER1);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && (StringUtils.containsIgnoreCase(var2, "blackberry") || StringMatchUtils.containsAnyOf(var2, "(BB10;", "(PlayBook"));
   }

   protected final String risMatch(String var1) {
      int var2;
      if (var1.contains("BB10")) {
         var2 = StringMatchUtils.indexOfOrLength(var1, ")");
      } else if (var1.startsWith("Mozilla/4")) {
         var2 = StringMatchUtils.secondSlash(var1);
      } else if (var1.startsWith("Mozilla/5")) {
         var2 = StringMatchUtils.ordinalIndexOfOrNotFound(var1, ";", 3);
      } else if (var1.contains("PlayBook")) {
         var2 = StringMatchUtils.firstCloseParenthesis(var1);
      } else {
         var2 = StringMatchUtils.firstSlash(var1);
      }

      return var2 != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var4;
      String var2 = var4 = var1.getNormalizedDeviceUserAgent();
      Matcher var6;
      var2 = (var6 = BLACKBERRY_OS_VERSION.matcher(var2)).find() ? var6.group(1) : null;
      if (var4.contains("BB10")) {
         return var4.contains("Mobile") ? BLACKBERRY_GENERIC_VER10 : BLACKBERRY_GENERIC_VER10_TABLET;
      } else if (var4.contains("PlayBook")) {
         return RIM_PLAYBOOK_VER1;
      } else {
         if (var2 != null) {
            for(Map.Entry var3 : OS_VERSION_TO_DEVICE_ID.entrySet()) {
               if (var2.contains((CharSequence)var3.getKey())) {
                  return (String)var3.getValue();
               }
            }
         }

         return "generic";
      }
   }

   public final String getMatcherName() {
      return "BlackBerryMatcher";
   }

   public final String getBucketMatcherName() {
      return "BlackBerry";
   }

   static {
      (OS_VERSION_TO_DEVICE_ID = new LinkedHashMap()).put("2.", "blackberry_generic_ver2");
      OS_VERSION_TO_DEVICE_ID.put("3.2", "blackberry_generic_ver3_sub2");
      OS_VERSION_TO_DEVICE_ID.put("3.3", "blackberry_generic_ver3_sub30");
      OS_VERSION_TO_DEVICE_ID.put("3.5", "blackberry_generic_ver3_sub50");
      OS_VERSION_TO_DEVICE_ID.put("3.6", "blackberry_generic_ver3_sub60");
      OS_VERSION_TO_DEVICE_ID.put("3.7", "blackberry_generic_ver3_sub70");
      OS_VERSION_TO_DEVICE_ID.put("4.1", "blackberry_generic_ver4_sub10");
      OS_VERSION_TO_DEVICE_ID.put("4.2", "blackberry_generic_ver4_sub20");
      OS_VERSION_TO_DEVICE_ID.put("4.3", "blackberry_generic_ver4_sub30");
      OS_VERSION_TO_DEVICE_ID.put("4.5", "blackberry_generic_ver4_sub50");
      OS_VERSION_TO_DEVICE_ID.put("4.6", "blackberry_generic_ver4_sub60");
      OS_VERSION_TO_DEVICE_ID.put("4.7", "blackberry_generic_ver4_sub70");
      OS_VERSION_TO_DEVICE_ID.put("4.", "blackberry_generic_ver4");
      OS_VERSION_TO_DEVICE_ID.put("5.", "blackberry_generic_ver5");
      OS_VERSION_TO_DEVICE_ID.put("6.", "blackberry_generic_ver6");
      OS_VERSION_TO_DEVICE_ID.put("10", BLACKBERRY_GENERIC_VER10);
      OS_VERSION_TO_DEVICE_ID.put("10t", BLACKBERRY_GENERIC_VER10_TABLET);
      BLACKBERRY_OS_VERSION = Pattern.compile("BlackBerry[^/\\s]+/(\\d\\.\\d)");
   }
}
