package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.matchers.EmailClientUserAgentMatcher;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.scientiamobile.wurfl.core.Constants.USER_AGENT;

/**
 * Implementation of Default WURFL Request.
 */

public class DefaultWURFLRequest implements WURFLRequest, Serializable {
    private static final String[] USER_AGENT_HEADERS = new String[]{"Device-Stock-UA", "X-OperaMini-Phone-UA", "X-UCBrowser-Device-UA", USER_AGENT};
    @Serial
    private static final long serialVersionUID = 100L;
    private final EngineTarget engineTarget;
    private final Map<String, String> headers;
    private String deviceUserAgent;
    private String cleanedDeviceUserAgent;
    private String normalizedDeviceUserAgent;
    private String browserUserAgent;
    private final UserAgentPriority userAgentPriority;
    private final String userAgentProfile;
    private Boolean cachedIsMobileBrowser;
    private Boolean cachedMobileKeywordsDetected;
    private Boolean cachedScreenSizeDetected;
    private Boolean cachedIsBot;
    private Boolean cachedIsDesktopBrowser;
    private Boolean cachedIsDesktopBrowserHeavyDutyAnalysis;
    private Boolean cachedIsSmartTvBrowser;
    private Boolean cachedIsEmailClient;
    private final transient UserAgentNormalizer genericNormalizer;
    private boolean urlEncoded;

    public DefaultWURFLRequest(String userAgent, UserAgentNormalizer genericNormalizer, UserAgentPriority userAgentPriority, EngineTarget engineTarget) {
        this(userAgent, (String) null, genericNormalizer, userAgentPriority, engineTarget);
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
        this.browserUserAgent = truncateUserAgent(headers.get(USER_AGENT));
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
        this.browserUserAgent = truncateUserAgent(headerProvider.getHeader(USER_AGENT));
        this.deviceUserAgent = getFirstAvailableUserAgent(headerProvider);
        if (this.deviceUserAgent == null) {
            this.deviceUserAgent = this.browserUserAgent;
        }

        this.userAgentProfile = UserAgentUtils.getUaProfile(headerProvider);
        this.headers = new HashMap<>();
        Enumeration<String> headerNames = headerProvider.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            this.headers.put(headerName, headerProvider.getHeader(headerName));
        }

        this.genericNormalizer = genericNormalizer;
        this.cleanedDeviceUserAgent = this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.deviceUserAgent : this.browserUserAgent;
        this.urlEncoded = UserAgentUtils.isRawUrlEncoded(this.deviceUserAgent) || UserAgentUtils.hasIIsLoggingStyle(this.deviceUserAgent);
        this.applyUcBrowserDeviceUserAgentOverrideIfNeeded();
    }

    /**
     * Returns the firs tvailabl ese rgent.
     */

    private static String getFirstAvailableUserAgent(WURFLHeaderProvider headerProvider) {
        for (String header : USER_AGENT_HEADERS) {
            String headerValue = headerProvider.getHeader(header);
            if (headerValue != null) {
                return truncateUserAgent(headerValue);
            }
        }

        return null;
    }

    /**
     * Returns the firs tvailabl ese rgent.
 */

    private static String getFirstAvailableUserAgent(Map<String, String> headers) {
        for (String header : USER_AGENT_HEADERS) {
            String headerValue = headers.get(header);
            if (headerValue != null) {
                return truncateUserAgent(headerValue);
            }
        }

        return null;
    }

    /**
     * Truncat ese rgent.
 */

    private static String truncateUserAgent(String userAgent) {
        return userAgent != null && userAgent.length() > 255 ? userAgent.substring(0, 255) : userAgent;
    }

    private void applyUcBrowserDeviceUserAgentOverrideIfNeeded() {
        if (this.headers.size() > 0 && this.headers.containsKey("X-UCBrowser-Device-UA")) {
            this.browserUserAgent = this.deviceUserAgent;
        }

    }

    @Override
/**
 * Returns the devic ese rgent.
 */

    public String getDeviceUserAgent() {
        return this.deviceUserAgent;
    }

    @Override
/**
 * Returns the cleane devic ese rgent.
 */

    public String getCleanedDeviceUserAgent() {
        return this.cleanedDeviceUserAgent;
    }

    @Override
/**
 * Returns the normalize devic ese rgent.
 */

    public String getNormalizedDeviceUserAgent() {
        return this.normalizedDeviceUserAgent;
    }

    @Override
/**
 * Returns the browse rse rgent.
 */

    public String getBrowserUserAgent() {
        return this.browserUserAgent;
    }

    @Override
/**
 * Returns the origina lse rgent.
 */

    public String getOriginalUserAgent() {
        return this.userAgentPriority == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.getDeviceUserAgent() : this.getBrowserUserAgent();
    }

    @Override
/**
 * Normaliz ese rgent.
 */

    public void normalizeUserAgent(UserAgentNormalizer normalizer) {
        if (normalizer == null) {
            this.normalizedDeviceUserAgent = this.cleanedDeviceUserAgent;
        } else {
            this.normalizedDeviceUserAgent = normalizer.normalize(this.cleanedDeviceUserAgent);
        }
    }

    @Override
/**
 * Returns whether this i sr lncoded.
 */

    public boolean isUrlEncoded() {
        return this.urlEncoded;
    }

    @Override
/**
 * Sets the ur lncoded.
 */

    public void setUrlEncoded(boolean urlEncoded) {
        this.urlEncoded = urlEncoded;
    }

    @Override
