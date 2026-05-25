package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MSIEMatcher extends a {
   private static final Pattern MSIE = Pattern.compile("^Mozilla/[45]\\.0 \\(compatible; MSIE (\\d+)\\.(\\d+)(?:[\\da-z]+)?;");
   private static final Pattern TRIDENT_RV = Pattern.compile("^Mozilla/5\\.0 \\(.+?Trident.+?; rv:(\\d\\d)\\.(\\d+)\\)");
   private static final Pattern EDGE = Pattern.compile("^Mozilla/5\\.0 \\(Windows NT.+? Edge/(\\d+)\\.(\\d+)");
   private static final Pattern UNIMPORTANT_TOKENS = Pattern.compile("( \\.NET CLR [\\d\\.]+;?| Media Center PC [\\d\\.]+;?| OfficeLive[a-zA-Z0-9\\.\\d]+;?| InfoPath[\\.\\d]+;?)");
   private static final Map DEVICE_BY_MAJOR_VERSION;

   public MSIEMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).addAll(DEVICE_BY_MAJOR_VERSION.values());
      var1.add("generic");
      var1.add("generic_web_browser");
      var1.add("msie_5_5");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      if (!var1._internalIsMobileBrowser() && var2.startsWith("Mozilla") && !StringMatchUtils.containsAnyOf(var2, "Opera", "armv", "MOTO", "BREW")) {
         return StringMatchUtils.containsAllOf(var2, "Trident", "rv:") || StringMatchUtils.containsAnyOf(var2, "MSIE", " Edge/");
      } else {
         return false;
      }
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      String var2 = UNIMPORTANT_TOKENS.matcher(var1.getNormalizedDeviceUserAgent()).replaceFirst("");
      Matcher[] var8 = new Matcher[]{EDGE.matcher(var2), TRIDENT_RV.matcher(var2), MSIE.matcher(var2)};
      boolean var3 = false;
      Matcher var4 = null;

      for(int var5 = 0; var5 < 3; ++var5) {
         Matcher var6;
         if (var3 = (var6 = var8[var5]).find()) {
            var4 = var6;
            break;
         }
      }

      if (var3) {
         String var9 = var4.group(1);
         String var10 = var4.group(2);
         Integer var11 = -1;

         try {
            var11 = Integer.parseInt(var10);
         } catch (NumberFormatException var7) {
         }

         if ("5".equals(var9) && (new Integer(5)).equals(var11)) {
            return "msie_5_5";
         }

         String var12;
         if ((var12 = (String)DEVICE_BY_MAJOR_VERSION.get(var9)) != null) {
            return var12;
         }
      }

      return super.applyConclusiveMatch(var1);
   }

   protected final String risMatch(String var1) {
      String var3;
      int var2 = StringMatchUtils.indexOfOrLength(var3 = UNIMPORTANT_TOKENS.matcher(var1).replaceFirst(""), "Trident");
      return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var3, var2);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return StringMatchUtils.containsAnyOf(UNIMPORTANT_TOKENS.matcher(var1.getNormalizedDeviceUserAgent()).replaceFirst(""), "SLCC1", "Media Center PC", ".NET CLR", "OfficeLiveConnector") ? "generic_web_browser" : "generic";
   }

   public final String getMatcherName() {
      return "MSIEMatcher";
   }

   public final String getBucketMatcherName() {
      return "MSIE";
   }

   static {
      (DEVICE_BY_MAJOR_VERSION = new HashMap()).put("0", "msie");
      DEVICE_BY_MAJOR_VERSION.put("4", "msie_4");
      DEVICE_BY_MAJOR_VERSION.put("5", "msie_5");
      DEVICE_BY_MAJOR_VERSION.put("6", "msie_6");
      DEVICE_BY_MAJOR_VERSION.put("7", "msie_7");
      DEVICE_BY_MAJOR_VERSION.put("8", "msie_8");
      DEVICE_BY_MAJOR_VERSION.put("9", "msie_9");
      DEVICE_BY_MAJOR_VERSION.put("10", "msie_10");
      DEVICE_BY_MAJOR_VERSION.put("11", "msie_11");
      DEVICE_BY_MAJOR_VERSION.put("12", "msie_12");
      DEVICE_BY_MAJOR_VERSION.put("13", "edge_13");
      DEVICE_BY_MAJOR_VERSION.put("14", "edge_14");
      DEVICE_BY_MAJOR_VERSION.put("15", "edge_15");
      DEVICE_BY_MAJOR_VERSION.put("16", "edge_16");
      DEVICE_BY_MAJOR_VERSION.put("17", "edge_17");
   }
}

