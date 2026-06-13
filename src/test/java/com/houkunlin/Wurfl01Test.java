package com.houkunlin;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.VirtualCapabilityInfo;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * WURFL 设备探测引擎的集成测试用例。
 * <p>
 * 该测试类用于验证 WURFL 设备数据库能否正确识别不同的 User-Agent 请求头，
 * 并返回准确的设备型号、操作系统、浏览器类型及各类虚拟能力（Virtual Capability）。
 * 测试覆盖了桌面端 Chrome 浏览器和 Android 移动端（WebView 及第三方浏览器）的识别场景。
 * <p>
 * 测试数据源为 {@code libs/wurfl.zip} 文件，在类加载时初始化为全局引擎实例，
 * 所有测试方法共享同一个引擎实例以节省资源。
 *
 * @author HouKunLin
 */
class Wurfl01Test {
    /**
     * WURFL 设备数据文件路径，包含压缩后的设备描述 XML。
     * 文件位于项目 {@code libs/} 目录下，版本为 1.9.1.0 时期的 wurfl.zip 版本。
     */
    private static final File file = new File("libs/wurfl.zip");

    /**
     * WURFL 引擎实例，负责所有设备探测操作。
     * 该实例在静态初始化块中创建并在 setup 方法中加载数据，供全部测试方法复用。
     */
    private static final GeneralWURFLEngine wurfl;

