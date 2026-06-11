package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 虚拟能力设备实现，提供操作系统和浏览器的名称/版本识别功能。
 * <p>通过解析 User-Agent 字符串，利用正则匹配和规则库识别设备的操作系统
 * （如 Windows、macOS、Android、iOS 等）和浏览器（如 Chrome、Firefox、Safari 等），
 * 并对识别结果进行归一化处理（如将 Windows NT 版本号转换为可读名称）。</p>
 */

public class VirtualCapabilityDevice implements Serializable {
    @Serial
    private static final long serialVersionUID = -9083698933173727805L;
    private static final Pattern WINDOWS_NT_VERSION_PATTERN = Pattern.compile("Windows NT (\\d+?\\.\\d)");
    private static final Pattern WINDOWS_VERSION_PATTERN = Pattern.compile("Windows [\\d.]+");
    private static final Pattern PPC_OS_X_VERSION_PATTERN = Pattern.compile("PPC.+OS X ([\\d._]+)");
    private static final Pattern TRIDENT_VERSION_PATTERN = Pattern.compile("Trident/([\\d.]+)");
    private static final Pattern INTEL_MAC_OS_X_VERSION_PATTERN = Pattern.compile("Intel Mac OS X ([\\d._]+)");
    private static final Pattern MAC_OS_X_VERSION_PATTERN = Pattern.compile("MacOS X ([\\d._]+)");
    private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("\\.");
    private static final String MAC_OS_X = "Mac OS X";
    private static final String LINUX = "Linux";
    private static final Map<String, String> windowsNtVersionToName = Map.ofEntries(
            Map.entry("4.0", "NT 4.0"), Map.entry("5.0", "2000"),
            Map.entry("5.1", "XP"), Map.entry("5.2", "XP"),
            Map.entry("6.0", "Vista"), Map.entry("6.1", "7"),
            Map.entry("6.2", "8"), Map.entry("6.3", "8.1"),
            Map.entry("6.4", "10"), Map.entry("10.0", "10")
    );
    private static final Map<String, String> tridentVersionToIeVersion = Map.of(
            "4.0", "8.0", "5.0", "9.0", "6.0", "10.0", "7.0", "11.0"
    );
    private static final Map<String, String> windowsPhoneVersionMapping = Map.of(
            "7.10", "7.5", "8.10", "8.1", "8.15", "10"
    );
    private static final Set<String> knownOsNames = Set.of(
            "Windows CE", "Windows Mobile", "Windows Phone", "Nintendo",
            "Android", "iOS", "Tizen", "Nokia Series 40", "Symbian",
            "BlackBerry", "RIM Tablet OS", "Bada", "webOS", LINUX,
            "X11", "Ubuntu", "Fedora", MAC_OS_X, "Fire OS"
    );

    private final NameVersionPair browserPair;
    private final NameVersionPair osPair;
    private final String deviceUserAgent;
    private final String browserUserAgent;
    private final String cleanedDeviceUserAgent;

    public VirtualCapabilityDevice(WURFLRequest request) {
        this.cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        if (request.isUrlEncoded()) {
            this.deviceUserAgent = this.cleanedDeviceUserAgent;
            this.browserUserAgent = this.cleanedDeviceUserAgent;
        } else {
            this.deviceUserAgent = request.getDeviceUserAgent();
            this.browserUserAgent = request.getBrowserUserAgent();
        }
        this.browserPair = new NameVersionPair();
        this.osPair = new NameVersionPair();
    }

    public VirtualCapabilityDevice(String deviceUserAgent, String browserUserAgent, String cleanedDeviceUserAgent) {
        this.deviceUserAgent = deviceUserAgent;
        this.browserUserAgent = browserUserAgent;
        this.cleanedDeviceUserAgent = cleanedDeviceUserAgent;

        this.browserPair = new NameVersionPair();
        this.osPair = new NameVersionPair();
    }

    /**
     * 获取设备 User-Agent 字符串。
     *
     * @return 设备 UA 字符串
     */

    public String getDeviceUserAgent() {
        return this.deviceUserAgent;
    }

    public String getBrowserUserAgent() {
        return this.browserUserAgent;
    }

    /**
     * 获取清理后的设备 User-Agent 字符串。
     *
     * @return 清理后的 UA 字符串
     */

    public String getCleanedDeviceUserAgent() {
        return this.cleanedDeviceUserAgent;
    }

    public NameVersionPair getBrowserPair() {
        return this.browserPair;
    }

    /**
     * 获取操作系统名称。
     *
     * @return 操作系统名称
     */

    public String getOsPairName() {
        return this.osPair.getName();
    }

    public String getBrowserPairName() {
        return this.browserPair.getName();
    }

    /**
     * 获取浏览器版本。
     *
     * @return 浏览器版本字符串
     */

    public String getBrowserPairVersion() {
        return this.browserPair.getVersion();
    }

    public String getOsPairVersion() {
        return this.osPair.getVersion();
    }

    /**
     * 获取操作系统名称-版本对。
     *
     * @return 操作系统名称-版本对
     */

    public NameVersionPair getOsPair() {
        return this.osPair;
    }

    public void normalizeOS() {
        if (normalizeWindowsOs()) {
            return;
        }
        normalizeWindowsPhoneVersion();
        normalizeMacOrFallbackOs();
    }

    /**
     * 归一化 Windows 操作系统名称和版本。
     * <p>将 Windows NT 版本号映射为可读的名称（如 6.1 → 7、10.0 → 10）。</p>
     *
     * @return 如果成功识别为 Windows 操作系统返回 {@code true}
     */

