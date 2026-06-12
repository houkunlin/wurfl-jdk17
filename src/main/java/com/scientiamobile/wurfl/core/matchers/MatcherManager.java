package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.DeviceInfo;
import com.scientiamobile.wurfl.core.EngineTarget;
import com.scientiamobile.wurfl.core.request.DefaultWURFLRequestFactory;
import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.HTCMacNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.generic.WebOSNormalizer;
import com.scientiamobile.wurfl.core.request.normalizer.specific.*;
import com.scientiamobile.wurfl.core.resource.ModelDevice;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 匹配器管理器，负责构建和管理匹配器链。
 * <p>该类是 WURFL 引擎的核心组件，负责创建所有内置匹配器、初始化规范化器、加载模型数据到匹配器索引中。</p>
 */

public final class MatcherManager {
    private static final Logger log = LoggerFactory.getLogger(MatcherManager.class);
    /**
     * 匹配器链，按优先级顺序排列所有匹配器
     */
    private MatcherChain matcherChain;

    /**
     * 使用 WURFL 模型构造匹配器管理器，自动构建匹配器链。
     *
     * @param model WURFL 设备模型
     */
    public MatcherManager(WURFLModel model) {
        this.matcherChain = this.buildMatcherChain(model);
    }

    /**
     * 构建匹配器链，创建所有内置匹配器并建立优先级顺序。
     */

