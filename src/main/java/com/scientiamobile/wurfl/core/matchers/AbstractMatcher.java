package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractMatcher implements Matcher {
   protected static final Logger LOG = LoggerFactory.getLogger(AbstractMatcher.class);
   private static final List CATCH_ALL_FALLBACKS;
   private MatcherFilter filter;
   private final UserAgentNormalizer normalizer;
   private static boolean ASSERTIONS_DISABLED = !AbstractMatcher.class.desiredAssertionStatus();

   public String toString() {
      return this.getClass().getSimpleName();
   }

   protected Set getRequiredDeviceIds() {
      return new HashSet();
   }

   private void validateRequiredDeviceIds(WURFLModel model) {
      if (model != null) {
         Set var4 = model.getAllDevicesId();

         for(String var3 : this.getRequiredDeviceIds()) {
            if (!var4.contains(var3)) {
               throw new MissingDeviceIdConsistencyException("wurfl.xml load error - Missing device id " + var3 + " you may need to update the wurfl.xml file to a more recent version");
            }
         }
      }

   }

   public AbstractMatcher() {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = null;
   }

   public AbstractMatcher(WURFLModel var1) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = null;
      this.validateRequiredDeviceIds(var1);
   }

   public AbstractMatcher(UserAgentNormalizer var1) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = var1;
   }

   public AbstractMatcher(UserAgentNormalizer var1, WURFLModel var2) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = var1;
      this.validateRequiredDeviceIds(var2);
   }

   public final void setFilter(MatcherFilter filter) {
      this.filter = filter;
   }

   public final MatcherFilter getFilter() {
      if (this.filter == null) {
         this.filter = new DefaultMatcherFilter(this);
      }

      return this.filter;
   }

   public final DeviceInfo match(WURFLRequest var1) {
      String var2 = "generic";
      var1.normalizeUserAgent(this.normalizer);
      String var3 = var1.getNormalizedDeviceUserAgent();
      MatchType var4 = MatchType.none;
      String var5 = this.getMatcherName();
      String var6 = this.getBucketMatcherName();
      if (!StringUtils.isBlank(var3)) {
         if (isBlankOrGeneric(var2 = this.getFilter().getIndex().getDeviceIdByUserAgent(var3))) {
            if (isBlankOrGeneric(var2 = this.applyConclusiveMatch(var1))) {
               if (isBlankOrGeneric(var2 = this.applyRecoveryMatch(var1))) {
                  this.getFilter().getIndex();
                  String var10000;
                  if (var1._internalIsDesktopBrowserHeavyDutyAnalysis()) {
                     var10000 = "generic_web_browser";
                  } else {
                     String var9 = var1.getCleanedDeviceUserAgent();
                     Iterator var7 = CATCH_ALL_FALLBACKS.iterator();

                     while(true) {
                        if (!var7.hasNext()) {
                           if (var9.indexOf("Mozilla/") <= 0 && !StringMatchUtils.containsAnyOf(var9, "Obigo", "AU-MIC/2", "AU-MIC-", "AU-OBIGO/", "Teleca Q03B1")) {
                              var10000 = StringMatchUtils.startsWithAnyOf(var9, "DoCoMo", "KDDI") ? "docomo_generic_jap_ver1" : (var1._internalIsMobileBrowser() ? "generic_mobile" : "generic");
                              break;
                           }

                           var10000 = "generic_xhtml";
                           break;
                        }

                        UserAgentFallbackRule var8 = (UserAgentFallbackRule)var7.next();
                        if (var9.contains(var8.keyword)) {
                           var10000 = var8.deviceId;
                           break;
                        }
                     }
                  }

                  var2 = var10000;
                  var4 = MatchType.catchAll;
               } else {
                  var4 = MatchType.recovery;
               }
            } else {
               var4 = MatchType.conclusive;
            }
         } else {
            var4 = MatchType.exact;
         }
      }

      if (!ASSERTIONS_DISABLED && var2 == null) {
         throw new AssertionError();
      } else {
         return new DeviceInfo(var2, var4, var5, var6, var1.getOriginalUserAgent(), var3);
      }
   }

   protected String applyConclusiveMatch(WURFLRequest var1) {
      String var3 = var1.getNormalizedDeviceUserAgent();
      var3 = this.risMatch(var3);
      String var2 = "generic";
      if (var3 != null) {
         var2 = this.getFilter().getIndex().getDeviceIdByUserAgent(var3);
      }

      if (var2 == null) {
         var2 = "generic";
      }

      return var2;
   }

   protected String risMatch(String var1) {
      int var2;
      return (var2 = StringMatchUtils.firstSlash(var1)) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), var1, var2);
   }

   protected String applyRecoveryMatch(WURFLRequest var1) {
      return "generic";
   }

   private static boolean isBlankOrGeneric(String deviceId) {
      return StringUtils.isBlank(deviceId) || "generic".equals(deviceId);
   }

   public final String normalize(String var1) {
      return this.normalizer == null ? var1 : this.normalizer.normalize(var1);
   }

   public String getBucketMatcherName() {
      return "Abstract";
   }

   public String getMatcherName() {
      return this.getClass().getSimpleName();
   }

   static {
      (CATCH_ALL_FALLBACKS = new ArrayList()).add(new UserAgentFallbackRule("CoreMedia", "apple_iphone_coremedia_ver1"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("Windows CE", "generic_ms_mobile"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/7.2", "opwv_v72_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/7", "opwv_v7_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/6.2", "opwv_v62_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/6", "opwv_v6_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/5", "upgui_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/4", "uptext_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("UP.Browser/3", "uptext_generic"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("Series60", "nokia_generic_series60"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.0", "generic_netfront_ver3"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.0", "generic_netfront_ver3"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.1", "generic_netfront_ver3_1"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.1", "generic_netfront_ver3_1"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.2", "generic_netfront_ver3_2"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.2", "generic_netfront_ver3_2"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.3", "generic_netfront_ver3_3"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("ACS-NF/3.3", "generic_netfront_ver3_3"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.4", "generic_netfront_ver3_4"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/3.5", "generic_netfront_ver3_5"));
      CATCH_ALL_FALLBACKS.add(new UserAgentFallbackRule("NetFront/4.0", "generic_netfront_ver4_0"));
   }
}
