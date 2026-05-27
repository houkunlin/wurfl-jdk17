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
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MatcherManager {
   private MatcherChain matcherChain;
   private static final Logger log = LoggerFactory.getLogger(MatcherManager.class);
   private UserAgentNormalizer windowsPhoneNormalizer;
   private UserAgentNormalizer operaMobiOrTabletOnAndroidNormalizer;
   private UserAgentNormalizer androidNormalizer;
   private UserAgentNormalizer lgNormalizer;
   private UserAgentNormalizer maemoNormalizer;
   private UserAgentNormalizer firefoxNormalizer;
   private UserAgentNormalizer safariNormalizer;
   private UserAgentNormalizer htcMacNormalizer;
   private UserAgentNormalizer webOSNormalizer;
   private UserAgentNormalizer operaNormalizer;

   public MatcherManager(WURFLModel model) {
      this.matcherChain = this.buildMatcherChain(model);
   }

   private MatcherChain buildMatcherChain(WURFLModel model) {
      MatcherChain matcherChain = new MatcherChain();
      this.lgNormalizer = new LGNormalizer();
      this.androidNormalizer = new AndroidNormalizer();
      this.operaMobiOrTabletOnAndroidNormalizer = new OperaMobiOrTabletOnAndroidNormalizer();
      this.firefoxNormalizer = new FirefoxNormalizer();
      this.safariNormalizer = new SafariNormalizer();
      this.windowsPhoneNormalizer = new WindowsPhoneNormalizer();
      this.maemoNormalizer = new MaemoNormalizer();
      this.htcMacNormalizer = new HTCMacNormalizer();
      this.webOSNormalizer = new WebOSNormalizer();
      this.operaNormalizer = new OperaNormalizer();
      SiemensMatcher siemensMatcher = new SiemensMatcher();
      SagemMatcher sagemMatcher = new SagemMatcher();
      PanasonicMatcher panasonicMatcher = new PanasonicMatcher();
      QtekMatcher qtekMatcher = new QtekMatcher();
      MitsubishiMatcher mitsubishiMatcher = new MitsubishiMatcher();
      PhilipsMatcher philipsMatcher = new PhilipsMatcher();
      KyoceraMatcher kyoceraMatcher = new KyoceraMatcher();
      AlcatelMatcher alcatelMatcher = new AlcatelMatcher();
      SharpMatcher sharpMatcher = new SharpMatcher();
      BenQMatcher benqMatcher = new BenQMatcher();
      ToshibaMatcher toshibaMatcher = new ToshibaMatcher();
      GrundigMatcher grundigMatcher = new GrundigMatcher();
      PortalmmmMatcher portalmmmMatcher = new PortalmmmMatcher();
      KonquerorMatcher konquerorMatcher = new KonquerorMatcher();
      CatchAllMozillaMatcher catchAllMozillaMatcher = new CatchAllMozillaMatcher(model);
      CatchAllRISMatcher catchAllRISMatcher = new CatchAllRISMatcher();
      FirefoxMatcher firefoxMatcher = new FirefoxMatcher(this.firefoxNormalizer, model);
      MSIEMatcher msieMatcher = new MSIEMatcher(model);
      OperaMatcher operaMatcher = new OperaMatcher(this.operaNormalizer, model);
      SafariMatcher safariMatcher = new SafariMatcher(this.safariNormalizer, model);
      matcherChain.addMatcher(new SmartTvMatcher(model));
      matcherChain.addMatcher(new KindleMatcher(model));
      matcherChain.addMatcher(new UcwebU3Matcher(new UcwebU3Normalizer(), model));
      matcherChain.addMatcher(new UcwebU2Matcher(new UcwebU2Normalizer(), model));
      matcherChain.addMatcher(new EmailClientUserAgentMatcher(new ThunderbirdNormalizer(), model));
      matcherChain.addMatcher(new WindowsPhoneMatcher(this.windowsPhoneNormalizer, model));
      matcherChain.addMatcher(new OperaMiniOnAndroidMatcher(model));
      matcherChain.addMatcher(new OperaMobiOrTabletOnAndroidMatcher(this.operaMobiOrTabletOnAndroidNormalizer, model));
      matcherChain.addMatcher(new FennecOnAndroidMatcher(model));
      matcherChain.addMatcher(new UCWEB7OnAndroidMatcher(model));
      matcherChain.addMatcher(new NetFrontOnAndroidMatcher(model));
      matcherChain.addMatcher(new AndroidMatcher(this.androidNormalizer, model));
      matcherChain.addMatcher(new UbuntuTouchOSMatcher(model));
      matcherChain.addMatcher(new TizenMatcher(model));
      matcherChain.addMatcher(new AppleMatcher(new AppleNormalizer(), model));
      matcherChain.addMatcher(new NokiaOviBrowserMatcher(model));
      matcherChain.addMatcher(new NokiaMatcher(model));
      matcherChain.addMatcher(new SamsungMatcher(model));
      matcherChain.addMatcher(new BlackBerryMatcher(model));
      matcherChain.addMatcher(new SonyEricssonMatcher(model));
      matcherChain.addMatcher(new MotorolaMatcher(model));
      matcherChain.addMatcher(alcatelMatcher);
      matcherChain.addMatcher(benqMatcher);
      matcherChain.addMatcher(new DoCoMoMatcher(model));
      matcherChain.addMatcher(grundigMatcher);
      matcherChain.addMatcher(new HTCMacMatcher(this.htcMacNormalizer, model));
      matcherChain.addMatcher(new HTCMatcher(model));
      matcherChain.addMatcher(new KDDIMatcher(model));
      matcherChain.addMatcher(kyoceraMatcher);
      matcherChain.addMatcher(new LGMatcher(this.lgNormalizer, model));
      matcherChain.addMatcher(new LGUPLUSMatcher(model));
      matcherChain.addMatcher(new MaemoMatcher(this.maemoNormalizer, model));
      matcherChain.addMatcher(mitsubishiMatcher);
      matcherChain.addMatcher(new NecMatcher(model));
      matcherChain.addMatcher(new NintendoMatcher(model));
      matcherChain.addMatcher(panasonicMatcher);
      matcherChain.addMatcher(new PantechMatcher(model));
      matcherChain.addMatcher(philipsMatcher);
      matcherChain.addMatcher(portalmmmMatcher);
      matcherChain.addMatcher(qtekMatcher);
      matcherChain.addMatcher(new ReksioMatcher(model));
      matcherChain.addMatcher(sagemMatcher);
      matcherChain.addMatcher(new SanyoMatcher(model));
      matcherChain.addMatcher(sharpMatcher);
      matcherChain.addMatcher(siemensMatcher);
      matcherChain.addMatcher(new SkyfireMatcher(model));
      matcherChain.addMatcher(new SPVMatcher(model));
      matcherChain.addMatcher(toshibaMatcher);
      matcherChain.addMatcher(new VodafoneMatcher(model));
      matcherChain.addMatcher(new WebOSMatcher(this.webOSNormalizer, model));
      matcherChain.addMatcher(new OperaMiniMatcher(new OperaMiniNormalizer(), model));
      matcherChain.addMatcher(new FirefoxOSMatcher(model));
      matcherChain.addMatcher(new JavaMidletMatcher(model));
      matcherChain.addMatcher(new WindowsRTMatcher(model));
      matcherChain.addMatcher(new BotMatcher(model));
      matcherChain.addMatcher(new XBoxMatcher(model));
      matcherChain.addMatcher(new DesktopApplicationMatcher(model));
      matcherChain.addMatcher(msieMatcher);
      matcherChain.addMatcher(operaMatcher);
      matcherChain.addMatcher(new ChromeMatcher(new ChromeNormalizer(), model));
      matcherChain.addMatcher(firefoxMatcher);
      matcherChain.addMatcher(safariMatcher);
      matcherChain.addMatcher(konquerorMatcher);
      matcherChain.addMatcher(catchAllMozillaMatcher);
      matcherChain.addMatcher(catchAllRISMatcher);
      new OrphanDeviceIdMatcher(model);
      List<ModelDevice> allDevices = model.getAllDevicesAsList();
      Validate.notNull(allDevices, "Model devices list is null");
      log.info("model devices: {}", allDevices.size());
      int filteredDevices = 0;

      for (ModelDevice device : allDevices) {
         String userAgent = device.getUserAgent();
         String deviceId = device.getID();
         WURFLRequest request;
         (request = (new DefaultWURFLRequestFactory()).createRequest(userAgent, (EngineTarget)null)).performGenericNormalization();
         if (!matcherChain.recordMatch(request, deviceId)) {
            throw new UnsupportedOperationException("no filter found for " + deviceId + "; device=" + device + "; count was " + filteredDevices);
         }
      }

      matcherChain.sortAll();
      log.info("model devices filtered: {}", filteredDevices);
      return matcherChain;
   }

   public final void reloadModel(WURFLModel model) {
      log.info("reloading the model");
      if (model == null) {
         throw new IllegalArgumentException("no model defined for Matcher Manager");
      } else {
         this.matcherChain = this.buildMatcherChain(model);
      }
   }

   public final DeviceInfo matchRequest(WURFLRequest request) {
      return this.matcherChain.match(request);
   }
}
