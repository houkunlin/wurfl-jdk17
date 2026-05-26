package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class AbstractMatcher implements Matcher {
   protected static final Logger LOG = LoggerFactory.getLogger(AbstractMatcher.class);
   private static final List<UserAgentFallbackRule> CATCH_ALL_FALLBACKS;
   private MatcherFilter filter;
   private final UserAgentNormalizer normalizer;
   private static boolean ASSERTIONS_DISABLED = !AbstractMatcher.class.desiredAssertionStatus();

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }

   protected Set<String> getRequiredDeviceIds() {
      return new HashSet<>();
   }

   private void validateRequiredDeviceIds(WURFLModel model) {
      if (model != null) {
         Set<String> allDeviceIds = model.getAllDevicesId();

         for (String deviceId : this.getRequiredDeviceIds()) {
            if (!allDeviceIds.contains(deviceId)) {
               throw new MissingDeviceIdConsistencyException("wurfl.xml load error - Missing device id " + deviceId + " you may need to update the wurfl.xml file to a more recent version");
            }
         }
      }

   }

   public AbstractMatcher() {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = null;
   }

   public AbstractMatcher(WURFLModel model) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = null;
      this.validateRequiredDeviceIds(model);
   }

   public AbstractMatcher(UserAgentNormalizer normalizer) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = normalizer;
   }

   public AbstractMatcher(UserAgentNormalizer normalizer, WURFLModel model) {
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.UNDETECTED_WURFL_DEVICES");
      LoggerFactory.getLogger("com.scientiamobile.wurfl.core.DETECTED_WURFL_DEVICES");
      this.normalizer = normalizer;
      this.validateRequiredDeviceIds(model);
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

   @Override
   public DeviceInfo match(WURFLRequest request) {
      String deviceId = "generic";
      request.normalizeUserAgent(this.normalizer);
      String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
      MatchType matchType = MatchType.none;
      String matcherName = this.getMatcherName();
      String bucketMatcherName = this.getBucketMatcherName();
      if (!StringUtils.isBlank(normalizedDeviceUserAgent)) {
         if (isBlankOrGeneric(deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedDeviceUserAgent))) {
            if (isBlankOrGeneric(deviceId = this.applyConclusiveMatch(request))) {
               if (isBlankOrGeneric(deviceId = this.applyRecoveryMatch(request))) {
                  this.getFilter().getIndex();
                  String fallbackDeviceId;
                  if (request._internalIsDesktopBrowserHeavyDutyAnalysis()) {
                     fallbackDeviceId = "generic_web_browser";
                  } else {
                     String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
                     fallbackDeviceId = null;

                     for(UserAgentFallbackRule fallbackRule : CATCH_ALL_FALLBACKS) {
                        if (cleanedDeviceUserAgent.contains(fallbackRule.keyword)) {
                           fallbackDeviceId = fallbackRule.deviceId;
                           break;
                        }
                     }

                     if (fallbackDeviceId == null) {
                        if (cleanedDeviceUserAgent.indexOf("Mozilla/") <= 0 && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Obigo", "AU-MIC/2", "AU-MIC-", "AU-OBIGO/", "Teleca Q03B1")) {
                           fallbackDeviceId = StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, "DoCoMo", "KDDI") ? "docomo_generic_jap_ver1" : (request._internalIsMobileBrowser() ? "generic_mobile" : "generic");
                        } else {
                           fallbackDeviceId = "generic_xhtml";
                        }
                     }
                  }

                  deviceId = fallbackDeviceId;
                  matchType = MatchType.catchAll;
               } else {
                  matchType = MatchType.recovery;
               }
            } else {
               matchType = MatchType.conclusive;
            }
         } else {
            matchType = MatchType.exact;
         }
      }

      if (!ASSERTIONS_DISABLED && deviceId == null) {
         throw new AssertionError();
      } else {
         return new DeviceInfo(deviceId, matchType, matcherName, bucketMatcherName, request.getOriginalUserAgent(), normalizedDeviceUserAgent);
      }
   }

   protected String applyConclusiveMatch(WURFLRequest request) {
      String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
      normalizedDeviceUserAgent = this.risMatch(normalizedDeviceUserAgent);
      String deviceId = "generic";
      if (normalizedDeviceUserAgent != null) {
         deviceId = this.getFilter().getIndex().getDeviceIdByUserAgent(normalizedDeviceUserAgent);
      }

      if (deviceId == null) {
         deviceId = "generic";
      }

      return deviceId;
   }

   protected String risMatch(String value) {
      int firstSlashIndex;
      return (firstSlashIndex = StringMatchUtils.firstSlash(value)) == -1 ? StringMatchUtils.NULL_STRING : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), value, firstSlashIndex);
   }

   protected String applyRecoveryMatch(WURFLRequest request) {
      return "generic";
   }

   private static boolean isBlankOrGeneric(String deviceId) {
      return StringUtils.isBlank(deviceId) || "generic".equals(deviceId);
   }

   @Override
   public String normalize(String value) {
      return this.normalizer == null ? value : this.normalizer.normalize(value);
   }

   public String getBucketMatcherName() {
      return "Abstract";
   }

   @Override
   public String getMatcherName() {
      return this.getClass().getSimpleName();
   }

   static {
      (CATCH_ALL_FALLBACKS = new ArrayList<>()).add(new UserAgentFallbackRule("CoreMedia", "apple_iphone_coremedia_ver1"));
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
