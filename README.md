[![Maven Central](https://img.shields.io/maven-central/v/com.houkunlin/wurfl-jdk17.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.houkunlin%22%20AND%20a:%22wurfl-jdk17%22)
[![Java CI with Gradle](https://github.com/houkunlin/wurfl-jdk17/actions/workflows/gradle.yml/badge.svg)](https://github.com/houkunlin/wurfl-jdk17/actions/workflows/gradle.yml)

# wurfl-jdk17

基于 [ScientiaMobile WURFL](https://www.scientiamobile.com/) 官方 1.9.1.0 版本改造的 Java 17 设备检测引擎。
从 User-Agent 字符串精确识别设备型号、操作系统、浏览器等属性。

> **说明**：本项目源自官方 WURFL API 1.9.1.0，在此基础上进行了 Java 17 适配、API 清理、
> 以及多项功能增强（详见[增强特性](#增强特性)）。WURFL 设备数据文件需另行从官方获取。

---

## 重要声明

### 数据文件

**本项目不提供任何 WURFL 设备数据文件（`wurfl.zip` / `wurfl.xml`）。**

WURFL 设备数据库是 ScientiaMobile 的商业产品，使用者需自行前往 [ScientiaMobile 官网](https://www.scientiamobile.com/) 获取合法授权及数据文件。本项目仅提供数据加载和解析引擎，**不包含、不分发、不捆绑**任何 WURFL 设备数据。

数据文件获取方式：
- **商业授权**：通过 ScientiaMobile 官方渠道购买许可证
- **社区版本**：ScientiaMobile 可能提供有限功能的社区版数据文件
- **已有用户**：如您已有 WURFL 数据文件，可直接通过 `XMLResource` 加载使用

### 免责声明

本项目基于 ScientiaMobile WURFL API 1.9.1.0 进行改造，仅供学习和参考使用。

- 本项目的贡献者及维护者**不对**因使用本项目而导致的任何直接或间接损失承担责任
- 使用者应自行确保对 WURFL 数据文件的使用符合 ScientiaMobile 的许可条款
- 本项目的开源许可（[MulanPSL v2](#许可)）**仅适用于本仓库中的 Java 源代码**，不适用于 WURFL 设备数据文件
- 如需将本项目用于商业环境，请确保已获得 WURFL 数据的合法授权

## 功能特性

### 设备检测（Matchers）

通过责任链模式匹配 User-Agent，支持 60+ 设备/浏览器匹配器：

| 类别        | 匹配器                                                                    | 说明                                    |
|-----------|------------------------------------------------------------------------|---------------------------------------|
| **操作系统**  | `AndroidMatcher`                                                       | Android 手机/平板（支持 Android 1.0 ~ 17.0）  |
|           | `HarmonyOSMatcher`                                                     | 华为 HarmonyOS 设备（支持纯 HarmonyOS UA 格式）  |
|           | `AppleMatcher`                                                         | iPhone / iPad / iPod touch            |
|           | `WindowsPhoneMatcher`                                                  | Windows Phone 设备                      |
|           | `WebOSMatcher` / `TizenMatcher` / `UbuntuTouchOSMatcher`               | 其他移动 OS                               |
| **桌面浏览器** | `ChromeMatcher` / `FirefoxMatcher` / `SafariMatcher`                   | 主流桌面浏览器                               |
|           | `MSIEMatcher` / `OperaMatcher` / `Edge`                                | IE / Opera / Edge                     |
|           | `DesktopApplicationMatcher`                                            | 桌面应用识别                                |
| **移动浏览器** | `UcwebU3Matcher` / `UcwebU2Matcher`                                    | UC 浏览器（含 Android 14+ 支持，按型号名自动查找具体设备） |
|           | `OperaMiniOnAndroidMatcher` / `OperaMobiOrTabletOnAndroidMatcher`      | Opera 移动版                             |
|           | `FennecOnAndroidMatcher`                                               | Firefox 移动版                           |
|           | `EmailClientUserAgentMatcher`                                          | 邮件客户端（Thunderbird / Outlook / 等）      |
| **品牌设备**  | `SamsungMatcher` / `NokiaMatcher` / `Huawei`(通过 HarmonyOSMatcher)      | 品牌专用匹配                                |
|           | `HTCMatcher` / `LGMatcher` / `SonyEricssonMatcher` / `MotorolaMatcher` | 更多品牌                                  |
|           | `KindleMatcher` / `NintendoMatcher` / `XBoxMatcher`                    | 专用设备                                  |
| **其他**    | `BotMatcher`                                                           | 爬虫/机器人识别                              |
|           | `SmartTvMatcher`                                                       | 智能电视识别                                |

### 虚拟能力（Virtual Capabilities）

通过设备数据 + UA 解析引擎自动推断的能力值：

| 能力                                                                | 说明                                                            |
|-------------------------------------------------------------------|---------------------------------------------------------------|
| `is_mobile` / `is_phone` / `is_tablet`                            | 设备形态判定                                                        |
| `is_smartphone` / `is_feature_phone` / `is_full_desktop`          | 设备分类                                                          |
| `form_factor`                                                     | 形态因子（Desktop / Smartphone / Tablet / Feature Phone / Robot 等） |
| `is_android` / `is_ios` / `is_windows_phone`                      | 操作系统判定                                                        |
| `advertised_device_os` / `advertised_device_os_version`           | 操作系统名称与版本                                                     |
| `advertised_browser` / `advertised_browser_version`               | 浏览器名称与版本                                                      |
| `advertised_app_name`                                             | 应用名称                                                          |
| `complete_device_name` / `device_name`                            | 设备完整名称与简称                                                     |
| `is_app` / `is_app_webview`                                       | 应用/WebView 识别                                                 |
| `is_robot`                                                        | 爬虫识别                                                          |
| `is_largescreen` / `is_touchscreen`                               | 屏幕特征                                                          |
| `is_html_preferred` / `is_wml_preferred` / `is_xhtmlmp_preferred` | 渲染偏好                                                          |
| `browser_core` / `browser_core_version`                           | 浏览器内核名称与版本（Chrome / Firefox / Safari，非空即表示套壳浏览器或原生标识）         |

### 增强特性

- **Android 10+ 整数版本支持**：`UserAgentUtils.getAndroidVersion()` 新增 `ANDROID_VERSION_MAJOR_PATTERN`，支持 "Android
  14" 等无点号版本号的解析
- **HarmonyOS 支持**：新增 `HarmonyOSMatcher`，支持纯 HarmonyOS 格式 UA（不含 `Android`
  关键字），采用双层匹配策略（预设映射表 + 动态模糊查找）
- **UC Browser 增强**：`UcwebU3Matcher` 多项增强
  - 修复 `ANDROID_VERSION_U3_PATTERN` 正则，添加 `\b` 词边界，防止规范化前缀 `U3Android 18.9` 被误识别为 Android 版本号
  - 新增 `applyConclusiveMatch` 覆盖，使用原始 UA 进行 RIS 匹配，避免规范化前缀干扰
  - 新增 `modelNameToDeviceId` 型号名索引，恢复匹配时自动遍历 WURFL 模型中所有 `actual_device_root=true` 的设备，
    根据 UA 中提取的设备型号（如 `PJD110`）O(1) 查找对应设备（如 `oneplus_pjd110_ver1`），
    无需硬编码品牌前缀，支持 OnePlus / Xiaomi / OPPO / Samsung 等任意品牌
  - 实例级懒加载 + 线程安全双检锁，动态重载数据文件时自动重建索引，无脏数据风险
- **Android 设备型号提取优化**：`UserAgentUtils.ANDROID_MODEL_BUILD_PATTERN` 正则增强，
  跳过 UA 中 `zh-CN`、`en-US` 等真实区域设置代码（原仅跳过 `xx-xx` 占位符），
  使型号提取更准确
- **浏览器识别增强**：
  - 新增 `HeyTapBrowser`（欢太浏览器）识别，基于 Chromium 的国产手机浏览器
  - `browserCore` / `browserCoreVersion` 虚拟能力全面升级：
    移除 `PRIMARY_BROWSERS` 限制，所有浏览器现在都返回内核信息
  - 上游内核捕获 `captureUpstreamBrowser` 覆盖所有 UA，按优先级捕获：
    - **Chrome**：`Chrome/{version}` → 适用于 Chrome / Edge / 360 / QQ / 百度 / 欢太等
    - **Firefox**：`Firefox/{version}` → 原生 Firefox
    - **Safari**：`AppleWebKit/{version}` → 原生 Safari / iOS 内置浏览器 / iOS QQ/微信等
- **安全增强**：
  - **ReDoS 超时防护**：`NameVersionPair.find()` 统一入口集成 `RegexUtils`，每个正则匹配带 200ms 超时兜底，
    `TimeLimitedCharSequence` 在字符访问时检测超时，突发回溯立即中断返回"不匹配"，服务绝不阻塞
  - **XXE 防护加固**：`SAXParserFactory` 新增 `load-external-dtd=false` 禁止外部 DTD 加载，
    特性设置失败时输出 `warn` 级告警而非静默忽略，运维人员可及时发现防护缺失
  - **文件路径遍历防护**：`ResourceInput` 在 `parseUri()` 和 `openStream()` 两层校验，
    通过 `File.getCanonicalPath()` 规范化后对比检测 `../`、`%2e%2e%2f`、`%252e%252e%252f` 等变体
- **性能优化**：
  - **惰性正则编译**：约 90 个正则模式改为 `LazyPattern` 惰性编译包装，
    类加载阶段零开销，仅在首次使用时编译，老旧平台（Symbian/Bada/webOS 等）模式可能永不编译
  - **请求级缓存**：`assignProperties()` 通过 `ThreadLocal` 缓存 `VirtualCapabilityDevice`，
    同一请求的 24 次虚拟能力评估共享一次计算结果，消除 96% 重复正则匹配
  - **Matcher 对象复用**：`captureUpstreamBrowser()` 使用 `ThreadLocal<Matcher>` + `usePattern()` 切换，
    热路径上 5 次分配降为 1 次重置
- **线程安全与锁统一**：
  - `GeneralWURFLEngine` 消除独立的 `initLock`，全部迁移至 `ReentrantReadWriteLock`，
    消除 `reload()` 写锁与 `setEngineTarget()` 等操作并发的隐患
  - `getDeviceForRequest()` 三个重载补齐读锁保护，与 `getWURFLUtils()` 模式一致
- **原子替换防数据丢失**：`replaceRoot()` 采用"备份 → 原子替换 → 回滚"三段式策略，
  替代原有的"先删后写"。`reload()` 失败时通过 `ATOMIC_MOVE` 恢复备份，消除磁盘/内存不一致窗口

## 快速开始

### 环境要求

- JDK 17+
- Gradle 8.x+

### 依赖配置

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.houkunlin:wurfl-jdk17:1.0.0'
}
```

### 基本用法

```java
import com.scientiamobile.wurfl.core.Device;
import com.scientiamobile.wurfl.core.GeneralWURFLEngine;
import com.scientiamobile.wurfl.core.resource.XMLResource;

import java.io.FileInputStream;

public class BaseTest {
    public static void main(String[] args) {
        // 1. 加载 WURFL 设备数据文件（从官方渠道获取 wurfl.zip）
        FileInputStream fis = new FileInputStream("libs/wurfl.zip");
        GeneralWURFLEngine engine = new GeneralWURFLEngine(new XMLResource(fis, "wurfl.zip"));
        engine.load();

        // 2. 根据 User-Agent 查询设备
        Device device = engine.getDeviceForRequest("Mozilla/5.0 (Linux; Android 14; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Mobile Safari/537.36");

        // 3. 获取设备能力
        System.out.println(device.getCapability("brand_name"));       // Google
        System.out.println(device.getCapability("model_name"));       // Pixel 7
        System.out.println(device.getCapability("device_os"));        // Android
        System.out.println(device.getCapability("device_os_version")); // 14

        // 4. 获取虚拟能力
        System.out.println(device.getVirtualCapability("is_mobile"));          // true
        System.out.println(device.getVirtualCapability("is_smartphone"));      // true
        System.out.println(device.getVirtualCapability("form_factor"));        // Smartphone
        System.out.println(device.getVirtualCapability("complete_device_name"));// Google Pixel 7
        System.out.println(device.getVirtualCapability("advertised_device_os"));// Android
        System.out.println(device.getVirtualCapability("advertised_browser"));  // Chrome Mobile
    }
}
```

### WURFL 数据文件

本引擎需要 WURFL 设备数据文件才能工作，文件需从 [ScientiaMobile](https://www.scientiamobile.com/) 官方获取。
支持 `.zip` / `.xml` / `.gz` 格式，通过 `XMLResource` 加载：

```text
// 从 ZIP 文件加载
new XMLResource(new FileInputStream("wurfl.zip"), "wurfl.zip");

// 从原始 XML 文件加载
new XMLResource(new FileInputStream("wurfl.xml"), "wurfl.xml");
```

## 测试

```bash
# 执行全部测试
./gradlew test

# 执行单个测试
./gradlew test --tests "com.houkunlin.Wurfl01Test.test_10036"
```

测试用例覆盖：Chrome 桌面端、OnePlus/vivo/Xiaomi 手机、iPhone/iPad、QQ 内置浏览器、UC 浏览器（含 Android 16
一加12）、HeyTap（欢太）浏览器、HarmonyOS 设备、Firefox 桌面端/移动端、Safari macOS/iOS 等。

## 许可

[Mulan Permissive Software License，Version 2](https://license.coscl.org.cn/MulanPSL2)
