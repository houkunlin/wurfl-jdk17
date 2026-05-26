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
   private static final String[] a = new String[]{"Device-Stock-UA", "X-OperaMini-Phone-UA", "X-UCBrowser-Device-UA", "User-Agent"};
   private static final long serialVersionUID = 100L;
   private String b;
   private String c;
   private String d;
   private String e;
   private UserAgentPriority f;
   private String g;
   private final EngineTarget engineTarget;
   private final Map<String, String> h;
   private Boolean i;
   private Boolean j;
   private Boolean k;
   private Boolean l;
   private Boolean m;
   private Boolean n;
   private Boolean o;
   private Boolean p;
   private UserAgentNormalizer q;
   private boolean r;

   public DefaultWURFLRequest(String var1, UserAgentNormalizer var2, UserAgentPriority var3, EngineTarget var4) {
      this(var1, (String)null, var2, var3, var4);
   }

   public DefaultWURFLRequest(String var1, String var2, UserAgentNormalizer var3, UserAgentPriority var4, EngineTarget var5) {
      this(var1, var2, var3, new HashMap<>(), var4, var5);
   }

   public DefaultWURFLRequest(String var1, String var2, UserAgentNormalizer var3, Map<String, String> var4, UserAgentPriority var5, EngineTarget var6) {
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = null;
      this.p = null;
      this.f = var5;
      this.engineTarget = var6;
      this.e = a((String)var4.get("User-Agent"));
      if (this.e == null && var1 != null) {
         this.e = a(var1);
      }

      this.b = a(var4);
      if (this.b == null) {
         this.b = this.e;
      }

      this.g = var2;
      this.h = var4;
      this.q = var3;
      this.c = this.getOriginalUserAgent();
      this.r = UserAgentUtils.isRawUrlEncoded(var1) || UserAgentUtils.hasIIsLoggingStyle(var1);
      this.a();
   }

   public DefaultWURFLRequest(UserAgentNormalizer var1, WURFLHeaderProvider var2, UserAgentPriority var3, EngineTarget var4) {
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = null;
      this.p = null;
      this.f = var3;
      this.engineTarget = var4;
      this.e = a(var2.getHeader("User-Agent"));
      this.b = a(var2);
      if (this.b == null) {
         this.b = this.e;
      }

      this.g = UserAgentUtils.getUaProfile(var2);
      this.h = new HashMap<>();
      Enumeration var5 = var2.getHeaderNames();

      while(var5.hasMoreElements()) {
         String var6 = (String)var5.nextElement();
         this.h.put(var6, var2.getHeader(var6));
      }

      this.q = var1;
      this.c = this.getOriginalUserAgent();
      this.r = UserAgentUtils.isRawUrlEncoded(this.b) || UserAgentUtils.hasIIsLoggingStyle(this.b);
      this.a();
   }

   private void a() {
      if (this.h.size() > 0 && this.h.containsKey("X-UCBrowser-Device-UA")) {
         this.e = this.b;
      }

   }

   private static String a(WURFLHeaderProvider var0) {
      for(int var1 = 0; var1 < 4; ++var1) {
         String var2;
         if ((var2 = var0.getHeader(a[var1])) != null) {
            return a(var2);
         }
      }

      return null;
   }

   private static String a(Map<String, String> var0) {
      for(int var1 = 0; var1 < 4; ++var1) {
         String var2;
         if ((var2 = var0.get(a[var1])) != null) {
            return a(var2);
         }
      }

      return null;
   }

   private static String a(String var0) {
      return var0 != null && var0.length() > 255 ? var0.substring(0, 255) : var0;
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
      return this.f == UserAgentPriority.OverrideSideloadedBrowserUserAgent ? this.getDeviceUserAgent() : this.getBrowserUserAgent();
   }

   public void normalizeUserAgent(UserAgentNormalizer var1) {
      if (var1 == null) {
         this.d = this.c;
      } else {
         this.d = var1.normalize(this.c);
      }
   }

   public boolean isUrlEncoded() {
      return this.r;
   }

   public void setUrlEncoded(boolean var1) {
      this.r = var1;
   }

   public void performGenericNormalization() {
      if (this.q != null) {
         this.c = this.q.normalize(this.getOriginalUserAgent());
      }

   }

   public String getUserAgentProfile() {
      return this.g;
   }

   public String getHeader(String var1) {
      return this.h.get(var1);
   }

   public Map getHeaders() {
      return Collections.unmodifiableMap(this.h);
   }

   public EngineTarget getEngineTarget() {
      return this.engineTarget;
   }

   public boolean _internalIsMobileBrowser() {
      if (this.i == null) {
         if (this._internalIsDesktopBrowser()) {
            this.i = false;
         } else if (this.mobileKeywordsDetected()) {
            this.i = true;
         } else {
            this.i = this.screenSizeDetected();
         }
      }

      return this.i;
   }

   public boolean mobileKeywordsDetected() {
      if (this.j == null) {
         this.j = UserAgentUtils.mobileKeywordsDetected(this.c);
      }

      return this.j;
   }

   public boolean screenSizeDetected() {
      if (this.k == null) {
         this.k = UserAgentUtils.screenSizeDetected(this.c);
      }

      return this.k;
   }

   public boolean _internalIsDesktopBrowser() {
      if (this.m == null) {
         this.m = UserAgentUtils.isDesktopBrowser(this.c);
      }

      return this.m;
   }

   public boolean _internalIsSmartTvBrowser() {
      if (this.o == null) {
         this.o = UserAgentUtils.isSmartTvBrowser(this.c);
      }

      return this.o;
   }

   public boolean _internalIsBot() {
      if (this.l == null) {
         this.l = UserAgentUtils.isBot(this.getOriginalUserAgent());
      }

      return this.l;
   }

   public boolean _internalIsDesktopBrowserHeavyDutyAnalysis() {
      if (this.n == null) {
         if (this._internalIsSmartTvBrowser()) {
            this.n = false;
         } else if (StringMatchUtils.containsAllOf(this.c, "Mozilla/5.0 (Windows NT ", " ARM;", " Edge/")) {
            this.n = false;
         } else if (this.c.contains("Chrome") && !StringMatchUtils.containsAnyOf(this.c, "Android", "Ventana", "android", "Tizen")) {
            this.n = true;
         } else if (this.mobileKeywordsDetected()) {
            this.n = false;
         } else if (this.c.contains("PPC")) {
            this.n = false;
         } else if (this.c.contains("Firefox") && !this.c.contains("Tablet")) {
            this.n = true;
         } else if (UserAgentUtils.isDesktopPattern(this.c)) {
            this.n = true;
         } else if (!this.c.startsWith("Opera/9.80 (Windows NT") && !this.c.startsWith("Opera/9.80 (Macintosh")) {
            if (this._internalIsDesktopBrowser()) {
               this.n = true;
            } else {
               this.n = UserAgentUtils.isIEPattern(this.c);
            }
         } else {
            this.n = true;
         }
      }

      return this.n;
   }

   public boolean _internalIsEmailClient() {
      if (this.p == null) {
         this.p = StringMatchUtils.containsAnyOf(this.c, EmailClientUserAgentMatcher.EMAIL_CLIENTS);
      }

      return this.p;
   }

   public int hashCode() {
      HashCodeBuilder var1;
      (var1 = new HashCodeBuilder(35, 79)).append(this.getClass()).append(this.b).append(this.g).toHashCode();
      return var1.toHashCode();
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof DefaultWURFLRequest)) {
         return false;
      } else {
         DefaultWURFLRequest other = (DefaultWURFLRequest)var1;
         return (new EqualsBuilder()).append(this.b, other.b).append(this.g, other.g).isEquals();
      }
   }

   public String toString() {
      StringBuilder var1;
      (var1 = new StringBuilder()).append("[userAgent: ").append(this.getOriginalUserAgent()).append(", userAgentProfile: ").append(this.g).append("]");
      return var1.toString();
   }
}