    private MatcherChain buildMatcherChain(WURFLModel model) {
        MatcherChain chain = new MatcherChain();
        /*
         * LG 设备的 User-Agent 规范化器
         */
        UserAgentNormalizer lgNormalizer = new LGNormalizer();
        /*
         * Android 的 User-Agent 规范化器
         */
        UserAgentNormalizer androidNormalizer = new AndroidNormalizer();
        /*
         * Opera Mobile/Tablet on Android 的 User-Agent 规范化器
         */
        UserAgentNormalizer operaMobiOrTabletOnAndroidNormalizer = new OperaMobiOrTabletOnAndroidNormalizer();
        /*
         * Firefox 的 User-Agent 规范化器
         */
        UserAgentNormalizer firefoxNormalizer = new FirefoxNormalizer();
        /*
         * Safari 的 User-Agent 规范化器
         */
        UserAgentNormalizer safariNormalizer = new SafariNormalizer();
        /*
         * Windows Phone 的 User-Agent 规范化器
         */
        UserAgentNormalizer windowsPhoneNormalizer = new WindowsPhoneNormalizer();
        /*
         * Maemo 设备的 User-Agent 规范化器
         */
        UserAgentNormalizer maemoNormalizer = new MaemoNormalizer();
        /*
         * HTC MAC 设备的 User-Agent 规范化器
         */
        UserAgentNormalizer htcMacNormalizer = new HTCMacNormalizer();
        /*
         * WebOS 的 User-Agent 规范化器
         */
        UserAgentNormalizer webOSNormalizer = new WebOSNormalizer();
        /*
         * Opera 的 User-Agent 规范化器
         */
        UserAgentNormalizer operaNormalizer = new OperaNormalizer();
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
        FirefoxMatcher firefoxMatcher = new FirefoxMatcher(firefoxNormalizer, model);
        MSIEMatcher msieMatcher = new MSIEMatcher(model);
        OperaMatcher operaMatcher = new OperaMatcher(operaNormalizer, model);
        SafariMatcher safariMatcher = new SafariMatcher(safariNormalizer, model);
        chain.addMatcher(new SmartTvMatcher(model));
        chain.addMatcher(new KindleMatcher(model));
        chain.addMatcher(new UcwebU3Matcher(new UcwebU3Normalizer(), model));
        chain.addMatcher(new UcwebU2Matcher(new UcwebU2Normalizer(), model));
        chain.addMatcher(new EmailClientUserAgentMatcher(new ThunderbirdNormalizer(), model));
        chain.addMatcher(new WindowsPhoneMatcher(windowsPhoneNormalizer, model));
        chain.addMatcher(new OperaMiniOnAndroidMatcher(model));
        chain.addMatcher(new OperaMobiOrTabletOnAndroidMatcher(operaMobiOrTabletOnAndroidNormalizer, model));
        chain.addMatcher(new FennecOnAndroidMatcher(model));
        chain.addMatcher(new UCWEB7OnAndroidMatcher(model));
        chain.addMatcher(new NetFrontOnAndroidMatcher(model));
        chain.addMatcher(new HarmonyOSMatcher(model));
        chain.addMatcher(new AndroidMatcher(androidNormalizer, model));
        chain.addMatcher(new UbuntuTouchOSMatcher(model));
        chain.addMatcher(new TizenMatcher(model));
        chain.addMatcher(new AppleMatcher(new AppleNormalizer(), model));
        chain.addMatcher(new NokiaOviBrowserMatcher(model));
        chain.addMatcher(new NokiaMatcher(model));
        chain.addMatcher(new SamsungMatcher(model));
        chain.addMatcher(new BlackBerryMatcher(model));
        chain.addMatcher(new SonyEricssonMatcher(model));
        chain.addMatcher(new MotorolaMatcher(model));
        chain.addMatcher(alcatelMatcher);
        chain.addMatcher(benqMatcher);
        chain.addMatcher(new DoCoMoMatcher(model));
        chain.addMatcher(grundigMatcher);
        chain.addMatcher(new HTCMacMatcher(htcMacNormalizer, model));
        chain.addMatcher(new HTCMatcher(model));
        chain.addMatcher(new KDDIMatcher(model));
        chain.addMatcher(kyoceraMatcher);
        chain.addMatcher(new LGMatcher(lgNormalizer, model));
        chain.addMatcher(new LGUPLUSMatcher(model));
        chain.addMatcher(new MaemoMatcher(maemoNormalizer, model));
        chain.addMatcher(mitsubishiMatcher);
        chain.addMatcher(new NecMatcher(model));
        chain.addMatcher(new NintendoMatcher(model));
        chain.addMatcher(panasonicMatcher);
        chain.addMatcher(new PantechMatcher(model));
        chain.addMatcher(philipsMatcher);
        chain.addMatcher(portalmmmMatcher);
        chain.addMatcher(qtekMatcher);
        chain.addMatcher(new ReksioMatcher(model));
        chain.addMatcher(sagemMatcher);
        chain.addMatcher(new SanyoMatcher(model));
        chain.addMatcher(sharpMatcher);
        chain.addMatcher(siemensMatcher);
        chain.addMatcher(new SkyfireMatcher(model));
        chain.addMatcher(new SPVMatcher(model));
        chain.addMatcher(toshibaMatcher);
        chain.addMatcher(new VodafoneMatcher(model));
        chain.addMatcher(new WebOSMatcher(webOSNormalizer, model));
        chain.addMatcher(new OperaMiniMatcher(new OperaMiniNormalizer(), model));
        chain.addMatcher(new FirefoxOSMatcher(model));
        chain.addMatcher(new JavaMidletMatcher(model));
        chain.addMatcher(new WindowsRTMatcher(model));
        chain.addMatcher(new BotMatcher(model));
        chain.addMatcher(new XBoxMatcher(model));
        chain.addMatcher(new DesktopApplicationMatcher(model));
        chain.addMatcher(msieMatcher);
        chain.addMatcher(operaMatcher);
        chain.addMatcher(new ChromeMatcher(new ChromeNormalizer(), model));
        chain.addMatcher(firefoxMatcher);
        chain.addMatcher(safariMatcher);
        chain.addMatcher(konquerorMatcher);
        chain.addMatcher(catchAllMozillaMatcher);
        chain.addMatcher(catchAllRISMatcher);
        new OrphanDeviceIdMatcher(model);
        List<ModelDevice> allDevices = model.getAllDevicesAsList();
        Validate.notNull(allDevices, "Model devices list is null");
        log.info("model devices: {}", allDevices.size());
        int filteredDevices = 0;

        for (ModelDevice device : allDevices) {
            String userAgent = device.getUserAgent();
            String deviceId = device.getID();
            WURFLRequest request;
            request = (new DefaultWURFLRequestFactory()).createRequest(userAgent, (EngineTarget) null);
            request.performGenericNormalization();
            if (!chain.recordMatch(request, deviceId)) {
                throw new UnsupportedOperationException("no filter found for " + deviceId + "; device=" + device + "; count was " + filteredDevices);
            }
        }

        chain.sortAll();
        log.info("model devices filtered: {}", filteredDevices);
        return chain;
    }

    /**
     * 热重载 WURFL 模型。
     * <p>当 WURFL 数据更新时调用此方法，会重新构建整个匹配器链以反映最新的设备数据。</p>
     *
     * @param model 新的 WURFL 数据模型
     * @throws IllegalArgumentException 如果模型为 {@code null}
     */

    public final void reloadModel(WURFLModel model) {
        log.info("reloading the model");
        if (model == null) {
            throw new IllegalArgumentException("no model defined for Matcher Manager");
        } else {
            this.matcherChain = this.buildMatcherChain(model);
        }
    }

    /**
     * 匹配请求，返回设备信息。
     * <p>将请求委托给内部的匹配器链执行匹配。</p>
     *
     * @param request WURFL 请求对象
     * @return 匹配到的设备信息
     */

    public final DeviceInfo matchRequest(WURFLRequest request) {
        return this.matcherChain.match(request);
    }
}