    private boolean normalizeWindowsOs() {
        if (this.osPair.getName() == null || StringMatchUtils.indexOf(this.deviceUserAgent, "Windows") < 0) {
            return false;
        }
        Matcher ntVersionMatcher = WINDOWS_NT_VERSION_PATTERN.matcher(this.osPair.getName());
        if (ntVersionMatcher.find()) {
            this.osPair.setName("Windows");
            String version = ntVersionMatcher.group(1);
            this.osPair.setVersion(windowsNtVersionToName.getOrDefault(version, version));
            return true;
        }
        return WINDOWS_VERSION_PATTERN.matcher(this.osPair.getName()).find();
    }

    /**
     * 归一化 Windows Phone 版本号。
     * <p>将内部版本号映射为用户可读的版本（如 7.10 → 7.5、8.10 → 8.1）。</p>
     */

    private void normalizeWindowsPhoneVersion() {
        if (StringMatchUtils.indexOf(this.osPair.getName(), "Windows Phone") < 0 || this.osPair.getVersion() == null) {
            return;
        }
        String mapped = windowsPhoneVersionMapping.get(this.osPair.getVersion());
        if (mapped != null) {
            this.osPair.setVersion(mapped);
        }
    }

    /**
     * 在 Mac 操作系统和其他回退策略之间选择归一化路径。
     */

    private void normalizeMacOrFallbackOs() {
        if (tryMatchMacVersion()) return;
        tryFallbackOs();
    }

    /**
     * 尝试匹配 Mac 操作系统版本。
     * <p>支持 PPC Mac OS X、Intel Mac OS X 和通用 Mac OS X 格式。</p>
     *
     * @return 如果成功匹配返回 {@code true}
     */

    private boolean tryMatchMacVersion() {
        if (this.osPair.matchAndSetGroup(PPC_OS_X_VERSION_PATTERN, this.deviceUserAgent, MAC_OS_X, 1)) {
            return replaceUnderscoreInVersion();
        }
        if (this.osPair.matchAndSetGroup(MAC_OS_X_VERSION_PATTERN, this.deviceUserAgent, MAC_OS_X, 1)) {
            return replaceUnderscoreInVersion();
        }
        if (this.osPair.matchAndSet(PPC_OS_X_VERSION_PATTERN, this.deviceUserAgent, MAC_OS_X, null)) {
            return true;
        }
        if (this.osPair.matchAndSetGroup(INTEL_MAC_OS_X_VERSION_PATTERN, this.deviceUserAgent, MAC_OS_X, 1)) {
            if (this.osPair.getVersion() != null) {
                this.osPair.setVersion(this.osPair.getVersion().replace("_", "."));
                if (isMacOS10OrLater()) {
                    this.osPair.setName("macOS");
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 将版本字符串中的下划线替换为点号。
     *
     * @return 始终返回 {@code true}
     */

    private boolean replaceUnderscoreInVersion() {
        if (this.osPair.getVersion() != null) {
            this.osPair.setVersion(this.osPair.getVersion().replace("_", "."));
        }
        return true;
    }

    /**
     * 判断是否为 macOS 10.12 或更高版本。
     * <p>用于区分旧版 Mac OS X 和 macOS 命名。</p>
     *
     * @return 如果是 macOS 10.12+ 返回 {@code true}
     */

    private boolean isMacOS10OrLater() {
        String[] majorMinor = DOT_SPLIT_PATTERN.split(this.osPair.getVersion());
        return majorMinor != null && majorMinor.length > 1
                && StringUtils.isNumeric(majorMinor[0]) && StringUtils.isNumeric(majorMinor[1])
                && Integer.parseInt(majorMinor[0]) >= 10 && Integer.parseInt(majorMinor[1]) >= 12;
    }

    /**
     * 尝试使用回退策略识别操作系统。
     * <p>当常规匹配无法识别操作系统时，尝试通过字符串包含检测来判断。</p>
     */

    private void tryFallbackOs() {
        if (this.osPair.containsAndSetName(this.deviceUserAgent, "Mac_PowerPC", MAC_OS_X)) return;
        if (this.osPair.containsAndSetName(this.deviceUserAgent, "CrOS", "Chrome OS")) return;

        if (this.osPair.getName() != null && (this.osPair.getName().contains(LINUX) || this.osPair.getName().contains("X11"))) {
            this.osPair.setName(LINUX);
        }
        if (!StringUtils.isNotEmpty(this.osPair.getName())) {
            return;
        }
        for (String osName : knownOsNames) {
            if (this.osPair.getName().contains(osName)) {
                return;
            }
        }
        this.osPair.setName("");
        this.osPair.setVersion("");
        if (StringMatchUtils.indexOf(this.deviceUserAgent, LINUX) >= 0 || StringMatchUtils.indexOf(this.deviceUserAgent, "X11") >= 0) {
            this.osPair.setName(LINUX);
        }
    }

    /**
     * 归一化浏览器名称和版本。
     * <p>针对 IE 浏览器，通过 Trident 版本号推断实际的 IE 版本号，
     * 如 Trident/7.0 → IE 11.0。如果检测到兼容性视图，会在版本后添加标记。</p>
     */

    public void normalizeBrowser() {
        if (!"IE".equals(this.browserPair.getName())) {
            return;
        }
        Matcher tridentMatcher = TRIDENT_VERSION_PATTERN.matcher(this.deviceUserAgent);
        if (!tridentMatcher.find()) {
            return;
        }
        String tridentVersion = tridentVersionToIeVersion.get(tridentMatcher.group(1));
        if (tridentVersion != null && !tridentVersion.equals(this.browserPair.getVersion())) {
            this.browserPair.setVersion(tridentVersion + "(Compatibility View)");
        }
    }
}
