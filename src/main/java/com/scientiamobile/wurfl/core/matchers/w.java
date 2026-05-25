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
import org.apache.commons.lang.StringUtils;

final class BlackBerryMatcher extends AbstractMatcher {
   private static String b = "blackberry_generic_ver10";
   private static String c = "blackberry_generic_ver10_tablet";
   private static String d = "rim_playbook_ver1";
   private static final Map e;
   private static final Pattern f;

   public BlackBerryMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(e.values());
      var1.add("generic_mobile");
      var1.add(d);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && (StringUtils.containsIgnoreCase(var2, "blackberry") || StringMatchUtils.containsAnyOf(var2, "(BB10;", "(PlayBook"));
   }

   protected final String a(String var1) {
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

      return var2 != -1 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String b(WURFLRequest var1) {
      String var4;
      String var2 = var4 = var1.getNormalizedDeviceUserAgent();
      Matcher var6;
      var2 = (var6 = f.matcher(var2)).find() ? var6.group(1) : null;
      if (var4.contains("BB10")) {
         return var4.contains("Mobile") ? b : c;
      } else if (var4.contains("PlayBook")) {
         return d;
      } else {
         if (var2 != null) {
            for(Map.Entry var3 : e.entrySet()) {
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
      (e = new LinkedHashMap()).put("2.", "blackberry_generic_ver2");
      e.put("3.2", "blackberry_generic_ver3_sub2");
      e.put("3.3", "blackberry_generic_ver3_sub30");
      e.put("3.5", "blackberry_generic_ver3_sub50");
      e.put("3.6", "blackberry_generic_ver3_sub60");
      e.put("3.7", "blackberry_generic_ver3_sub70");
      e.put("4.1", "blackberry_generic_ver4_sub10");
      e.put("4.2", "blackberry_generic_ver4_sub20");
      e.put("4.3", "blackberry_generic_ver4_sub30");
      e.put("4.5", "blackberry_generic_ver4_sub50");
      e.put("4.6", "blackberry_generic_ver4_sub60");
      e.put("4.7", "blackberry_generic_ver4_sub70");
      e.put("4.", "blackberry_generic_ver4");
      e.put("5.", "blackberry_generic_ver5");
      e.put("6.", "blackberry_generic_ver6");
      e.put("10", b);
      e.put("10t", c);
      f = Pattern.compile("BlackBerry[^/\\s]+/(\\d\\.\\d)");
   }
}
