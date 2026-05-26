package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

final class OperaMatcher extends MatcherBase {
   private static final String OPERA_GENERIC = "opera";
   private static final Pattern OPERA_VERSION = Pattern.compile("Opera[ /]?(\\d+\\.\\d+)");
   private static final Map<String, String> MAJOR_VERSION_TO_DEVICE_ID;

   public OperaMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(MAJOR_VERSION_TO_DEVICE_ID.values());
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Opera", "OPR/");
   }

   protected final String risMatch(String var1) {
      int var2 = StringMatchUtils.indexOf(var1, "Opera");
      var2 = StringMatchUtils.indexOfOrLength(var1, ".", var2);
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      Matcher var2;
      String var3;
      String[] var4;
      String var5;
      return (var2 = OPERA_VERSION.matcher(var1.getNormalizedDeviceUserAgent())).find() && StringUtils.isNotEmpty(var3 = var2.group(1)) && ArrayUtils.isNotEmpty(var4 = var3.split("\\.")) && StringUtils.isNotEmpty(var5 = MAJOR_VERSION_TO_DEVICE_ID.get(var4[0])) ? var5 : OPERA_GENERIC;
   }

   public final String getMatcherName() {
      return "OperaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Opera";
   }

   static {
      (MAJOR_VERSION_TO_DEVICE_ID = new HashMap<>()).put("", OPERA_GENERIC);
      MAJOR_VERSION_TO_DEVICE_ID.put("7", "opera_7");
      MAJOR_VERSION_TO_DEVICE_ID.put("8", "opera_8");
      MAJOR_VERSION_TO_DEVICE_ID.put("9", "opera_9");
      MAJOR_VERSION_TO_DEVICE_ID.put("10", "opera_10");
      MAJOR_VERSION_TO_DEVICE_ID.put("11", "opera_11");
      MAJOR_VERSION_TO_DEVICE_ID.put("12", "opera_12");
      MAJOR_VERSION_TO_DEVICE_ID.put("15", "opera_15");
      MAJOR_VERSION_TO_DEVICE_ID.put("16", "opera_16");
      MAJOR_VERSION_TO_DEVICE_ID.put("17", "opera_17");
      MAJOR_VERSION_TO_DEVICE_ID.put("18", "opera_18");
      MAJOR_VERSION_TO_DEVICE_ID.put("19", "opera_19");
      MAJOR_VERSION_TO_DEVICE_ID.put("20", "opera_20");
      MAJOR_VERSION_TO_DEVICE_ID.put("21", "opera_21");
      MAJOR_VERSION_TO_DEVICE_ID.put("22", "opera_22");
      MAJOR_VERSION_TO_DEVICE_ID.put("23", "opera_23");
      MAJOR_VERSION_TO_DEVICE_ID.put("24", "opera_24");
      MAJOR_VERSION_TO_DEVICE_ID.put("25", "opera_25");
      MAJOR_VERSION_TO_DEVICE_ID.put("26", "opera_26");
      MAJOR_VERSION_TO_DEVICE_ID.put("27", "opera_27");
      MAJOR_VERSION_TO_DEVICE_ID.put("28", "opera_28");
      MAJOR_VERSION_TO_DEVICE_ID.put("29", "opera_29");
      MAJOR_VERSION_TO_DEVICE_ID.put("30", "opera_30");
      MAJOR_VERSION_TO_DEVICE_ID.put("31", "opera_31");
      MAJOR_VERSION_TO_DEVICE_ID.put("32", "opera_32");
      MAJOR_VERSION_TO_DEVICE_ID.put("33", "opera_33");
      MAJOR_VERSION_TO_DEVICE_ID.put("34", "opera_34");
      MAJOR_VERSION_TO_DEVICE_ID.put("35", "opera_35");
      MAJOR_VERSION_TO_DEVICE_ID.put("36", "opera_36");
      MAJOR_VERSION_TO_DEVICE_ID.put("37", "opera_37");
      MAJOR_VERSION_TO_DEVICE_ID.put("38", "opera_38");
      MAJOR_VERSION_TO_DEVICE_ID.put("39", "opera_39");
      MAJOR_VERSION_TO_DEVICE_ID.put("40", "opera_40");
      MAJOR_VERSION_TO_DEVICE_ID.put("41", "opera_41");
      MAJOR_VERSION_TO_DEVICE_ID.put("42", "opera_42");
      MAJOR_VERSION_TO_DEVICE_ID.put("43", "opera_43");
   }
}
