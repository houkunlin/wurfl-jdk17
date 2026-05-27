package com.scientiamobile.wurfl.core;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VirtualCapabilityDevice implements Serializable {
    private static final long serialVersionUID = -9083698933173727805L;
    private static final Pattern WINDOWS_NT_VERSION_PATTERN = Pattern.compile("Windows NT ([0-9]+?\\.[0-9])");
    private static final Pattern WINDOWS_VERSION_PATTERN = Pattern.compile("Windows [0-9\\.]+");
    private static final Pattern PPC_OS_X_VERSION_PATTERN = Pattern.compile("PPC.+OS X ([0-9\\._]+)");
    private static final Pattern PPC_OS_X_VERSION_PATTERN_ALT = Pattern.compile("PPC.+OS X ([0-9\\._]+)");
    private static final Pattern TRIDENT_VERSION_PATTERN = Pattern.compile("Trident/([\\d\\.]+)");
    private static final Pattern INTEL_MAC_OS_X_VERSION_PATTERN = Pattern.compile("Intel Mac OS X ([0-9\\._]+)");
    private static final Pattern MAC_OS_X_VERSION_PATTERN = Pattern.compile("MacOS X ([0-9\\._]+)");
    private static final Pattern DOT_SPLIT_PATTERN = Pattern.compile("\\.");
    private static Map<String, String> windowsNtVersionToName = new HashMap<>();
    private static Map<String, String> tridentVersionToIeVersion = new HashMap<>();
    private static Map<String, String> windowsPhoneVersionMapping = new HashMap<>();
    private static Set<String> knownOsNames = new HashSet<>(16);

    static {
        windowsNtVersionToName.put("4.0", "NT 4.0");
        windowsNtVersionToName.put("5.0", "2000");
        windowsNtVersionToName.put("5.1", "XP");
        windowsNtVersionToName.put("5.2", "XP");
        windowsNtVersionToName.put("6.0", "Vista");
        windowsNtVersionToName.put("6.1", "7");
        windowsNtVersionToName.put("6.2", "8");
        windowsNtVersionToName.put("6.3", "8.1");
        windowsNtVersionToName.put("6.4", "10");
        windowsNtVersionToName.put("10.0", "10");
        tridentVersionToIeVersion.put("4.0", "8.0");
        tridentVersionToIeVersion.put("5.0", "9.0");
        tridentVersionToIeVersion.put("6.0", "10.0");
        tridentVersionToIeVersion.put("7.0", "11.0");
        windowsPhoneVersionMapping.put("7.10", "7.5");
        windowsPhoneVersionMapping.put("8.10", "8.1");
        windowsPhoneVersionMapping.put("8.15", "10");
        knownOsNames.add("Windows CE");
        knownOsNames.add("Windows Mobile");
        knownOsNames.add("Windows Phone");
        knownOsNames.add("Nintendo");
        knownOsNames.add("Android");
        knownOsNames.add("iOS");
        knownOsNames.add("Tizen");
        knownOsNames.add("Nokia Series 40");
        knownOsNames.add("Symbian");
        knownOsNames.add("BlackBerry");
        knownOsNames.add("RIM Tablet OS");
        knownOsNames.add("Bada");
        knownOsNames.add("webOS");
        knownOsNames.add("Linux");
        knownOsNames.add("X11");
        knownOsNames.add("Ubuntu");
        knownOsNames.add("Fedora");
        knownOsNames.add("Mac OS X");
        knownOsNames.add("Fire OS");
    }

    private final NameVersionPair browserPair;
    private final NameVersionPair osPair;
    private String deviceUserAgent;
    private String browserUserAgent;
    private String cleanedDeviceUserAgent;

    public VirtualCapabilityDevice(WURFLRequest request) {
        if (request.isUrlEncoded()) {
            this.deviceUserAgent = request.getCleanedDeviceUserAgent();
            this.browserUserAgent = this.deviceUserAgent;
            this.cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        } else {
            this.deviceUserAgent = request.getDeviceUserAgent();
            this.browserUserAgent = request.getBrowserUserAgent();
            this.cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        }

        this.browserPair = new NameVersionPair();
        this.osPair = new NameVersionPair();
    }

    public VirtualCapabilityDevice(String deviceUserAgent, String browserUserAgent, String cleanedDeviceUserAgent, String rawUserAgent) {
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
        if (this.osPair.matchAndSetGroup(PPC_OS_X_VERSION_PATTERN, this.deviceUserAgent, "Mac OS X", 1)) {
            return replaceUnderscoreInVersion();
        }
        if (this.osPair.matchAndSetGroup(MAC_OS_X_VERSION_PATTERN, this.deviceUserAgent, "Mac OS X", 1)) {
            return replaceUnderscoreInVersion();
        }
        if (this.osPair.matchAndSet(PPC_OS_X_VERSION_PATTERN_ALT, this.deviceUserAgent, "Mac OS X", (String) null)) {
            return true;
        }
        if (this.osPair.matchAndSetGroup(INTEL_MAC_OS_X_VERSION_PATTERN, this.deviceUserAgent, "Mac OS X", 1)) {
            if (this.osPair.getVersion() != null) {
                this.osPair.setVersion(this.osPair.getVersion().replaceAll("_", "."));
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
            this.osPair.setVersion(this.osPair.getVersion().replaceAll("_", "."));
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
        if (this.osPair.containsAndSetName(this.deviceUserAgent, "Mac_PowerPC", "Mac OS X")) return;
        if (this.osPair.containsAndSetName(this.deviceUserAgent, "CrOS", "Chrome OS")) return;

        if (this.osPair.getName() != null && (this.osPair.getName().contains("Linux") || this.osPair.getName().contains("X11"))) {
            this.osPair.setName("Linux");
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
        if (StringMatchUtils.indexOf(this.deviceUserAgent, "Linux") >= 0 || StringMatchUtils.indexOf(this.deviceUserAgent, "X11") >= 0) {
            this.osPair.setName("Linux");
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
