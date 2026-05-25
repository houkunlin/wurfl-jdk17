package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class B extends a {
   private static String b = "google_chrome";

   public B(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsMobileBrowser() && StringUtils.contains(var1.getCleanedDeviceUserAgent(), "Chrome");
   }

   protected final String a(String var1) {
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, StringMatchUtils.indexOfOrLength(var1, "."));
   }

   protected final String b(WURFLRequest var1) {
      return b;
   }

   public final String getMatcherName() {
      return "ChromeMatcher";
   }

   public final String getBucketMatcherName() {
      return "Chrome";
   }
}
