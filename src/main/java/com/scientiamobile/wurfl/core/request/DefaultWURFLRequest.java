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
  private static final String[] a = new String[] { "Device-Stock-UA", "X-OperaMini-Phone-UA", "X-UCBrowser-Device-UA", "User-Agent" };
  
  private static final long serialVersionUID = 100L;
  
  private String b;
  
  private String c;
  
  private String d;
  
  private String e;
  
  private UserAgentPriority f;
  
  private String g;
  
  private final EngineTarget engineTarget;
  
  private final Map h;
  
  private Boolean i = null;
  
  private Boolean j = null;
  
  private Boolean k = null;
  
  private Boolean l = null;
  
  private Boolean m = null;
  
  private Boolean n = null;
  
  private Boolean o = null;
  
  private Boolean p = null;
  
  private UserAgentNormalizer q;
  
  private boolean r;
  
  public DefaultWURFLRequest(String paramString, UserAgentNormalizer paramUserAgentNormalizer, UserAgentPriority paramUserAgentPriority, EngineTarget paramEngineTarget) {
    this(paramString, null, paramUserAgentNormalizer, paramUserAgentPriority, paramEngineTarget);
  }
  
  public DefaultWURFLRequest(String paramString1, String paramString2, UserAgentNormalizer paramUserAgentNormalizer, UserAgentPriority paramUserAgentPriority, EngineTarget paramEngineTarget) {
    this(paramString1, paramString2, paramUserAgentNormalizer, new HashMap<Object, Object>(), paramUserAgentPriority, paramEngineTarget);
  }
  
  public DefaultWURFLRequest(String paramString1, String paramString2, UserAgentNormalizer paramUserAgentNormalizer, Map paramMap, UserAgentPriority paramUserAgentPriority, EngineTarget paramEngineTarget) {
    this.f = paramUserAgentPriority;
    this.engineTarget = paramEngineTarget;
    this.e = a((String)paramMap.get("User-Agent"));
    if (this.e == null && paramString1 != null)
      this.e = a(paramString1); 
    this.b = a(paramMap);
    if (this.b == null)
      this.b = this.e; 
    this.g = paramString2;
    this.h = paramMap;
    this.q = paramUserAgentNormalizer;
    this.c = getOriginalUserAgent();
    this.r = (UserAgentUtils.isRawUrlEncoded(paramString1) || UserAgentUtils.hasIIsLoggingStyle(paramString1));
    a();
  }
  
  public DefaultWURFLRequest(UserAgentNormalizer paramUserAgentNormalizer, WURFLHeaderProvider paramWURFLHeaderProvider, UserAgentPriority paramUserAgentPriority, EngineTarget paramEngineTarget) {
    this.f = paramUserAgentPriority;
    this.engineTarget = paramEngineTarget;
    this.e = a(paramWURFLHeaderProvider.getHeader("User-Agent"));
    this.b = a(paramWURFLHeaderProvider);
    if (this.b == null)
      this.b = this.e; 
    this.g = UserAgentUtils.getUaProfile(paramWURFLHeaderProvider);
    this.h = new HashMap<Object, Object>();
    Enumeration<String> enumeration = paramWURFLHeaderProvider.getHeaderNames();
    while (enumeration.hasMoreElements()) {
      String str = enumeration.nextElement();
      this.h.put(str, paramWURFLHeaderProvider.getHeader(str));
    } 
    this.q = paramUserAgentNormalizer;
    this.c = getOriginalUserAgent();
    this.r = (UserAgentUtils.isRawUrlEncoded(this.b) || UserAgentUtils.hasIIsLoggingStyle(this.b));
    a();
  }
  
  private void a() {
    if (this.h.size() > 0 && this.h.containsKey("X-UCBrowser-Device-UA"))
      this.e = this.b; 
  }
  
  private static String a(WURFLHeaderProvider paramWURFLHeaderProvider) {
    for (byte b = 0; b < 4; b++) {
      String str;
      if ((str = paramWURFLHeaderProvider.getHeader(a[b])) != null)
        return a(str); 
    } 
    return null;
  }
  
  private static String a(Map paramMap) {
    for (byte b = 0; b < 4; b++) {
      String str;
      if ((str = (String)paramMap.get(a[b])) != null)
        return a(str); 
    } 
    return null;
  }
  
  private static String a(String paramString) {
    return (paramString == null || paramString.length() <= 255) ? paramString : paramString.substring(0, 255);
  }
  
  public String getDeviceUserAgent() {
    return this.b;
  }
  
  public String getCleanedDeviceUserAgent() {
    return this.c;
  }
  
  public String getNormalizedDeviceUserAgent() {
    return this.d;
  }
  
  public String getBrowserUserAgent() {
    return this.e;
  }
  
  public String getOriginalUserAgent() {
    return (this.f == UserAgentPriority.OverrideSideloadedBrowserUserAgent) ? getDeviceUserAgent() : getBrowserUserAgent();
  }
  
  public void normalizeUserAgent(UserAgentNormalizer paramUserAgentNormalizer) {
    if (paramUserAgentNormalizer == null) {
      this.d = this.c;
      return;
    } 
    this.d = paramUserAgentNormalizer.normalize(this.c);
  }
  
  public boolean isUrlEncoded() {
    return this.r;
  }
  
  public void setUrlEncoded(boolean paramBoolean) {
    this.r = paramBoolean;
  }
  
  public void performGenericNormalization() {
    if (this.q != null)
      this.c = this.q.normalize(getOriginalUserAgent()); 
  }
  
  public String getUserAgentProfile() {
    return this.g;
  }
  
  public String getHeader(String paramString) {
    return (String)this.h.get(paramString);
  }
  
  public Map getHeaders() {
    return Collections.unmodifiableMap(this.h);
  }
  
  public EngineTarget getEngineTarget() {
    return this.engineTarget;
  }
  
  public boolean _internalIsMobileBrowser() {
    if (this.i == null)
      if (_internalIsDesktopBrowser()) {
        this.i = Boolean.valueOf(false);
      } else if (mobileKeywordsDetected()) {
        this.i = Boolean.valueOf(true);
      } else {
        this.i = Boolean.valueOf(screenSizeDetected());
      }  
    return this.i.booleanValue();
  }
  
  public boolean mobileKeywordsDetected() {
    if (this.j == null)
      this.j = Boolean.valueOf(UserAgentUtils.mobileKeywordsDetected(this.c)); 
    return this.j.booleanValue();
  }
  
  public boolean screenSizeDetected() {
    if (this.k == null)
      this.k = Boolean.valueOf(UserAgentUtils.screenSizeDetected(this.c)); 
    return this.k.booleanValue();
  }
  
  public boolean _internalIsDesktopBrowser() {
    if (this.m == null)
      this.m = Boolean.valueOf(UserAgentUtils.isDesktopBrowser(this.c)); 
    return this.m.booleanValue();
  }
  
  public boolean _internalIsSmartTvBrowser() {
    if (this.o == null)
      this.o = Boolean.valueOf(UserAgentUtils.isSmartTvBrowser(this.c)); 
    return this.o.booleanValue();
  }
  
  public boolean _internalIsBot() {
    if (this.l == null)
      this.l = Boolean.valueOf(UserAgentUtils.isBot(getOriginalUserAgent())); 
    return this.l.booleanValue();
  }
  
  public boolean _internalIsDesktopBrowserHeavyDutyAnalysis() {
    if (this.n == null)
      if (_internalIsSmartTvBrowser()) {
        this.n = Boolean.valueOf(false);
      } else if (StringMatchUtils.containsAllOf(this.c, new String[] { "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/" })) {
        this.n = Boolean.valueOf(false);
      } else if (this.c.contains("Chrome") && !StringMatchUtils.containsAnyOf(this.c, new String[] { "Android", "Ventana", "android", "Tizen" })) {
        this.n = Boolean.valueOf(true);
      } else if (mobileKeywordsDetected()) {
        this.n = Boolean.valueOf(false);
      } else if (this.c.contains("PPC")) {
        this.n = Boolean.valueOf(false);
      } else if (this.c.contains("Firefox") && !this.c.contains("Tablet")) {
        this.n = Boolean.valueOf(true);
      } else if (UserAgentUtils.isDesktopPattern(this.c)) {
        this.n = Boolean.valueOf(true);
      } else if (this.c.startsWith("Opera/9.80 (Windows NT") || this.c.startsWith("Opera/9.80 (Macintosh")) {
        this.n = Boolean.valueOf(true);
      } else if (_internalIsDesktopBrowser()) {
        this.n = Boolean.valueOf(true);
      } else {
        this.n = Boolean.valueOf(UserAgentUtils.isIEPattern(this.c));
      }  
    return this.n.booleanValue();
  }
  
  public boolean _internalIsEmailClient() {
    if (this.p == null)
      this.p = Boolean.valueOf(StringMatchUtils.containsAnyOf(this.c, EmailClientUserAgentMatcher.EMAIL_CLIENTS)); 
    return this.p.booleanValue();
  }
  
  public int hashCode() {
    HashCodeBuilder hashCodeBuilder;
    (hashCodeBuilder = new HashCodeBuilder(35, 79)).append(getClass()).append(this.b).append(this.g).toHashCode();
    return hashCodeBuilder.toHashCode();
  }
  
  public boolean equals(Object paramObject) {
    EqualsBuilder equalsBuilder;
    (equalsBuilder = new EqualsBuilder()).appendSuper(getClass().isInstance(paramObject));
    if (equalsBuilder.isEquals()) {
      paramObject = paramObject;
      equalsBuilder.append(this.b, ((DefaultWURFLRequest)paramObject).b);
      equalsBuilder.append(this.g, ((DefaultWURFLRequest)paramObject).g);
    } 
    return equalsBuilder.isEquals();
  }
  
  public String toString() {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append("[userAgent: ").append(getOriginalUserAgent()).append(", userAgentProfile: ").append(this.g).append("]");
    return stringBuilder.toString();
  }
}


/* Location:              D:\workspace\houkunlin\wurfl-jdk17\libs\wurfl-1.9.0.0.jar!\com\scientiamobile\wurfl\core\request\DefaultWURFLRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */
