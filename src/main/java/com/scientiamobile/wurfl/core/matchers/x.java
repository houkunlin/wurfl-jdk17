package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class x extends a {
   private static String b = "google_image_proxy";

   public x(WURFLModel var1) {
      super(var1);
   }

   protected final Set a() {
      HashSet var1;
      (var1 = new HashSet()).add(b);
      var1.add("generic_web_crawler");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1._internalIsBot();
   }

   protected final String a(String var1) {
      int var2;
      return (var2 = var1.startsWith("Mozilla") ? StringMatchUtils.firstCloseParenthesis(var1) : StringMatchUtils.firstSlash(var1)) != -1 ? StringMatchUtils.risMatch(this.getFilter().a().a(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String a(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("GoogleImageProxy") ? b : super.a(var1);
   }

   protected final String b(WURFLRequest var1) {
      return "generic_web_crawler";
   }

   public final String getMatcherName() {
      return "BotMatcher";
   }

   public final String getBucketMatcherName() {
      return "Bot";
   }
}