/**
 * Perfor meneri cormalization.
 */

    public void performGenericNormalization() {
        if (this.genericNormalizer != null) {
            this.cleanedDeviceUserAgent = this.genericNormalizer.normalize(this.getOriginalUserAgent());
        }

    }

    @Override
/**
 * Returns the use rgen trofile.
 */

    public String getUserAgentProfile() {
        return this.userAgentProfile;
    }

    @Override
/**
 * Returns the header.
 */

    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    @Override
/**
 * Returns the headers.
 */

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    @Override
/**
 * Returns the engin earget.
 */

    public EngineTarget getEngineTarget() {
        return this.engineTarget;
    }

    @Override
/**
 * _interna l sobil erowser.
 */

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

    /**
     * Mobil eeyword setected.
 */

    public boolean mobileKeywordsDetected() {
        if (this.cachedMobileKeywordsDetected == null) {
            this.cachedMobileKeywordsDetected = UserAgentUtils.mobileKeywordsDetected(this.cleanedDeviceUserAgent);
        }

        return this.cachedMobileKeywordsDetected;
    }

    /**
     * Scree niz eetected.
 */

    public boolean screenSizeDetected() {
        if (this.cachedScreenSizeDetected == null) {
            this.cachedScreenSizeDetected = UserAgentUtils.screenSizeDetected(this.cleanedDeviceUserAgent);
        }

        return this.cachedScreenSizeDetected;
    }

    @Override
/**
 * _interna l seskto prowser.
 */

    public boolean _internalIsDesktopBrowser() {
        if (this.cachedIsDesktopBrowser == null) {
            this.cachedIsDesktopBrowser = UserAgentUtils.isDesktopBrowser(this.cleanedDeviceUserAgent);
        }

        return this.cachedIsDesktopBrowser;
    }

    @Override
/**
 * _interna l smar t vrowser.
 */

    public boolean _internalIsSmartTvBrowser() {
        if (this.cachedIsSmartTvBrowser == null) {
            this.cachedIsSmartTvBrowser = UserAgentUtils.isSmartTvBrowser(this.cleanedDeviceUserAgent);
        }

        return this.cachedIsSmartTvBrowser;
    }

    @Override
/**
 * _interna l sot.
 */

    public boolean _internalIsBot() {
        if (this.cachedIsBot == null) {
            this.cachedIsBot = UserAgentUtils.isBot(this.getOriginalUserAgent());
        }

        return this.cachedIsBot;
    }

    @Override
/**
 * _interna l seskto prowse reav yut ynalysis.
 */

    public boolean _internalIsDesktopBrowserHeavyDutyAnalysis() {
        if (this.cachedIsDesktopBrowserHeavyDutyAnalysis != null) {
            return this.cachedIsDesktopBrowserHeavyDutyAnalysis;
        }
        if (this._internalIsSmartTvBrowser()) {
            return cacheDesktopHeavyDuty(false);
        }
        String ua = this.cleanedDeviceUserAgent;
        if (StringMatchUtils.containsAllOf(ua, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            return cacheDesktopHeavyDuty(false);
        }
        if (ua.contains("Chrome") && !StringMatchUtils.containsAnyOf(ua, "Android", "Ventana", "android", "Tizen")) {
            return cacheDesktopHeavyDuty(true);
        }
        if (this.mobileKeywordsDetected() || ua.contains("PPC")) {
            return cacheDesktopHeavyDuty(false);
        }
        if (ua.contains("Firefox") && !ua.contains("Tablet")) {
            return cacheDesktopHeavyDuty(true);
        }
        if (UserAgentUtils.isDesktopPattern(ua)) {
            return cacheDesktopHeavyDuty(true);
        }
        boolean isOperaOnDesktop = ua.startsWith("Opera/9.80 (Windows NT") || ua.startsWith("Opera/9.80 (Macintosh");
        if (isOperaOnDesktop) {
            return cacheDesktopHeavyDuty(true);
        }
        if (this._internalIsDesktopBrowser()) {
            return cacheDesktopHeavyDuty(true);
        }
        return cacheDesktopHeavyDuty(UserAgentUtils.isIEPattern(ua));
    }

    /**
     * Cach eeskto peav yuty.
 */

    private boolean cacheDesktopHeavyDuty(boolean value) {
        this.cachedIsDesktopBrowserHeavyDutyAnalysis = value;
        return value;
    }

    @Override
/**
 * _interna l smai llient.
 */

    public boolean _internalIsEmailClient() {
        if (this.cachedIsEmailClient == null) {
            this.cachedIsEmailClient = StringMatchUtils.containsAnyOf(this.cleanedDeviceUserAgent, EmailClientUserAgentMatcher.EMAIL_CLIENTS.toArray(new String[0]));
        }

        return this.cachedIsEmailClient;
    }

    @Override
/**
 * Returns whether this has hode.
 */

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(35, 79);
        hashCodeBuilder.append(this.getClass()).append(this.deviceUserAgent).append(this.userAgentProfile).toHashCode();
        return hashCodeBuilder.toHashCode();
    }

    @Override
/**
 * Indicates whether some other object is equal to this one.
 * @param obj the reference object with which to compare
 * @return true if this object is the same as the obj argument
 */

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof DefaultWURFLRequest other)) {
            return false;
        } else {
            return (new EqualsBuilder()).append(this.deviceUserAgent, other.deviceUserAgent).append(this.userAgentProfile, other.userAgentProfile).isEquals();
        }
    }

    @Override
/**
 * Returns a string representation of this object.
 */

    public String toString() {
        return "[userAgent: " + this.getOriginalUserAgent() + ", userAgentProfile: " + this.userAgentProfile + "]";
    }
}
