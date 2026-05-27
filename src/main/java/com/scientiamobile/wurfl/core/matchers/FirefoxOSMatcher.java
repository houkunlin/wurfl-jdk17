package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class FirefoxOSMatcher extends MatcherBase {
    private static final String FALLBACK_TABLET = "firefox_os_ver1_3_tablet";
    private static final String FALLBACK_GENERIC = "generic_firefox_os";
    private static final Pattern VERSION_RV_PREFIX = Pattern.compile("\\brv:\\d+\\.\\d+(.)");
    private static final Pattern VERSION_RV = Pattern.compile("\\brv:(\\d+\\.\\d+)");
    private static final Map<String, String> RV_TO_FIREFOX_OS_VERSION = new HashMap<>();
    private static final List<String> SUPPORTED_DEVICES = new ArrayList<>();

    static {
        RV_TO_FIREFOX_OS_VERSION.put("18.0", "1.0");
        RV_TO_FIREFOX_OS_VERSION.put("18.1", "1.1");
        RV_TO_FIREFOX_OS_VERSION.put("26.0", "1.2");
        RV_TO_FIREFOX_OS_VERSION.put("28.0", "1.3");
        RV_TO_FIREFOX_OS_VERSION.put("30.0", "1.4");
        RV_TO_FIREFOX_OS_VERSION.put("32.0", "2.0");
        RV_TO_FIREFOX_OS_VERSION.put("33.0", "2.1");
        RV_TO_FIREFOX_OS_VERSION.put("34.0", "2.1");
        RV_TO_FIREFOX_OS_VERSION.put("37.0", "2.2");
        RV_TO_FIREFOX_OS_VERSION.put("43.0", "2.5");
        SUPPORTED_DEVICES.add("firefox_os_ver1");
        SUPPORTED_DEVICES.add("firefox_os_ver1_1");
        SUPPORTED_DEVICES.add("firefox_os_ver1_2");
        SUPPORTED_DEVICES.add("firefox_os_ver1_3");
        SUPPORTED_DEVICES.add("firefox_os_ver1_4");
        SUPPORTED_DEVICES.add("firefox_os_ver1_4_tablet");
        SUPPORTED_DEVICES.add("firefox_os_ver2_0");
        SUPPORTED_DEVICES.add("firefox_os_ver2_0_tablet");
        SUPPORTED_DEVICES.add("firefox_os_ver2_1");
        SUPPORTED_DEVICES.add("firefox_os_ver2_1_tablet");
        SUPPORTED_DEVICES.add("firefox_os_ver2_2");
        SUPPORTED_DEVICES.add("firefox_os_ver2_2_tablet");
        SUPPORTED_DEVICES.add("firefox_os_ver2_5");
        SUPPORTED_DEVICES.add("firefox_os_ver2_5_tablet");
    }

    public FirefoxOSMatcher(WURFLModel model) {
        super(model);
    }

    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>(SUPPORTED_DEVICES);
        requiredDeviceIds.add(FALLBACK_TABLET);
        requiredDeviceIds.add(FALLBACK_GENERIC);
        return requiredDeviceIds;
    }

    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.contains("Firefox/") && StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Mobile", "Tablet");
    }

    @Override
    protected String risMatch(String userAgent) {
        Matcher rvPrefixMatcher = VERSION_RV_PREFIX.matcher(userAgent);
        return rvPrefixMatcher.find() ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, rvPrefixMatcher.end(1)) : null;
    }

    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        String firefoxOsVersion = "1.0";
        Matcher rvMatcher = VERSION_RV.matcher(normalizedUserAgent);
        if (rvMatcher.find()) {
            String rvVersion = rvMatcher.group(1);
            if (RV_TO_FIREFOX_OS_VERSION.containsKey(rvVersion)) {
                firefoxOsVersion = RV_TO_FIREFOX_OS_VERSION.get(rvVersion);
            }
        }

        String versionSuffix = firefoxOsVersion.replace(".", "_").replace("_0", "");
        String baseDeviceId = "firefox_os_ver" + versionSuffix;
        if (normalizedUserAgent.contains("Tablet")) {
            String tabletDeviceId = baseDeviceId + "_tablet";
            return SUPPORTED_DEVICES.contains(tabletDeviceId) ? tabletDeviceId : FALLBACK_TABLET;
        } else {
            return SUPPORTED_DEVICES.contains(baseDeviceId) ? baseDeviceId : FALLBACK_GENERIC;
        }
    }

    @Override
    public String getMatcherName() {
        return "FirefoxOSMatcher";
    }

    @Override
    public String getBucketMatcherName() {
        return "FirefoxOS";
    }
}
