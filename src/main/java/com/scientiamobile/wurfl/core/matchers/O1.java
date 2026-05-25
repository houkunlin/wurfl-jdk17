package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

final class O extends a {
   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add("generic");
      return var1;
   }

   public O(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public final boolean canHandle(WURFLRequest var1) {
      return !var1._internalIsDesktopBrowser() && StringUtils.startsWithIgnoreCase(var1.getCleanedDeviceUserAgent(), "lg");
   }

   protected final String a(String var1) {
      int var2 = StringMatchUtils.indexOfOrLength(var1, "/", var1.indexOf("LG"));
      return StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2);
   }

   protected final String b(WURFLRequest var1) {
      G var2;
      String var3;
      return (var3 = StringMatchUtils.risMatch((var2 = this.getFilter().a()).a(), var1.getNormalizedDeviceUserAgent(), 7)) != null ? var2.a(var3) : "generic";
   }

   public final String getMatcherName() {
      return "LGMatcher";
   }

   public final String getBucketMatcherName() {
      return "LG";
   }
}