    /*
     * 静态初始化块：从 ZIP 文件中读取 WURFL XML 资源，构造引擎实例。
     * ZIP 文件直接通过流加载，不对文件体系结构做解压缩处理——XMLResource 内部处理了解析逻辑。
     * 若文件缺失则包装为 RuntimeException 中断测试执行。
     */
    static {
        try {
            wurfl = new GeneralWURFLEngine(new XMLResource(new FileInputStream(file), file.getName()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在所有测试执行之前加载 WURFL 设备数据库。
     * <p>
     * 引擎的 {@code load()} 方法负责将 XML 数据解析到内存中，构建设备树结构。
     * 此操作较为耗时，因此放在类级别一次性完成，避免每个测试方法重复加载。
     */
    @BeforeAll
    static void setup() {
        wurfl.load();
    }

    /**
     * 验证 WURFL 引擎的 API 版本信息。
     * <p>
     * 断言检查包括两个方面：
     * <ul>
     *   <li>引擎的 API 版本号是否等于期望的 {@code 1.9.1.0}</li>
     *   <li>底层数据源的版本字符串应以 {@code "Root:Stream resource:data.scientiamobile.com"} 开头</li>
     * </ul>
     * 该测试用于确保集成的 SDK 版本与数据源版本均符合预期，可作为依赖升级后的回归校验。
     */
    @Test
    void testVersion() {
        Assertions.assertEquals("1.9.1.0", wurfl.getAPIVersion());
        Assertions.assertTrue(wurfl.getWURFLUtils().getVersion().startsWith("Root:Stream resource:data.scientiamobile.com"));
    }

    /**
     * 使用桌面端 Google Chrome 浏览器的 User-Agent 验证设备识别准确性。
     * <p>
     * 测试使用的 User-Agent 模拟了 <b>Windows 10 + Chrome 147</b> 环境，
     * 这是典型的桌面端访问场景。验证点包括：
     * <ul>
     *   <li>设备 ID 应为 {@code google_chrome_147}，标识精确匹配到具体浏览器版本</li>
     *   <li>匹配类型为 {@code conclusive}，表示完全精确匹配而非模糊匹配</li>
     *   <li>桌面端应具备的特征：{@code is_full_desktop} 为 true、{@code is_largescreen} 为 true、{@code form_factor} 为 Desktop</li>
     *   <li>非移动端特征：{@code is_mobile}、{@code is_phone}、{@code is_smartphone}、{@code is_touchscreen} 均为 false</li>
     *   <li>操作系统识别为 Windows、浏览器识别为 Chrome、版本号为 147.0.0.0</li>
     * </ul>
     * 该用例覆盖了 WURFL 对桌面端浏览器的探测准确性。
     */
    @DisplayName("电脑谷歌浏览器")
    @Test
    void testGoogleBrowser() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("google_chrome_147", device.getId());
        Assertions.assertEquals("conclusive", device.getMatchType().name());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Chrome", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertEquals("147.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("147.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("电脑谷歌浏览器: 测试 VirtualCapabilityInfo 转换结果")
    @Test
    void testGoogleBrowser1() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("google_chrome_147", device.getId());
        Assertions.assertTrue(device.getMatchType().name().matches("conclusive|cached"),
                "Expected conclusive or cached but was: " + device.getMatchType().name());
        Assertions.assertEquals(device.getVirtualCapability("is_app_webview"), Boolean.toString(info.isAppWebview()));
        Assertions.assertEquals(device.getVirtualCapability("is_app"), Boolean.toString(info.isApp()));
        Assertions.assertEquals(device.getVirtualCapability("is_mobile"), Boolean.toString(info.isMobile()));
        Assertions.assertEquals(device.getVirtualCapability("is_phone"), Boolean.toString(info.isPhone()));
        Assertions.assertEquals(device.getVirtualCapability("advertised_app_name"), info.getAdvertisedAppName());
        Assertions.assertEquals(device.getVirtualCapability("is_full_desktop"), Boolean.toString(info.isFullDesktop()));
        Assertions.assertEquals(device.getVirtualCapability("advertised_browser"), info.getAdvertisedBrowser());
        Assertions.assertEquals(device.getVirtualCapability("is_smartphone"), Boolean.toString(info.isSmartphone()));
        Assertions.assertEquals(device.getVirtualCapability("is_robot"), Boolean.toString(info.isRobot()));
        Assertions.assertEquals(device.getVirtualCapability("complete_device_name"), info.getCompleteDeviceName());
        Assertions.assertEquals(device.getVirtualCapability("is_largescreen"), Boolean.toString(info.isLargescreen()));
        Assertions.assertEquals(device.getVirtualCapability("advertised_device_os"), info.getAdvertisedDeviceOs());
        Assertions.assertEquals(device.getVirtualCapability("is_android"), Boolean.toString(info.isAndroid()));
        Assertions.assertEquals(device.getVirtualCapability("is_xhtmlmp_preferred"), Boolean.toString(info.isXhtmlmpPreferred()));
        Assertions.assertEquals(device.getVirtualCapability("device_name"), info.getDeviceName());
        Assertions.assertEquals(device.getVirtualCapability("advertised_browser_version"), info.getAdvertisedBrowserVersion());
        Assertions.assertEquals(device.getVirtualCapability("is_html_preferred"), Boolean.toString(info.isHtmlPreferred()));
        Assertions.assertEquals(device.getVirtualCapability("is_windows_phone"), Boolean.toString(info.isWindowsPhone()));
        Assertions.assertEquals(device.getVirtualCapability("is_ios"), Boolean.toString(info.isIos()));
        Assertions.assertEquals(device.getVirtualCapability("is_touchscreen"), Boolean.toString(info.isTouchscreen()));
        Assertions.assertEquals(device.getVirtualCapability("is_wml_preferred"), Boolean.toString(info.isWmlPreferred()));
        Assertions.assertEquals(device.getVirtualCapability("form_factor"), info.getFormFactor());
        Assertions.assertEquals(device.getVirtualCapability("advertised_device_os_version"), info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals(device.getVirtualCapability("browser_core"), info.getBrowserCore());
        Assertions.assertEquals(device.getVirtualCapability("browser_core_version"), info.getBrowserCoreVersion());
    }

    /**
     * 使用 OnePlus PLU110（一加 Turbo 6）手机的 WebView User-Agent 验证设备识别准确性。
     * <p>
     * 此 User-Agent 特征显著：包含 {@code wv} 标记表明运行在 WebView 环境中，
     * 且设备型号 PLU110 为已知的一加机型。验证点包括：
     * <ul>
     *   <li>WebView 环境标记：{@code is_app} 与 {@code is_app_webview} 均为 true</li>
     *   <li>移动设备特征：{@code is_mobile}、{@code is_phone}、{@code is_smartphone} 均为 true</li>
     *   <li>具体设备识别：型号名称为 {@code OnePlus PLU110 (Turbo 6)}，设备名为 {@code OnePlus Turbo 6}</li>
     *   <li>操作系统识别为 Android 16、触摸屏支持为 true、form_factor 为 Smartphone</li>
     *   <li>浏览器并非独立浏览器而是 Chromium WebView 内核</li>
     * </ul>
     * 该用例覆盖了 WURFL 对 Android 原生应用内 WebView 场景的探测能力。
     */
    @DisplayName("一加")
    @Test
    void testOnePlusPLU110() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 16; PLU110 Build/BP2A.250605.015; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/138.0.7204.179 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chromium", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PLU110 (Turbo 6)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus Turbo 6", info.getDeviceName());
        Assertions.assertEquals("138.0.7204.179", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("138.0.7204.179", info.getBrowserCoreVersion());
    }


    /**
     * 使用 OnePlus 12（PJD110）手机的 Quark 浏览器 User-Agent 验证设备识别行为。
     * <p>
     * 该 User-Agent 来自 <b>Quark 浏览器</b>（一款国产轻量级浏览器），其特点是
     * 对自身浏览器信息做了伪装，User-Agent 结构与标准浏览器差异较大。
     * 验证点包括：
     * <ul>
     *   <li>浏览器识别为 Chromium，版本号为 144.0.7559.86</li>
     *   <li>操作系统识别为 Android 16、触摸屏支持为 true</li>
     *   <li>WURFL 已能正确识别此 UA 为 OnePlus PJD110 (12) 设备</li>
     *   <li>{@code is_largescreen} 为 true，符合 OnePlus 12 的 1440×3168 高分辨率屏幕</li>
     *   <li>仍能正确识别出非桌面端、非 iOS、非机器人的移动端基本特征</li>
     * </ul>
     * 该用例用于验证 WURFL 对第三方小众浏览器的兼容性及在 UA 伪装场景下的降级识别策略。
     */
    @DisplayName("手机夸克浏览器：一加12")
    @Test
    void testOnePlus12Quark() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-CN; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.86 Quark/10.10.0.1075 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Quark browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Quark", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PJD110 (12)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 12", info.getDeviceName());
        Assertions.assertEquals("10.10.0.1075", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("144.0.7559.86", info.getBrowserCoreVersion());
    }

    @DisplayName("iPhone3")
    @Test
    void test_iPhone3() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/1A542a Safari/419.3");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Stock Browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Mobile Safari", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPhone", info.getCompleteDeviceName());
        Assertions.assertFalse(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPhone", info.getDeviceName());
        Assertions.assertEquals("3.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("3.0", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("420.1", info.getBrowserCoreVersion());
    }

    @DisplayName("QQ 内置浏览器 UA: Android")
    @Test
    void test_10003() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 11; M2102J2SC Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.72 MQQBrowser/6.2 TBS/045908 Mobile Safari/537.36 V1_AND_SQ_8.8.28_2092_YYB_D A_8082800 QQ/8.8.28.6155 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/91 SimpleUISwitch/1 QQTheme/2921 InMagicWin/0 StudyMode/0 CurrentMode/1 CurrentFontScale/0.9375");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Xiaomi M2102J2SC (Redmi K40 5G Premium Edition)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Xiaomi Redmi K40 5G Premium Edition", info.getDeviceName());
        Assertions.assertEquals("6.2", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("11", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("89.0.4389.72", info.getBrowserCoreVersion());
    }

    @DisplayName("QQ 内置浏览器 UA: iPhone")
    @Test
    void test_10004() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; CPU iPhone OS 15_1_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 QQ/8.8.38.705 V1_IPH_SQ_8.8.38_1_APP_A Pixel/1170 MiniAppEnable SimpleUISwitch/1 StudyMode/0 CurrentMode/1 CurrentFontScale/0.941000 QQTheme/2921 Core/WKWebView Device/Apple(iPhone 12 Pro) NetType/WIFI QBWebViewType/1 WKType/1");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPhone", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPhone", info.getDeviceName());
        Assertions.assertEquals("8.8.38.705", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("15.1.1", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("605.1.15", info.getBrowserCoreVersion());
    }

    @DisplayName("QQ 内置浏览器 UA: iPad")
    @Test
    void test_10005() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPad; U; CPU OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertEquals("Stock Browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Mobile Safari", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPad", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPad", info.getDeviceName());
        Assertions.assertEquals("5.1", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Tablet", info.getFormFactor());
        Assertions.assertEquals("5.0", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("534.46", info.getBrowserCoreVersion());
    }

    @DisplayName("UC 浏览器 (Android)")
    @Test
    void test_10006() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 14; zh-CN; 23116PN5BC Build/UKQ1.230804.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/120.0.0.0 Mobile UCBrowser/13.6.0.1306 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("UCBrowser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("UC Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Xiaomi 23116PN5BC (14 Pro)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Xiaomi 14 Pro", info.getDeviceName());
        Assertions.assertEquals("13.6.0.1306", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("Google Pixel 7")
    @Test
    void test_10007() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Pixel 7", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Pixel 7", info.getDeviceName());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("华为 Mate 30 Pro (HarmonyOS 4.0)")
    @Test
    void test_10008() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; HarmonyOS; HUAWEI LIO-AN00) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Huawei LIO-AL00 (Mate 30 Pro)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Huawei Mate 30 Pro", info.getDeviceName());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("vivo X100 (Funtouch OS 14)")
    @Test
    void test_10009() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; V2309) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Vivo V2309 (X100 Pro)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Vivo X100 Pro", info.getDeviceName());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("鸿蒙 HarmonyOS 平板")
    @Test
    void test_10010() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (HarmonyOS; Tablet; HUAWEI MRX-W09) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/120.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chromium", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Huawei MRX-W09 (MatePad Pro)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Huawei MatePad Pro", info.getDeviceName());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Tablet", info.getFormFactor());
        Assertions.assertEquals("", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("HeyTapBrowser: OnePlus 8T (Android 14)")
    @Test
    void test_10012() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 14; zh-cn; KB2000 Build/UKQ1.230924.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/115.0.5790.168 Mobile Safari/537.36 HeyTapBrowser/40.10.17.1");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertEquals("HeyTap Browser", info.getAdvertisedAppName());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("HeyTap Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus KB2000 (8T)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 8T", info.getDeviceName());
        Assertions.assertEquals("40.10.17.1", info.getAdvertisedBrowserVersion());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("115.0.5790.168", info.getBrowserCoreVersion());
    }

    @DisplayName("华为 Mate X5 (HarmonyOS)")
    @Test
    void test_10011() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (HarmonyOS; Tablet; HUAWEI TET-AN00) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(device.getCapabilities());
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Huawei TET-AN00 (Mate X2)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Huawei Mate X2", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("", info.getAdvertisedDeviceOsVersion());
    }

    @DisplayName("QQ 浏览器 (iOS)")
    @Test
    void test_10013() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; CPU iPhone OS 17_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Mobile/15E148 Safari/604.1 MQQBrowser/9.8.8");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPhone", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPhone", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("9.8.8", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("17.1", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("605.1.15", info.getBrowserCoreVersion());
    }

    @DisplayName("百度浏览器 (Android)")
    @Test
    void test_10014() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; SM-G998B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36 T7/14.3 baidubrowser/13.23.5.10");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Samsung SM-G998B (Galaxy S21 Ultra 5G)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Samsung Galaxy S21 Ultra 5G", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("Win11 谷歌浏览器")
    @Test
    void test_10015() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/149.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("149.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("149.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("Win11 360极速浏览器X")
    @Test
    void test_10016() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.6261.95 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("122.0.6261.95", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("122.0.6261.95", info.getBrowserCoreVersion());
    }

    @DisplayName("Win11 360极速浏览器")
    @Test
    void test_10017() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("86.0.4240.198", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("86.0.4240.198", info.getBrowserCoreVersion());
    }

    @DisplayName("Win11 QQ浏览器")
    @Test
    void test_10018() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.97 Safari/537.36 Core/1.116.475.400 QQBrowser/13.5.6267.400");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("13.5.6267.400", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("116.0.5845.97", info.getBrowserCoreVersion());
    }

    @DisplayName("Win11 火狐浏览器")
    @Test
    void test_10019() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:148.0) Gecko/20100101 Firefox/148.0");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Firefox browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Firefox", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Mozilla Firefox", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Mozilla Firefox", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("148.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Firefox", info.getBrowserCore());
        Assertions.assertEquals("148.0", info.getBrowserCoreVersion());
    }

