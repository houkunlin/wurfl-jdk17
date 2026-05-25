package com.scientiamobile.wurfl.core.request.normalizer.generic;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebOSNormalizer implements UserAgentNormalizer {
   private static final Pattern a = Pattern.compile(" ([^/]+)/([\\d\\.]+)$");
   private static final Pattern b = Pattern.compile("(?:hpw|web)OS.(\\d)\\.");

   public String normalize(String var1) {
      Matcher var3;
      String var2 = (var3 = b.matcher(var1)).find() ? "webOS".concat(var3.group(1)) : null;
      String var5 = (var3 = a.matcher(var1)).find() ? var3.group(1) + " " + var3.group(2) : null;
      return var2 != null && var5 != null ? var5 + " " + var2 + "---" + var1 : var1;
   }
}
