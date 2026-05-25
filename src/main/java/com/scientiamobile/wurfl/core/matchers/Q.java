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

final class Q extends a {
   private static final Pattern b = Pattern.compile("^Mozilla/[45]\\.0 \\(compatible; MSIE (\\d+)\\.(\\d+)(?:[\\da-z]+)?;");
   private static final Pattern c = Pattern.compile("^Mozilla/5\\.0 \\(.+?Trident.+?; rv:(\\d\\d)\\.(\\d+)\\)");
   private static final Pattern d = Pattern.compile("^Mozilla/5\\.0 \\(Windows NT.+? Edge/(\\d+)\\.(\\d+)");
   private static final Pattern e = Pattern.compile("( \\.NET CLR [\\d\\.]+;?| Media Center PC [\\d\\.]+;?| OfficeLive[a-zA-Z0-9\\.\\d]+;?| InfoPath[\\.\\d]+;?)");
   private static final Map f;

   public Q(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(f.values());
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

   protected final String a(WURFLRequest var1) {
      String var2 = e.matcher(var1.getNormalizedDeviceUserAgent()).replaceFirst("");
      Matcher[] var8 = new Matcher[]{d.matcher(var2), c.matcher(var2), b.matcher(var2)};
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
         if ((var12 = (String)f.get(var9)) != null) {
            return var12;
         }
      }

      return super.a(var1);
   }

   protected final String a(String var1) {
      String var3;
      int var2 = StringMatchUtils.indexOfOrLength(var3 = e.matcher(var1).replaceFirst(""), "Trident");
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var3, var2);
   }

   protected final String b(WURFLRequest var1) {
      return StringMatchUtils.containsAnyOf(e.matcher(var1.getNormalizedDeviceUserAgent()).replaceFirst(""), "SLCC1", "Media Center PC", ".NET CLR", "OfficeLiveConnector") ? "generic_web_browser" : "generic";
   }

   public final String getMatcherName() {
      return "MSIEMatcher";
   }

   public final String getBucketMatcherName() {
      return "MSIE";
   }

   static {
      (f = new HashMap()).put("0", "msie");
      f.put("4", "msie_4");
      f.put("5", "msie_5");
      f.put("6", "msie_6");
      f.put("7", "msie_7");
      f.put("8", "msie_8");
      f.put("9", "msie_9");
      f.put("10", "msie_10");
      f.put("11", "msie_11");
      f.put("12", "msie_12");
      f.put("13", "edge_13");
      f.put("14", "edge_14");
      f.put("15", "edge_15");
      f.put("16", "edge_16");
      f.put("17", "edge_17");
   }
}
