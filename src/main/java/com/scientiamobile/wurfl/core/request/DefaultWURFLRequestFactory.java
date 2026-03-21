package com.scientiamobile.wurfl.core.request;

import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.UserAgentPriority;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.BlackBerryNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.CFNetworkNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.GenericAndroidNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.LocaleNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.SerialNumberNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.TransferEncodingNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.UCWebNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.UPLinkNormalizer;
import com.scientiamobile.wurfl.core.utils.UserAgentUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

public class DefaultWURFLRequestFactory implements WURFLRequestFactoryWithPriority {
  private final UserAgentResolver a;
  
  private UserAgentNormalizer b;
  
  private UserAgentPriority c;
  
  private static UserAgentNormalizerChain a() {
    return new UserAgentNormalizerChain(new UserAgentNormalizer[] { (UserAgentNormalizer)new UCWebNormalizer(), (UserAgentNormalizer)new UPLinkNormalizer(), (UserAgentNormalizer)new SerialNumberNormalizer(), (UserAgentNormalizer)new LocaleNormalizer(), (UserAgentNormalizer)new CFNetworkNormalizer(), (UserAgentNormalizer)new BlackBerryNormalizer(), (UserAgentNormalizer)new GenericAndroidNormalizer(), (UserAgentNormalizer)new TransferEncodingNormalizer() });
  }
  
  public DefaultWURFLRequestFactory() {
    this(a());
  }
  
  public DefaultWURFLRequestFactory(UserAgentResolver paramUserAgentResolver) {
    this(paramUserAgentResolver, a());
  }
  
  public DefaultWURFLRequestFactory(UserAgentNormalizer paramUserAgentNormalizer) {
    this(new a(), paramUserAgentNormalizer);
  }
  
  public DefaultWURFLRequestFactory(UserAgentResolver paramUserAgentResolver, UserAgentNormalizer paramUserAgentNormalizer) {
    this(paramUserAgentResolver, paramUserAgentNormalizer, UserAgentPriority.OverrideSideloadedBrowserUserAgent);
  }
  
  public DefaultWURFLRequestFactory(UserAgentPriority paramUserAgentPriority) {
    this(a(), paramUserAgentPriority);
  }
  
  public DefaultWURFLRequestFactory(UserAgentResolver paramUserAgentResolver, UserAgentPriority paramUserAgentPriority) {
    this(paramUserAgentResolver, a(), paramUserAgentPriority);
  }
  
  public DefaultWURFLRequestFactory(UserAgentNormalizer paramUserAgentNormalizer, UserAgentPriority paramUserAgentPriority) {
    this(new a(), paramUserAgentNormalizer, paramUserAgentPriority);
  }
  
  public DefaultWURFLRequestFactory(UserAgentResolver paramUserAgentResolver, UserAgentNormalizer paramUserAgentNormalizer, UserAgentPriority paramUserAgentPriority) {
    LoggerFactory.getLogger(DefaultWURFLRequestFactory.class);
    Validate.notNull(paramUserAgentResolver, "userAgentResolver is null");
    this.a = paramUserAgentResolver;
    this.b = paramUserAgentNormalizer;
    this.c = paramUserAgentPriority;
  }
  
  public WURFLRequest createRequest(HttpServletRequest paramHttpServletRequest, EngineTarget paramEngineTarget) {
    Validate.notNull(paramHttpServletRequest, "The sourceRequest must be not null");
    String str1 = StringUtils.trimToEmpty(this.a.resolve(paramHttpServletRequest));
    String str2 = UserAgentUtils.getUaProfile(paramHttpServletRequest);
    return new DefaultWURFLRequest(str1, str2, this.b, UserAgentUtils.getHeaders(paramHttpServletRequest), this.c, paramEngineTarget);
  }
  
  public WURFLRequest createRequest(String paramString, EngineTarget paramEngineTarget) {
    paramString = StringUtils.trimToEmpty(paramString);
    return new DefaultWURFLRequest(paramString, this.b, this.c, paramEngineTarget);
  }
  
  public WURFLRequest createRequest(String paramString1, String paramString2, EngineTarget paramEngineTarget) {
    paramString1 = StringUtils.trimToEmpty(paramString1);
    return new DefaultWURFLRequest(paramString1, paramString2, this.b, this.c, paramEngineTarget);
  }
  
  public WURFLRequest createRequest(WURFLHeaderProvider paramWURFLHeaderProvider, EngineTarget paramEngineTarget) {
    return new DefaultWURFLRequest(this.b, paramWURFLHeaderProvider, this.c, paramEngineTarget);
  }
  
  public UserAgentPriority getUserAgentPriority() {
    return this.c;
  }
  
  public void setUserAgentPriority(UserAgentPriority paramUserAgentPriority) {
    this.c = paramUserAgentPriority;
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\DefaultWURFLRequestFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
