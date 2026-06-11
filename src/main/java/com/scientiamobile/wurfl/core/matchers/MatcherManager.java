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
    /** Windows Phone 的 User-Agent 规范化器 */
    private UserAgentNormalizer windowsPhoneNormalizer;
    /** Opera Mobile/Tablet on Android 的 User-Agent 规范化器 */
    private UserAgentNormalizer operaMobiOrTabletOnAndroidNormalizer;
    /** Android 的 User-Agent 规范化器 */
    private UserAgentNormalizer androidNormalizer;
    /** LG 设备的 User-Agent 规范化器 */
    private UserAgentNormalizer lgNormalizer;
    /** Maemo 设备的 User-Agent 规范化器 */
    private UserAgentNormalizer maemoNormalizer;
    /** Firefox 的 User-Agent 规范化器 */
    private UserAgentNormalizer firefoxNormalizer;
    /** Safari 的 User-Agent 规范化器 */
    private UserAgentNormalizer safariNormalizer;
    /** HTC MAC 设备的 User-Agent 规范化器 */
    private UserAgentNormalizer htcMacNormalizer;
    /** WebOS 的 User-Agent 规范化器 */
    private UserAgentNormalizer webOSNormalizer;
    /** Opera 的 User-Agent 规范化器 */
    private UserAgentNormalizer operaNormalizer;

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
            request = (new DefaultWURFLRequestFactory()).createRequest(userAgent, (EngineTarget) null);
            request.performGenericNormalization();
            if (!matcherChain.recordMatch(request, deviceId)) {
                throw new UnsupportedOperationException("no filter found for " + deviceId + "; device=" + device + "; count was " + filteredDevices);
            }
        }

        matcherChain.sortAll();
        log.info("model devices filtered: {}", filteredDevices);
        return matcherChain;
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
