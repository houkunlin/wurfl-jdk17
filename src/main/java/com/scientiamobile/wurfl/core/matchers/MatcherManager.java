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
   private MatcherChain a;
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

   private MatcherChain a(WURFLModel var1) {
      MatcherChain var2 = new MatcherChain();
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
      SiemensMatcher var3 = new SiemensMatcher();
      SagemMatcher var4 = new SagemMatcher();
      PanasonicMatcher var5 = new PanasonicMatcher();
      QtekMatcher var6 = new QtekMatcher();
      MitsubishiMatcher var7 = new MitsubishiMatcher();
      PhilipsMatcher var8 = new PhilipsMatcher();
      KyoceraMatcher var9 = new KyoceraMatcher();
      AlcatelMatcher var10 = new AlcatelMatcher();
      SharpMatcher var11 = new SharpMatcher();
      BenQMatcher var12 = new BenQMatcher();
      ToshibaMatcher var13 = new ToshibaMatcher();
      GrundigMatcher var14 = new GrundigMatcher();
      PortalmmmMatcher var15 = new PortalmmmMatcher();
      KonquerorMatcher var16 = new KonquerorMatcher();
      CatchAllMozillaMatcher var17 = new CatchAllMozillaMatcher(var1);
      CatchAllRISMatcher var18 = new CatchAllRISMatcher();
      FirefoxMatcher var19 = new FirefoxMatcher(this.h, var1);
      MSIEMatcher var21 = new MSIEMatcher(var1);
      OperaMatcher var22 = new OperaMatcher(this.l, var1);
      SafariMatcher var23 = new SafariMatcher(this.i, var1);
      var2.addMatcher(new SmartTvMatcher(var1));
      var2.addMatcher(new KindleMatcher(var1));
      var2.addMatcher(new UcwebU3Matcher(new UcwebU3Normalizer(), var1));
      var2.addMatcher(new UcwebU2Matcher(new UcwebU2Normalizer(), var1));
      var2.addMatcher(new EmailClientUserAgentMatcher(new ThunderbirdNormalizer(), var1));
      var2.addMatcher(new WindowsPhoneMatcher(this.c, var1));
      var2.addMatcher(new OperaMiniOnAndroidMatcher(var1));
      var2.addMatcher(new OperaMobiOrTabletOnAndroidMatcher(this.d, var1));
      var2.addMatcher(new FennecOnAndroidMatcher(var1));
      var2.addMatcher(new UCWEB7OnAndroidMatcher(var1));
      var2.addMatcher(new NetFrontOnAndroidMatcher(var1));
      var2.addMatcher(new AndroidMatcher(this.e, var1));
      var2.addMatcher(new UbuntuTouchOSMatcher(var1));
      var2.addMatcher(new TizenMatcher(var1));
      var2.addMatcher(new AppleMatcher(new AppleNormalizer(), var1));
      var2.addMatcher(new NokiaOviBrowserMatcher(var1));
      var2.addMatcher(new NokiaMatcher(var1));
      var2.addMatcher(new SamsungMatcher(var1));
      var2.addMatcher(new BlackBerryMatcher(var1));
      var2.addMatcher(new SonyEricssonMatcher(var1));
      var2.addMatcher(new MotorolaMatcher(var1));
      var2.addMatcher(var10);
      var2.addMatcher(var12);
      var2.addMatcher(new DoCoMoMatcher(var1));
      var2.addMatcher(var14);
      var2.addMatcher(new HTCMacMatcher(this.j, var1));
      var2.addMatcher(new HTCMatcher(var1));
      var2.addMatcher(new KDDIMatcher(var1));
      var2.addMatcher(var9);
      var2.addMatcher(new LGMatcher(this.f, var1));
      var2.addMatcher(new LGUPLUSMatcher(var1));
      var2.addMatcher(new MaemoMatcher(this.g, var1));
      var2.addMatcher(var7);
      var2.addMatcher(new NecMatcher(var1));
      var2.addMatcher(new NintendoMatcher(var1));
      var2.addMatcher(var5);
      var2.addMatcher(new PantechMatcher(var1));
      var2.addMatcher(var8);
      var2.addMatcher(var15);
      var2.addMatcher(var6);
      var2.addMatcher(new ReksioMatcher(var1));
      var2.addMatcher(var4);
      var2.addMatcher(new SanyoMatcher(var1));
      var2.addMatcher(var11);
      var2.addMatcher(var3);
      var2.addMatcher(new SkyfireMatcher(var1));
      var2.addMatcher(new SPVMatcher(var1));
      var2.addMatcher(var13);
      var2.addMatcher(new VodafoneMatcher(var1));
      var2.addMatcher(new WebOSMatcher(this.k, var1));
      var2.addMatcher(new OperaMiniMatcher(new OperaMiniNormalizer(), var1));
      var2.addMatcher(new FirefoxOSMatcher(var1));
      var2.addMatcher(new JavaMidletMatcher(var1));
      var2.addMatcher(new WindowsRTMatcher(var1));
      var2.addMatcher(new BotMatcher(var1));
      var2.addMatcher(new XBoxMatcher(var1));
      var2.addMatcher(new DesktopApplicationMatcher(var1));
      var2.addMatcher(var21);
      var2.addMatcher(var22);
      var2.addMatcher(new ChromeMatcher(new ChromeNormalizer(), var1));
      var2.addMatcher(var19);
      var2.addMatcher(var23);
      var2.addMatcher(var16);
      var2.addMatcher(var17);
      var2.addMatcher(var18);
      new OrphanDeviceIdMatcher(var1);
      List var25 = var1.getAllDevicesAsList();
      MatcherChain var24 = var2;
      Validate.notNull(var25);
      this.b.info("model devices: " + var25.size());
      int var27 = 0;

      for(Iterator var26 = var25.iterator(); var26.hasNext(); ++var27) {
         ModelDevice var28;
         String var29 = (var28 = (ModelDevice)var26.next()).getUserAgent();
         String var31 = var28.getID();
         WURFLRequest var30;
         (var30 = (new DefaultWURFLRequestFactory()).createRequest((String)var29, (EngineTarget)null)).performGenericNormalization();
         if (!var24.recordMatch(var30, var31)) {
            throw new UnsupportedOperationException("no filter found for " + var31 + "; device=" + var28 + "; count was " + var27);
         }
      }

      var24.sortAll();
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
