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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

final class an extends a {
   private static String b = "opera";
   private static final Pattern c = Pattern.compile("Opera[ /]?(\\d+\\.\\d+)");
   private static final Map d;

   public an(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(d.values());
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(var1.getCleanedDeviceUserAgent(), "Opera", "OPR/");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOf(var1, "Opera");
      var2 = StringMatchUtils.indexOfOrLength(var1, ".", var2);
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      Matcher var2;
      String var3;
      String[] var4;
      String var5;
      return (var2 = c.matcher(var1.getNormalizedDeviceUserAgent())).find() && StringUtils.isNotEmpty(var3 = var2.group(1)) && ArrayUtils.isNotEmpty(var4 = var3.split("\\.")) && StringUtils.isNotEmpty(var5 = (String)d.get(var4[0])) ? var5 : b;
   }

   public final String getMatcherName() {
      return "OperaMatcher";
   }

   public final String getBucketMatcherName() {
      return "Opera";
   }

   static {
      (d = new HashMap()).put("", b);
      d.put("7", "opera_7");
      d.put("8", "opera_8");
      d.put("9", "opera_9");
      d.put("10", "opera_10");
      d.put("11", "opera_11");
      d.put("12", "opera_12");
      d.put("15", "opera_15");
      d.put("16", "opera_16");
      d.put("17", "opera_17");
      d.put("18", "opera_18");
      d.put("19", "opera_19");
      d.put("20", "opera_20");
      d.put("21", "opera_21");
      d.put("22", "opera_22");
      d.put("23", "opera_23");
      d.put("24", "opera_24");
      d.put("25", "opera_25");
      d.put("26", "opera_26");
      d.put("27", "opera_27");
      d.put("28", "opera_28");
      d.put("29", "opera_29");
      d.put("30", "opera_30");
      d.put("31", "opera_31");
      d.put("32", "opera_32");
      d.put("33", "opera_33");
      d.put("34", "opera_34");
      d.put("35", "opera_35");
      d.put("36", "opera_36");
      d.put("37", "opera_37");
      d.put("38", "opera_38");
      d.put("39", "opera_39");
      d.put("40", "opera_40");
      d.put("41", "opera_41");
      d.put("42", "opera_42");
      d.put("43", "opera_43");
   }
}
