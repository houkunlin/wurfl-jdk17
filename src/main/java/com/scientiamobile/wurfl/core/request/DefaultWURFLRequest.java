package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.matchers.EmailClientUserAgentMatcher;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class DefaultWURFLRequest implements WURFLRequest, Serializable {
   private static final String[] USER_AGENT_HEADERS = new String[]{"Device-Stock-UA", "X-OperaMini-Phone-UA", "X-UCBrowser-Device-UA", "User-Agent"};
   private static final long serialVersionUID = 100L;
   private String deviceUserAgent;
   private String cleanedDeviceUserAgent;
   private String normalizedDeviceUserAgent;
   private String browserUserAgent;
   private UserAgentPriority userAgentPriority;
   private String userAgentProfile;
   private final EngineTarget engineTarget;
   @SuppressWarnings("serial")
   private final Map<String, String> headers;
   private Boolean cachedIsMobileBrowser;
   private Boolean cachedMobileKeywordsDetected;
   private Boolean cachedScreenSizeDetected;
   private Boolean cachedIsBot;
   private Boolean cachedIsDesktopBrowser;
   private Boolean cachedIsDesktopBrowserHeavyDutyAnalysis;
   private Boolean cachedIsSmartTvBrowser;
   private Boolean cachedIsEmailClient;
   private transient UserAgentNormalizer genericNormalizer;
   private boolean urlEncoded;

   public DefaultWURFLRequest(String userAgent, UserAgentNormalizer genericNormalizer, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
      this(userAgent, (String)null, genericNormalizer, userAgentPriority, engineTarget);
   }

   public DefaultWURFLRequest(String userAgent, String userAgentProfile, UserAgentNormalizer genericNormalizer, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
      this(userAgent, userAgentProfile, genericNormalizer, new HashMap<>(), userAgentPriority, engineTarget);
   }

   public DefaultWURFLRequest(String userAgent, String userAgentProfile, UserAgentNormalizer genericNormalizer, Map<String, String> headers, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
      this.cachedIsMobileBrowser = null;
      this.cachedMobileKeywordsDetected = null;
      this.cachedScreenSizeDetected = null;
      this.cachedIsBot = null;
      this.cachedIsDesktopBrowser = null;
      this.cachedIsDesktopBrowserHeavyDutyAnalysis = null;
      this.cachedIsSmartTvBrowser = null;
      this.cachedIsEmailClient = null;
      this.userAgentPriority = userAgentPriority;
      this.engineTarget = engineTarget;
      this.browserUserAgent = truncateUserAgent(headers.get("User-Agent"));
      if (this.browserUserAgent == null && userAgent != null) {
         this.browserUserAgent = truncateUserAgent(userAgent);
      }

      this.deviceUserAgent = getFirstAvailableUserAgent(headers);
      if (this.deviceUserAgent == null) {
         this.deviceUserAgent = this.browserUserAgent;
      }

      this.userAgentProfile = userAgentProfile;
      this.headers = headers;
      this.genericNormalizer = genericNormalizer;
      this.cleanedDeviceUserAgent = this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.deviceUserAgent : this.browserUserAgent;
      this.urlEncoded = UserAgentUtils.isRawUrlEncoded(userAgent) || UserAgentUtils.hasIIsLoggingStyle(userAgent);
      this.applyUcBrowserDeviceUserAgentOverrideIfNeeded();
   }

   public DefaultWURFLRequest(UserAgentNormalizer genericNormalizer, WURFLHeaderProvider headerProvider, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
      this.cachedIsMobileBrowser = null;
      this.cachedMobileKeywordsDetected = null;
      this.cachedScreenSizeDetected = null;
      this.cachedIsBot = null;
      this.cachedIsDesktopBrowser = null;
      this.cachedIsDesktopBrowserHeavyDutyAnalysis = null;
      this.cachedIsSmartTvBrowser = null;
      this.cachedIsEmailClient = null;
      this.userAgentPriority = userAgentPriority;
      this.engineTarget = engineTarget;
      this.browserUserAgent = truncateUserAgent(headerProvider.getHeader("User-Agent"));
      this.deviceUserAgent = getFirstAvailableUserAgent(headerProvider);
      if (this.deviceUserAgent == null) {
         this.deviceUserAgent = this.browserUserAgent;
      }

      this.userAgentProfile = UserAgentUtils.getUaProfile(headerProvider);
      this.headers = new HashMap<>();
      Enumeration<String> headerNames = headerProvider.getHeaderNames();

      while(headerNames.hasMoreElements()) {
         String headerName = headerNames.nextElement();
         this.headers.put(headerName, headerProvider.getHeader(headerName));
      }

      this.genericNormalizer = genericNormalizer;
      this.cleanedDeviceUserAgent = this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.deviceUserAgent : this.browserUserAgent;
      this.urlEncoded = UserAgentUtils.isRawUrlEncoded(this.deviceUserAgent) || UserAgentUtils.hasIIsLoggingStyle(this.deviceUserAgent);
      this.applyUcBrowserDeviceUserAgentOverrideIfNeeded();
   }

   private void applyUcBrowserDeviceUserAgentOverrideIfNeeded() {
      if (this.headers.size() > 0 && this.headers.containsKey("X-UCBrowser-Device-UA")) {
         this.browserUserAgent = this.deviceUserAgent;
      }

   }

   private static String getFirstAvailableUserAgent(WURFLHeaderProvider headerProvider) {
      for(int i = 0; i < 4; ++i) {
         String headerValue = headerProvider.getHeader(USER_AGENT_HEADERS[i]);
         if (headerValue != null) {
            return truncateUserAgent(headerValue);
         }
      }

      return null;
   }

   private static String getFirstAvailableUserAgent(Map<String, String> headers) {
      for(int i = 0; i < 4; ++i) {
         String headerValue = headers.get(USER_AGENT_HEADERS[i]);
         if (headerValue != null) {
            return truncateUserAgent(headerValue);
         }
      }

      return null;
   }

   private static String truncateUserAgent(String userAgent) {
      return userAgent != null && userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent;
   }

   public String getDeviceUserAgent() {
      return this.deviceUserAgent;
   }

   public String getCleanedDeviceUserAgent() {
      return this.cleanedDeviceUserAgent;
   }

   public String getNormalizedDeviceUserAgent() {
      return this.normalizedDeviceUserAgent;
   }

   public String getBrowserUserAgent() {
      return this.browserUserAgent;
   }

   public final String getOriginalUserAgent() {
      return this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.getDeviceUserAgent() : this.getBrowserUserAgent();
   }

   public void normalizeUserAgent(UserAgentNormalizer normalizer) {
      if (normalizer == null) {
         this.normalizedDeviceUserAgent = this.cleanedDeviceUserAgent;
      } else {
         this.normalizedDeviceUserAgent = normalizer.normalize(this.cleanedDeviceUserAgent);
      }
   }

   public boolean isUrlEncoded() {
      return this.urlEncoded;
   }

   public void setUrlEncoded(boolean urlEncoded) {
      this.urlEncoded = urlEncoded;
   }

   public void performGenericNormalization() {
      if (this.genericNormalizer != null) {
         this.cleanedDeviceUserAgent = this.genericNormalizer.normalize(this.getOriginalUserAgent());
      }

   }

   public String getUserAgentProfile() {
      return this.userAgentProfile;
   }

   public String getHeader(String headerName) {
      return this.headers.get(headerName);
   }

   public Map<String, String> getHeaders() {
      return Collections.unmodifiableMap(this.headers);
   }

   public EngineTarget getEngineTarget() {
      return this.engineTarget;
   }

   public boolean _internalIsMobileBrowser() {
      if (this.cachedIsMobileBrowser == null) {
         if (this._internalIsDesktopBrowser()) {
            this.cachedIsMobileBrowser = false;
         } else if (this.mobileKeywordsDetected()) {
            this.cachedIsMobileBrowser = true;
         } else {
            this.cachedIsMobileBrowser = this.screenSizeDetected();
         }
      }

      return this.cachedIsMobileBrowser;
   }

   public boolean mobileKeywordsDetected() {
      if (this.cachedMobileKeywordsDetected == null) {
         this.cachedMobileKeywordsDetected = UserAgentUtils.mobileKeywordsDetected(this.cleanedDeviceUserAgent);
      }

      return this.cachedMobileKeywordsDetected;
   }

   public boolean screenSizeDetected() {
      if (this.cachedScreenSizeDetected == null) {
         this.cachedScreenSizeDetected = UserAgentUtils.screenSizeDetected(this.cleanedDeviceUserAgent);
      }

      return this.cachedScreenSizeDetected;
   }

   public boolean _internalIsDesktopBrowser() {
      if (this.cachedIsDesktopBrowser == null) {
         this.cachedIsDesktopBrowser = UserAgentUtils.isDesktopBrowser(this.cleanedDeviceUserAgent);
      }

      return this.cachedIsDesktopBrowser;
   }

   public boolean _internalIsSmartTvBrowser() {
      if (this.cachedIsSmartTvBrowser == null) {
         this.cachedIsSmartTvBrowser = UserAgentUtils.isSmartTvBrowser(this.cleanedDeviceUserAgent);
      }

      return this.cachedIsSmartTvBrowser;
   }

   public boolean _internalIsBot() {
      if (this.cachedIsBot == null) {
         this.cachedIsBot = UserAgentUtils.isBot(this.getOriginalUserAgent());
      }

      return this.cachedIsBot;
   }

   public boolean _internalIsDesktopBrowserHeavyDutyAnalysis() {
      if (this.cachedIsDesktopBrowserHeavyDutyAnalysis == null) {
         if (this._internalIsSmartTvBrowser()) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = false;
         } else if (StringMatchUtils.containsAllOf(this.cleanedDeviceUserAgent, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = false;
         } else if (this.cleanedDeviceUserAgent.contains("Chrome") && !StringMatchUtils.containsAnyOf(this.cleanedDeviceUserAgent, "Android", "Ventana", "android", "Tizen")) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = true;
         } else if (this.mobileKeywordsDetected()) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = false;
         } else if (this.cleanedDeviceUserAgent.contains("PPC")) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = false;
         } else if (this.cleanedDeviceUserAgent.contains("Firefox") && !this.cleanedDeviceUserAgent.contains("Tablet")) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = true;
         } else if (UserAgentUtils.isDesktopPattern(this.cleanedDeviceUserAgent)) {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = true;
         } else if (!this.cleanedDeviceUserAgent.startsWith("Opera/9.80 (Windows NT") && !this.cleanedDeviceUserAgent.startsWith("Opera/9.80 (Macintosh")) {
            if (this._internalIsDesktopBrowser()) {
               this.cachedIsDesktopBrowserHeavyDutyAnalysis = true;
            } else {
               this.cachedIsDesktopBrowserHeavyDutyAnalysis = UserAgentUtils.isIEPattern(this.cleanedDeviceUserAgent);
            }
         } else {
            this.cachedIsDesktopBrowserHeavyDutyAnalysis = true;
         }
      }

      return this.cachedIsDesktopBrowserHeavyDutyAnalysis;
   }

   public boolean _internalIsEmailClient() {
      if (this.cachedIsEmailClient == null) {
         this.cachedIsEmailClient = StringMatchUtils.containsAnyOf(this.cleanedDeviceUserAgent, EmailClientUserAgentMatcher.EMAIL_CLIENTS.toArray(new String[0]));
      }

      return this.cachedIsEmailClient;
   }

   public int hashCode() {
      HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(35, 79);
      hashCodeBuilder.append(this.getClass()).append(this.deviceUserAgent).append(this.userAgentProfile).toHashCode();
      return hashCodeBuilder.toHashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof DefaultWURFLRequest)) {
         return false;
      } else {
         DefaultWURFLRequest other = (DefaultWURFLRequest)obj;
         return (new EqualsBuilder()).append(this.deviceUserAgent, other.deviceUserAgent).append(this.userAgentProfile, other.userAgentProfile).isEquals();
      }
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("[userAgent: ").append(this.getOriginalUserAgent()).append(", userAgentProfile: ").append(this.userAgentProfile).append("]");
      return builder.toString();
   }
}
