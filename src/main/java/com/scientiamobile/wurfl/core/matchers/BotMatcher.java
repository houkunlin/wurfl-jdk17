package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.HashSet;
import java.util.Set;

final class BotMatcher extends AbstractMatcher {
   private static final String GOOGLE_IMAGE_PROXY = "google_image_proxy";

   public BotMatcher(WURFLModel var1) {
      super(var1);
   }

   protected final Set<String> getRequiredDeviceIds() {
      HashSet<String> var1;
      (var1 = new HashSet<>()).add(GOOGLE_IMAGE_PROXY);
      var1.add("generic_web_crawler");
      return var1;
   }

   public final boolean canHandle(WURFLRequest var1) {
      return var1._internalIsBot();
   }

   protected final String risMatch(String var1) {
      int var2;
      return (var2 = var1.startsWith("Mozilla") ? StringMatchUtils.firstCloseParenthesis(var1) : StringMatchUtils.firstSlash(var1)) != -1 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2) : StringMatchUtils.NULL_STRING;
   }

   protected final String applyConclusiveMatch(WURFLRequest var1) {
      return var1.getCleanedDeviceUserAgent().contains("GoogleImageProxy") ? GOOGLE_IMAGE_PROXY : super.applyConclusiveMatch(var1);
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return "generic_web_crawler";
   }

   public final String getMatcherName() {
      return "BotMatcher";
   }

   public final String getBucketMatcherName() {
      return "Bot";
   }
}
