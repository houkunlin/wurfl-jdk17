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

    public String getDeviceUserAgent() {
        return this.deviceUserAgent;
    }

    public String getBrowserUserAgent() {
        return this.browserUserAgent;
    }

    public String getCleanedDeviceUserAgent() {
        return this.cleanedDeviceUserAgent;
    }

    public NameVersionPair getBrowserPair() {
        return this.browserPair;
    }

    public String getOsPairName() {
        return this.osPair.getName();
    }

    public String getBrowserPairName() {
        return this.browserPair.getName();
    }

    public String getBrowserPairVersion() {
        return this.browserPair.getVersion();
    }

    public String getOsPairVersion() {
        return this.osPair.getVersion();
    }

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

    private void normalizeWindowsPhoneVersion() {
        if (StringMatchUtils.indexOf(this.osPair.getName(), "Windows Phone") < 0 || this.osPair.getVersion() == null) {
            return;
        }
        String mapped = windowsPhoneVersionMapping.get(this.osPair.getVersion());
        if (mapped != null) {
            this.osPair.setVersion(mapped);
        }
    }

    private void normalizeMacOrFallbackOs() {
        if (tryMatchMacVersion()) return;
        tryFallbackOs();
    }

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

    private boolean replaceUnderscoreInVersion() {
        if (this.osPair.getVersion() != null) {
            this.osPair.setVersion(this.osPair.getVersion().replace("_", "."));
        }
        return true;
    }

    private boolean isMacOS10OrLater() {
        String[] majorMinor = DOT_SPLIT_PATTERN.split(this.osPair.getVersion());
        return majorMinor != null && majorMinor.length > 1
                && StringUtils.isNumeric(majorMinor[0]) && StringUtils.isNumeric(majorMinor[1])
                && Integer.parseInt(majorMinor[0]) >= 10 && Integer.parseInt(majorMinor[1]) >= 12;
    }

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
