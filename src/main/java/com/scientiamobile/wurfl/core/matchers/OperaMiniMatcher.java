package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

final class OperaMiniMatcher extends MatcherBase {
   private static final SortedMap<String, String> OPERA_MINI_VERSION_TO_DEVICE_ID;

   public OperaMiniMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(OPERA_MINI_VERSION_TO_DEVICE_ID.values());
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Opera Mini", "OperaMini", "Opera Mobi", "OperaMobi");
   }

   protected final String risMatch(String var1) {
      int var2;
      if ((var2 = var1.indexOf("---")) >= 0) {
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 3);
      } else if ((var2 = StringMatchUtils.indexOf(var1, "Opera Mini")) >= 0 && (var2 = StringMatchUtils.indexOf(var1, ".", var2)) >= 0) {
         return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2 + 1);
      } else {
         return (var2 = StringMatchUtils.firstSlash(var1)) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : StringMatchUtils.NULL_STRING;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      String var3 = var1.getNormalizedDeviceUserAgent();
      for(String var2 : OPERA_MINI_VERSION_TO_DEVICE_ID.keySet()) {
         if (var3.toLowerCase().contains(var2.toLowerCase())) {
            return OPERA_MINI_VERSION_TO_DEVICE_ID.get(var2);
         }
      }

      return var3.contains("Opera Mobi") ? "generic_opera_mini_version4" : "generic_opera_mini_version1";
   }

   public final String getMatcherName() {
      return "OperaMiniMatcher";
   }

   public final String getBucketMatcherName() {
      return "OperaMini";
   }

   static {
      (OPERA_MINI_VERSION_TO_DEVICE_ID = new TreeMap<>()).put("Opera Mini/1", "generic_opera_mini_version1");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/2", "generic_opera_mini_version2");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/3", "generic_opera_mini_version3");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/4", "generic_opera_mini_version4");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/5", "generic_opera_mini_version5");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/6", "generic_opera_mini_version6");
      OPERA_MINI_VERSION_TO_DEVICE_ID.put("Opera Mini/7", "generic_opera_mini_version7");
   }
}
