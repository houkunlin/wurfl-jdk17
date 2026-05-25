package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.HTCMacNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.WebOSNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.AndroidNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.AppleNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.ChromeNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.FirefoxNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.LGNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.MaemoNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaMiniNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaMobiOrTabletOnAndroidNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.OperaNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.SafariNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.ThunderbirdNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU2Normalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.UcwebU3Normalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.WindowsPhoneNormalizer;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MatcherManager {
   private S a;
   private final transient Logger b = LoggerFactory.getLogger(this.getClass());
   private UserAgentNormalizer c;
   private UserAgentNormalizer d;
   private UserAgentNormalizer e;
   private UserAgentNormalizer f;
   private UserAgentNormalizer g;
   private UserAgentNormalizer h;
   private UserAgentNormalizer i;
   private UserAgentNormalizer j;
   private UserAgentNormalizer k;
   private UserAgentNormalizer l;

   public MatcherManager(WURFLModel var1) {
      this.a = this.a(var1);
   }

   private S a(WURFLModel var1) {
      S var2 = new S();
      this.f = new LGNormalizer();
      this.e = new AndroidNormalizer();
      this.d = new OperaMobiOrTabletOnAndroidNormalizer();
      this.h = new FirefoxNormalizer();
      this.i = new SafariNormalizer();
      this.c = new WindowsPhoneNormalizer();
      this.g = new MaemoNormalizer();
      this.j = new HTCMacNormalizer();
      this.k = new WebOSNormalizer();
      this.l = new OperaNormalizer();
      T var3 = new T();
      Z var4 = new Z();
      aa var5 = new aa();
      ab var6 = new ab();
      ac var7 = new ac();
      ad var8 = new ad();
      ae var9 = new ae();
      af var10 = new af();
      ag var11 = new ag();
      U var12 = new U();
      V var13 = new V();
      W var14 = new W();
      X var15 = new X();
      Y var16 = new Y();
      CatchAllMozillaMatcher var17 = new CatchAllMozillaMatcher(var1);
      CatchAllRISMatcher var18 = new CatchAllRISMatcher();
      H var19 = new H(this.h, var1);
      M var20 = new M(var1);
      Q var21 = new Q(var1);
      an var22 = new an(this.l, var1);
      e var23 = new e(this.i, var1);
      var2.a(new i(var1));
      var2.a(new N(var1));
      var2.a(new m(new UcwebU3Normalizer(), var1));
      var2.a(new l(new UcwebU2Normalizer(), var1));
      var2.a(new EmailClientUserAgentMatcher(new ThunderbirdNormalizer(), var1));
      var2.a(new WindowsPhoneMatcher(this.c, var1));
      var2.a(new ap(var1));
      var2.a(new aq(this.d, var1));
      var2.a(new E(var1));
      var2.a(new k(var1));
      var2.a(new aj(var1));
      var2.a(new AndroidMatcher(this.e, var1));
      var2.a(new UbuntuTouchOSMatcher(var1));
      var2.a(new TizenMatcher(var1));
      var2.a(new AppleMatcher(new AppleNormalizer(), var1));
      var2.a(new am(var1));
      var2.a(new al(var1));
      var2.a(new f(var1));
      var2.a(new BlackBerryMatcher(var1));
      var2.a(new j(var1));
      var2.a(new ah(var1));
      var2.a(var10);
      var2.a(var12);
      var2.a(new D(var1));
      var2.a(var14);
      var2.a(new J(this.j, var1));
      var2.a(new K(var1));
      var2.a(var20);
      var2.a(var9);
      var2.a(new O(this.f, var1));
      var2.a(new P(var1));
      var2.a(new R(this.g, var1));
      var2.a(var7);
      var2.a(new ai(var1));
      var2.a(new ak(var1));
      var2.a(var5);
      var2.a(new b(var1));
      var2.a(var8);
      var2.a(var15);
      var2.a(var6);
      var2.a(new c(var1));
      var2.a(var4);
      var2.a(new g(var1));
      var2.a(var11);
      var2.a(var3);
      var2.a(new h(var1));
      var2.a(new d(var1));
      var2.a(var13);
      var2.a(new n(var1));
      var2.a(new WebOSMatcher(this.k, var1));
      var2.a(new ao(new OperaMiniNormalizer(), var1));
      var2.a(new I(var1));
      var2.a(new L(var1));
      var2.a(new WindowsRTMatcher(var1));
      var2.a(new BotMatcher(var1));
      var2.a(new XBoxMatcher(var1));
      var2.a(new DesktopApplicationMatcher(var1));
      var2.a(var21);
      var2.a(var22);
      var2.a(new B(new ChromeNormalizer(), var1));
      var2.a(var19);
      var2.a(var23);
      var2.a(var16);
      var2.a(var17);
      var2.a(var18);
      new OrphanDeviceIdMatcher(var1);
      List var25 = var1.getAllDevicesAsList();
      S var24 = var2;
      Validate.notNull(var25);
      this.b.info("model devices: " + var25.size());
      int var27 = 0;

      for(Iterator var26 = var25.iterator(); var26.hasNext(); ++var27) {
         ModelDevice var28;
         String var29 = (var28 = (ModelDevice)var26.next()).getUserAgent();
         String var31 = var28.getID();
         WURFLRequest var30;
         (var30 = (new DefaultWURFLRequestFactory()).createRequest((String)var29, (EngineTarget)null)).performGenericNormalization();
         if (!var24.a(var30, var31)) {
            throw new UnsupportedOperationException("no filter found for " + var31 + "; device=" + var28 + "; count was " + var27);
         }
      }

      var24.b();
      this.b.info("model devices filtered: " + var27);
      return var2;
   }

   public final void reloadModel(WURFLModel var1) {
      this.b.info("reloading the model");
      if (var1 == null) {
         throw new IllegalArgumentException("no model defined for Matcher Manager");
      } else {
         this.a = this.a(var1);
      }
   }

   public final DeviceInfo matchRequest(WURFLRequest var1) {
      return this.a.match(var1);
   }
}
