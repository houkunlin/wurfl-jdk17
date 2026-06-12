package com.houkunlin;

import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.resource.XMLResource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

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
    private static final ObjectMapper objectMapper = new ObjectMapper();
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
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("google_chrome_147", device.getId());
        Assertions.assertEquals("conclusive", device.getMatchType().name());
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chrome", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Google Chrome", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Windows", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Google Chrome", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("147.0.0.0", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Desktop", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("10", device.getVirtualCapability("advertised_device_os_version"));
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
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("true", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chromium", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("OnePlus PLU110 (Turbo 6)", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("OnePlus Turbo 6", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("138.0.7204.179", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("16", device.getVirtualCapability("advertised_device_os_version"));
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
    @DisplayName("夸克浏览器：一加12")
    @Test
    void testOnePlus12Quark() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 16; zh-CN; PJD110 Build/BP2A.250605.015) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/144.0.7559.86 Quark/10.10.0.1075 Mobile Safari/537.36");
        System.out.println(device);
        System.out.println(device.getVirtualCapabilities());
        System.out.println(device.getCapabilities());
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chromium", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("OnePlus PJD110 (12)", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("OnePlus 12", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("144.0.7559.86", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("16", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("iPhone3")
    @Test
    void test_iPhone3() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/420.1 (KHTML, like Gecko) Version/3.0 Mobile/1A542a Safari/419.3");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Stock Browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Mobile Safari", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Apple iPhone", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("iOS", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Apple iPhone", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("3.0", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("3.0", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("QQ 内置浏览器 UA: Android")
    @Test
    void test_10003() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 11; M2102J2SC Build/RKQ1.200826.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.72 MQQBrowser/6.2 TBS/045908 Mobile Safari/537.36 V1_AND_SQ_8.8.28_2092_YYB_D A_8082800 QQ/8.8.28.6155 NetType/WIFI WebP/0.3.0 Pixel/1080 StatusBarHeight/91 SimpleUISwitch/1 QQTheme/2921 InMagicWin/0 StudyMode/0 CurrentMode/1 CurrentFontScale/0.9375");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chromium", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Xiaomi M2102J2SC (Redmi K40 5G Premium Edition)", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Xiaomi Redmi K40 5G Premium Edition", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("89.0.4389.72", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("11", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("QQ 内置浏览器 UA: iPhone")
    @Test
    void test_10004() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPhone; CPU iPhone OS 15_1_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148 QQ/8.8.38.705 V1_IPH_SQ_8.8.38_1_APP_A Pixel/1170 MiniAppEnable SimpleUISwitch/1 StudyMode/0 CurrentMode/1 CurrentFontScale/0.941000 QQTheme/2921 Core/WKWebView Device/Apple(iPhone 12 Pro) NetType/WIFI QBWebViewType/1 WKType/1");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("true", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Stock Browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Mobile Safari", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Apple iPhone", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("iOS", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Apple iPhone", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("15.1.1", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("15.1.1", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("QQ 内置浏览器 UA: iPad")
    @Test
    void test_10005() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (iPad; U; CPU OS 5_0 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Stock Browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Mobile Safari", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Apple iPad", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("iOS", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Apple iPad", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("5.1", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Tablet", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("5.0", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("UC 浏览器 (Android)")
    @Test
    void test_10006() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; U; Android 14; zh-CN; 23116PN5BC Build/UKQ1.230804.001) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/120.0.0.0 Mobile UCBrowser/13.6.0.1306 Safari/537.36");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("UCBrowser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("UC Browser", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Xiaomi 23116PN5BC (14 Pro)", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Xiaomi 14 Pro", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("13.6.0.1306", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("14", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("Google Pixel 7")
    @Test
    void test_10007() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chrome Mobile", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("Google Pixel 7", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Android", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("Google Pixel 7", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("120.0.0.0", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Smartphone", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("14", device.getVirtualCapability("advertised_device_os_version"));
    }

    @DisplayName("华为 Mate 30 Pro (HarmonyOS 4.0)")
    @Test
    void test_10008() {
        Device device = wurfl.getDeviceForRequest("Mozilla/5.0 (Linux; HarmonyOS; HUAWEI LIO-AN00) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");
        System.out.println(device);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getVirtualCapabilities()));
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(device.getCapabilities()));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app_webview"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_app"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_mobile"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_phone"));
        Assertions.assertEquals("Chrome browser", device.getVirtualCapability("advertised_app_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_full_desktop"));
        Assertions.assertEquals("Chrome", device.getVirtualCapability("advertised_browser"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_smartphone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_robot"));
        Assertions.assertEquals("generic web browser", device.getVirtualCapability("complete_device_name"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_largescreen"));
        Assertions.assertEquals("Linux", device.getVirtualCapability("advertised_device_os"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_android"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_xhtmlmp_preferred"));
        Assertions.assertEquals("generic web browser", device.getVirtualCapability("device_name"));
        Assertions.assertEquals("120.0.0.0", device.getVirtualCapability("advertised_browser_version"));
        Assertions.assertEquals("true", device.getVirtualCapability("is_html_preferred"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_windows_phone"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_ios"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_touchscreen"));
        Assertions.assertEquals("false", device.getVirtualCapability("is_wml_preferred"));
        Assertions.assertEquals("Desktop", device.getVirtualCapability("form_factor"));
        Assertions.assertEquals("", device.getVirtualCapability("advertised_device_os_version"));
    }
}
