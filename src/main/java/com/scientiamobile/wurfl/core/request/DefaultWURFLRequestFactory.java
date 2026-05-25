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
      return new UserAgentNormalizerChain(new UserAgentNormalizer[]{new UCWebNormalizer(), new UPLinkNormalizer(), new SerialNumberNormalizer(), new LocaleNormalizer(), new CFNetworkNormalizer(), new BlackBerryNormalizer(), new GenericAndroidNormalizer(), new TransferEncodingNormalizer()});
   }

   public DefaultWURFLRequestFactory() {
      this((UserAgentNormalizer)a());
   }

   public DefaultWURFLRequestFactory(UserAgentResolver var1) {
      this((UserAgentResolver)var1, (UserAgentNormalizer)a());
   }

   public DefaultWURFLRequestFactory(UserAgentNormalizer var1) {
      this((UserAgentResolver)(new HttpServletRequestUserAgentResolver()), (UserAgentNormalizer)var1);
   }

   public DefaultWURFLRequestFactory(UserAgentResolver var1, UserAgentNormalizer var2) {
      this(var1, var2, UserAgentPriority.OverrideSideloadedBrowserUserAgent);
   }

   public DefaultWURFLRequestFactory(UserAgentPriority var1) {
      this((UserAgentNormalizer)a(), (UserAgentPriority)var1);
   }

   public DefaultWURFLRequestFactory(UserAgentResolver var1, UserAgentPriority var2) {
      this(var1, a(), var2);
   }

   public DefaultWURFLRequestFactory(UserAgentNormalizer var1, UserAgentPriority var2) {
      this(new HttpServletRequestUserAgentResolver(), var1, var2);
   }

   public DefaultWURFLRequestFactory(UserAgentResolver var1, UserAgentNormalizer var2, UserAgentPriority var3) {
      LoggerFactory.getLogger(DefaultWURFLRequestFactory.class);
      Validate.notNull(var1, "userAgentResolver is null");
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public WURFLRequest createRequest(HttpServletRequest var1, EngineTarget var2) {
      Validate.notNull(var1, "The sourceRequest must be not null");
      String var3 = StringUtils.trimToEmpty(this.a.resolve(var1));
      String var4 = UserAgentUtils.getUaProfile(var1);
      return new DefaultWURFLRequest(var3, var4, this.b, UserAgentUtils.getHeaders(var1), this.c, var2);
   }

   public WURFLRequest createRequest(String var1, EngineTarget var2) {
      var1 = StringUtils.trimToEmpty(var1);
      return new DefaultWURFLRequest(var1, this.b, this.c, var2);
   }

   public WURFLRequest createRequest(String var1, String var2, EngineTarget var3) {
      var1 = StringUtils.trimToEmpty(var1);
      return new DefaultWURFLRequest(var1, var2, this.b, this.c, var3);
   }

   public WURFLRequest createRequest(WURFLHeaderProvider var1, EngineTarget var2) {
      return new DefaultWURFLRequest(this.b, var1, this.c, var2);
   }

   public UserAgentPriority getUserAgentPriority() {
      return this.c;
   }

   public void setUserAgentPriority(UserAgentPriority var1) {
      this.c = var1;
   }
}
