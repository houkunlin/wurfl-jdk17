package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

final class I extends a {
   private static String b = "firefox_os_ver1_3_tablet";
   private static String c = "generic_firefox_os";
   private static final Pattern d = Pattern.compile("\\brv:\\d+\\.\\d+(.)");
   private static final Pattern e = Pattern.compile("\\brv:(\\d+\\.\\d+)");
   private static final Map f = new HashMap();
   private static final List g = new ArrayList();

   public I(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).addAll(g);
      var1.add(b);
      var1.add(c);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2;
      return StringUtils.contains(var2 = var1.getCleanedDeviceUserAgent(), "Firefox/") && StringMatchUtils.containsAnyOf(var2, "Mobile", "Tablet");
   }

   protected final String a(String var1) {
      Matcher var2;
      return (var2 = d.matcher(var1)).find() ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2.end(1)) : null;
   }

   protected final String b(WURFLRequest var1) {
      String var10000;
      label24: {
         String var2 = var3 = var1.getNormalizedDeviceUserAgent();
         Matcher var5;
         if ((var5 = e.matcher(var2)).find()) {
            String var6 = var5.group(1);
            if (f.containsKey(var6)) {
               var10000 = (String)f.get(var6);
               break label24;
            }
         }

         var10000 = "1.0";
      }

      String var7 = var10000.replace(".", "_").replace("_0", "");
      var7 = "firefox_os_ver" + var7;
      if (var3.contains("Tablet")) {
         String var4 = var7 + "_tablet";
         return g.contains(var4) ? var4 : b;
      } else {
         return g.contains(var7) ? var7 : c;
      }
   }

   public final String getMatcherName() {
      return "FirefoxOSMatcher";
   }

   public final String getBucketMatcherName() {
      return "FirefoxOS";
   }

   static {
      f.put("18.0", "1.0");
      f.put("18.1", "1.1");
      f.put("26.0", "1.2");
      f.put("28.0", "1.3");
      f.put("30.0", "1.4");
      f.put("32.0", "2.0");
      f.put("33.0", "2.1");
      f.put("34.0", "2.1");
      f.put("37.0", "2.2");
      f.put("43.0", "2.5");
      g.add("firefox_os_ver1");
      g.add("firefox_os_ver1_1");
      g.add("firefox_os_ver1_2");
      g.add("firefox_os_ver1_3");
      g.add("firefox_os_ver1_4");
      g.add("firefox_os_ver1_4_tablet");
      g.add("firefox_os_ver2_0");
      g.add("firefox_os_ver2_0_tablet");
      g.add("firefox_os_ver2_1");
      g.add("firefox_os_ver2_1_tablet");
      g.add("firefox_os_ver2_2");
      g.add("firefox_os_ver2_2_tablet");
      g.add("firefox_os_ver2_5");
      g.add("firefox_os_ver2_5_tablet");
   }
}
