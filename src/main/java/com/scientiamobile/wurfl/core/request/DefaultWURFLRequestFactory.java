package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.*;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

/**
 * Factory for creating Default WURFL Request instances.
 */

public class DefaultWURFLRequestFactory implements WURFLRequestFactoryWithPriority {
    private final UserAgentResolver userAgentResolver;
    private final UserAgentNormalizer userAgentNormalizer;
    private UserAgentPriority userAgentPriority;

    public DefaultWURFLRequestFactory() {
        this(createDefaultNormalizerChain());
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver) {
        this(userAgentResolver, createDefaultNormalizerChain());
    }

    public DefaultWURFLRequestFactory(UserAgentNormalizer userAgentNormalizer) {
        this(new HttpServletRequestUserAgentResolver(), userAgentNormalizer);
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentNormalizer userAgentNormalizer) {
        this(userAgentResolver, userAgentNormalizer, UserAgentPriority.OverrideSideloadedBrowserUserAgent);
    }

    public DefaultWURFLRequestFactory(UserAgentPriority userAgentPriority) {
        this(createDefaultNormalizerChain(), userAgentPriority);
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentPriority userAgentPriority) {
        this(userAgentResolver, createDefaultNormalizerChain(), userAgentPriority);
    }

    public DefaultWURFLRequestFactory(UserAgentNormalizer userAgentNormalizer, UserAgentPriority userAgentPriority) {
        this(new HttpServletRequestUserAgentResolver(), userAgentNormalizer, userAgentPriority);
    }

    public DefaultWURFLRequestFactory(UserAgentResolver userAgentResolver, UserAgentNormalizer userAgentNormalizer, UserAgentPriority userAgentPriority) {
        LoggerFactory.getLogger(DefaultWURFLRequestFactory.class);
        Validate.notNull(userAgentResolver, "userAgentResolver is null");
        this.userAgentResolver = userAgentResolver;
        this.userAgentNormalizer = userAgentNormalizer;
        this.userAgentPriority = userAgentPriority;
    }

    /**
     * Creat eefaul tormalize rhain.
     */

    private static UserAgentNormalizerChain createDefaultNormalizerChain() {
        return new UserAgentNormalizerChain(new UserAgentNormalizer[]{new UCWebNormalizer(), new UPLinkNormalizer(), new SerialNumberNormalizer(), new LocaleNormalizer(), new CFNetworkNormalizer(), new BlackBerryNormalizer(), new GenericAndroidNormalizer(), new TransferEncodingNormalizer()});
    }

    @Override
/**
 * Creat eequest.
 */

    public WURFLRequest createRequest(HttpServletRequest request, EngineTarget engineTarget) {
        Validate.notNull(request, "The sourceRequest must be not null");
        String userAgent = StringUtils.trimToEmpty(this.userAgentResolver.resolve(request));
        String uaProfile = UserAgentUtils.getUaProfile(request);
        return new DefaultWURFLRequest(userAgent, uaProfile, this.userAgentNormalizer, UserAgentUtils.getHeaders(request), this.userAgentPriority, engineTarget);
    }

    @Override
/**
 * Creat eequest.
 */

    public WURFLRequest createRequest(String userAgent, EngineTarget engineTarget) {
        userAgent = StringUtils.trimToEmpty(userAgent);
        return new DefaultWURFLRequest(userAgent, this.userAgentNormalizer, this.userAgentPriority, engineTarget);
    }

    /**
     * Creat eequest.
 */

    public WURFLRequest createRequest(String userAgent, String uaProfile, EngineTarget engineTarget) {
        userAgent = StringUtils.trimToEmpty(userAgent);
        return new DefaultWURFLRequest(userAgent, uaProfile, this.userAgentNormalizer, this.userAgentPriority, engineTarget);
    }

    @Override
/**
 * Creat eequest.
 */

    public WURFLRequest createRequest(WURFLHeaderProvider headerProvider, EngineTarget engineTarget) {
        return new DefaultWURFLRequest(this.userAgentNormalizer, headerProvider, this.userAgentPriority, engineTarget);
    }

    @Override
/**
 * Returns the use rgen triority.
 */

    public UserAgentPriority getUserAgentPriority() {
        return this.userAgentPriority;
    }

    @Override
/**
 * Sets the use rgen triority.
 */

    public void setUserAgentPriority(UserAgentPriority userAgentPriority) {
        this.userAgentPriority = userAgentPriority;
    }
}
