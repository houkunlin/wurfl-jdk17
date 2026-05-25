package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.util.HashSet;
import java.util.Set;

final class UcwebU2Matcher extends MatcherBase {
   protected final Set getRequiredDeviceIds() {
      HashSet var1;
      (var1 = new HashSet()).add("generic_ucweb");
      return var1;
   }

   public UcwebU2Matcher(UserAgentNormalizer var1, WURFLModel var2) {
      super(var1, var2);
   }

   public final boolean canHandle(WURFLRequest var1) {
      String var2 = var1.getCleanedDeviceUserAgent();
      return !var1._internalIsDesktopBrowser() && var2.startsWith("UCWEB") && var2.contains("UCBrowser");
   }

   protected final String risMatch(String var1) {
      if (UserAgentUtils.getUcBrowserVersion(var1, true) == null) {
         return null;
      } else {
         int var2;
         if ((var2 = var1.indexOf("---")) > 0) {
            var2 += 3;
            String var3 = var1.substring(var2);
            if (var1.contains("Adr")) {
               var3 = UserAgentUtils.getUcAndroidModel(var1, false);
               String var4 = UserAgentUtils.getUcAndroidVersion(var1, false);
               if (var3 != null && var4 != null) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
               }
            } else if (var1.contains("iPh OS")) {
               if (UcwebU2Normalizer.IPHONE.matcher(var3).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
               }
            } else if (var1.contains("wds")) {
               if (UcwebU2Normalizer.WINDOWS_PHONE.matcher(var3).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
               }
            } else if (var1.contains("Symbian")) {
               if (UcwebU2Normalizer.SYMBIAN.matcher(var3).find()) {
                  return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
               }
            } else if (var1.contains("Java") && UcwebU2Normalizer.JAVA.matcher(var3).find()) {
               return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
            }
         }

         return null;
      }
   }

   protected final String applyRecoveryMatch(WURFLRequest var1) {
      return "generic_ucweb";
   }

   public final String getMatcherName() {
      return "UcwebU2Matcher";
   }

   public final String getBucketMatcherName() {
      return "UcwebU2";
   }
}
