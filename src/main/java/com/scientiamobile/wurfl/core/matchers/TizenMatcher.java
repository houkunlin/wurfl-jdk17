package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TizenMatcher extends MatcherBase {
   private static final Pattern b = Pattern.compile("Tizen (\\d+?\\.\\d+?)");
   private static final List<String> c = new ArrayList<>();
   private static final List<String> d = new ArrayList<>();

   public TizenMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> a() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).addAll(c);
      var1.add("generic_tizen");
      return var1;
   }

   public boolean canHandle(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().startsWith("Mozilla") && var1.getCleanedDeviceUserAgent().contains("Tizen");
   }

   protected final String a(String var1) {
      int var2;
      return (var2 = var1.indexOf("AppleWebKit/")) >= 0 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2 + 12) : null;
   }

   protected final String b(WURFLRequest var1) {
      StringBuilder var10000 = new StringBuilder("generic_tizen_ver");
      String var2 = var1.getNormalizedDeviceUserAgent();
      Matcher var3;
      var2 = var10000.append((var3 = b.matcher(var2)).find() && d.contains(var3.group(1)) ? var3.group(1).replace('.', '_') : "1_0").toString();
      return c.contains(var2) ? var2 : "generic_tizen";
   }

   public String getMatcherName() {
      return "TizenMatcher";
   }

   public String getBucketMatcherName() {
      return "Tizen";
   }

   static {
      c.add("generic_tizen_ver1_0");
      c.add("generic_tizen_ver2_0");
      c.add("generic_tizen_ver2_1");
      c.add("generic_tizen_ver2_2");
      c.add("generic_tizen_ver2_3");
      c.add("generic_tizen_ver2_4");
      c.add("generic_tizen_ver3_0");
      d.add("1.0");
      d.add("2.0");
      d.add("2.1");
      d.add("2.2");
      d.add("2.3");
      d.add("2.4");
      d.add("3.0");
   }
}