    @DisplayName("三星 Galaxy Z Fold5")
    @Test
    void test_10021() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; SM-F946B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Samsung SM-F946B (Galaxy Z Fold5)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Samsung Galaxy Z Fold5", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("小米 Pad 6 (MIUI Pad 14)")
    @Test
    void test_10022() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; 23043RP34C) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Xiaomi 23043RP34C (Pad 6)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Xiaomi Pad 6", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Tablet", info.getFormFactor());
        Assertions.assertEquals("14", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("iPad Pro (M2 芯片)")
    @Test
    void test_10023() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPad; CPU OS 17_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Mobile/15E148 Safari/604.1");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Stock Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Mobile Safari", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPad", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPad", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("17.1", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Tablet", info.getFormFactor());
        Assertions.assertEquals("17.1", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("605.1.15", info.getBrowserCoreVersion());
    }

    @DisplayName("Safari (iPhone)")
    @Test
    void test_10024() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; CPU iPhone OS 17_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Mobile/15E148 Safari/604.1");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Stock Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Mobile Safari", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPhone", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPhone", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("17.1", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("17.1", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("605.1.15", info.getBrowserCoreVersion());
    }

    @DisplayName("Safari macOS")
    @Test
    void test_10025() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.1 Safari/605.1.15");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Stock Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple Safari", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("macOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple Safari", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10.15.7", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("605.1.15", info.getBrowserCoreVersion());
    }

    @DisplayName("Microsoft Edge macOS")
    @Test
    void test_10026() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("macOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10.15.7", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("120.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("Mozilla Firefox Linux")
    @Test
    void test_10027() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (X11; Linux x86_64; rv:120.0) Gecko/20100101 Firefox/120.0");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Firefox browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Firefox", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Mozilla Firefox", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Linux", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Mozilla Firefox", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Firefox", info.getBrowserCore());
        Assertions.assertEquals("120.0", info.getBrowserCoreVersion());
    }

    @DisplayName("Mozilla Firefox macOS")
    @Test
    void test_10028() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:120.0) Gecko/20100101 Firefox/120.0");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("Firefox browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Firefox", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Mozilla Firefox", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("macOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Mozilla Firefox", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("120.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10.15", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Firefox", info.getBrowserCore());
        Assertions.assertEquals("120.0", info.getBrowserCoreVersion());
    }

    @DisplayName("微信内置浏览器 UA：Android")
    @Test
    void test_10029() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 11; M2102J2SC Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.72 MQQBrowser/6.2 TBS/045811 Mobile Safari/537.36 MMWEBID/3950 MicroMessenger/8.0.11.1980(0x28000B5B) Process/tools WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("QQ Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Xiaomi M2102J2SC (Redmi K40 5G Premium Edition)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Xiaomi Redmi K40 5G Premium Edition", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("6.2", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("11", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("89.0.4389.72", info.getBrowserCoreVersion());
    }

    @DisplayName("微信内置浏览器 UA：iPhone")
    @Test
    void test_10030() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; CPU iPhone OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Mobile/9B176 MicroMessenger/6.1");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("WeChat", info.getAdvertisedAppName());
        Assertions.assertEquals("WeChat Built-in", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Apple iPhone", info.getCompleteDeviceName());
        Assertions.assertFalse(info.isLargescreen());
        Assertions.assertEquals("iOS", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Apple iPhone", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("6.1", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertTrue(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("5.1", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Safari", info.getBrowserCore());
        Assertions.assertEquals("534.46", info.getBrowserCoreVersion());
    }

    @DisplayName("电脑微信内置浏览器")
    @Test
    void test_10031() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x63090a13) UnifiedPCWindowsWechat(0xf2541a1f) XWEB/25030 Flue");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertFalse(info.isMobile());
        Assertions.assertFalse(info.isPhone());
        Assertions.assertTrue(info.isFullDesktop());
        Assertions.assertEquals("WeChat", info.getAdvertisedAppName());
        Assertions.assertEquals("WeChat Built-in", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Google Chrome", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Windows", info.getAdvertisedDeviceOs());
        Assertions.assertFalse(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Google Chrome", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("7.0.20.1781", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertFalse(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Desktop", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("144.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("手机微信内置浏览器：一加12")
    @Test
    void test_10032() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 16; PJD110 Build/BP2A.250605.015; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/146.0.7680.178 Mobile Safari/537.36 XWEB/1460205 MMWEBSDK/20260502 MMWEBID/7317 REV/67359f4ed01be2f482ec1f36b7b0474c71acc749 MicroMessenger/8.0.74.3120(0x28004A36) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("WeChat", info.getAdvertisedAppName());
        Assertions.assertEquals("WeChat Built-in", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PJD110 (12)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 12", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("8.0.74.3120", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("146.0.7680.178", info.getBrowserCoreVersion());
    }

    @DisplayName("手机夸克浏览器：一加12")
    @Test
    void test_10033() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-CN; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.86 Quark/10.11.5.1090 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Quark browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Quark", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PJD110 (12)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 12", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("10.11.5.1090", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("144.0.7559.86", info.getBrowserCoreVersion());
    }

    @DisplayName("手机谷歌浏览器：一加12")
    @Test
    void test_10034() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/149.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("Chrome browser", info.getAdvertisedAppName());
        Assertions.assertEquals("Chrome Mobile", info.getAdvertisedBrowser());
        Assertions.assertFalse(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("Generic Android", info.getCompleteDeviceName());
        Assertions.assertFalse(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("Generic Android", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("149.0.0.0", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Feature Phone", info.getFormFactor());
        Assertions.assertEquals("10", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("149.0.0.0", info.getBrowserCoreVersion());
    }

    @DisplayName("手机UC浏览器：一加12")
    @Test
    void test_10035() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-CN; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/123.0.6312.80 UCBrowser/18.9.0.1516 Mobile Safari/537.36");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertFalse(info.isAppWebview());
        Assertions.assertFalse(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("UCBrowser", info.getAdvertisedAppName());
        Assertions.assertEquals("UC Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PJD110 (12)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 12", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("18.9.0.1516", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("123.0.6312.80", info.getBrowserCoreVersion());
    }

    @DisplayName("手机欢太浏览器：一加12")
    @Test
    void test_10036() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-cn; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/115.0.5790.168 Mobile Safari/537.36 HeyTapBrowser/40.10.17.10");
        System.out.println(device);
        VirtualCapabilityInfo info = device.getVirtualCapabilityInfo();
        System.out.println(info);
        System.out.println(buildAssertions(info));
        Assertions.assertTrue(info.isAppWebview());
        Assertions.assertTrue(info.isApp());
        Assertions.assertTrue(info.isMobile());
        Assertions.assertTrue(info.isPhone());
        Assertions.assertFalse(info.isFullDesktop());
        Assertions.assertEquals("HeyTap Browser", info.getAdvertisedAppName());
        Assertions.assertEquals("HeyTap Browser", info.getAdvertisedBrowser());
        Assertions.assertTrue(info.isSmartphone());
        Assertions.assertFalse(info.isRobot());
        Assertions.assertEquals("OnePlus PJD110 (12)", info.getCompleteDeviceName());
        Assertions.assertTrue(info.isLargescreen());
        Assertions.assertEquals("Android", info.getAdvertisedDeviceOs());
        Assertions.assertTrue(info.isAndroid());
        Assertions.assertFalse(info.isXhtmlmpPreferred());
        Assertions.assertEquals("OnePlus 12", info.getDeviceName());
        Assertions.assertTrue(info.isHtmlPreferred());
        Assertions.assertEquals("40.10.17.10", info.getAdvertisedBrowserVersion());
        Assertions.assertFalse(info.isWindowsPhone());
        Assertions.assertFalse(info.isIos());
        Assertions.assertTrue(info.isTouchscreen());
        Assertions.assertFalse(info.isWmlPreferred());
        Assertions.assertEquals("Smartphone", info.getFormFactor());
        Assertions.assertEquals("16", info.getAdvertisedDeviceOsVersion());
        Assertions.assertEquals("Chrome", info.getBrowserCore());
        Assertions.assertEquals("115.0.5790.168", info.getBrowserCoreVersion());
    }

    String buildAssertions(VirtualCapabilityInfo info) {
        StringBuilder stringBuilder = new StringBuilder();
        append(stringBuilder, info.isAppWebview(), "info.isAppWebview()");
        append(stringBuilder, info.isApp(), "info.isApp()");
        append(stringBuilder, info.isMobile(), "info.isMobile()");
        append(stringBuilder, info.isPhone(), "info.isPhone()");
        append(stringBuilder, info.isFullDesktop(), "info.isFullDesktop()");
        append(stringBuilder, info.getAdvertisedAppName(), "info.getAdvertisedAppName()");
        append(stringBuilder, info.getAdvertisedBrowser(), "info.getAdvertisedBrowser()");
        append(stringBuilder, info.isSmartphone(), "info.isSmartphone()");
        append(stringBuilder, info.isRobot(), "info.isRobot()");
        append(stringBuilder, info.getCompleteDeviceName(), "info.getCompleteDeviceName()");
        append(stringBuilder, info.isLargescreen(), "info.isLargescreen()");
        append(stringBuilder, info.getAdvertisedDeviceOs(), "info.getAdvertisedDeviceOs()");
        append(stringBuilder, info.isAndroid(), "info.isAndroid()");
        append(stringBuilder, info.isXhtmlmpPreferred(), "info.isXhtmlmpPreferred()");
        append(stringBuilder, info.getDeviceName(), "info.getDeviceName()");
        append(stringBuilder, info.isHtmlPreferred(), "info.isHtmlPreferred()");
        append(stringBuilder, info.getAdvertisedBrowserVersion(), "info.getAdvertisedBrowserVersion()");
        append(stringBuilder, info.isWindowsPhone(), "info.isWindowsPhone()");
        append(stringBuilder, info.isIos(), "info.isIos()");
        append(stringBuilder, info.isTouchscreen(), "info.isTouchscreen()");
        append(stringBuilder, info.isWmlPreferred(), "info.isWmlPreferred()");
        append(stringBuilder, info.getFormFactor(), "info.getFormFactor()");
        append(stringBuilder, info.getAdvertisedDeviceOsVersion(), "info.getAdvertisedDeviceOsVersion()");
        append(stringBuilder, info.getBrowserCore(), "info.getBrowserCore()");
        append(stringBuilder, info.getBrowserCoreVersion(), "info.getBrowserCoreVersion()");
        return stringBuilder.toString();
    }

    void append(StringBuilder stringBuilder, boolean isTrue, String value) {
        if (isTrue) {
            stringBuilder.append("Assertions.assertTrue(").append(value).append(");\n");
        } else {
            stringBuilder.append("Assertions.assertFalse(").append(value).append(");\n");
        }
    }

    void append(StringBuilder stringBuilder, String eq, String value) {
        stringBuilder.append("Assertions.assertEquals(\"").append(eq).append("\", ").append(value).append(");\n");
    }
}
