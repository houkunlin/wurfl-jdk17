package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matcher implementation for identifying MSIE devices and browsers.
 */

final class MSIEMatcher extends MatcherBase {
    private static final Pattern MSIE = Pattern.compile("^Mozilla/[45]\\.0 \\(compatible; MSIE (\\d+)\\.(\\d+)(?:[\\da-z]+)?;");
    private static final Pattern TRIDENT_RV = Pattern.compile("^Mozilla/5\\.0 \\(.+?Trident.+?; rv:(\\d\\d)\\.(\\d+)\\)");
    private static final Pattern EDGE = Pattern.compile("^Mozilla/5\\.0 \\(Windows NT.+? Edge/(\\d+)\\.(\\d+)");
    private static final Pattern UNIMPORTANT_TOKENS = Pattern.compile("( \\.NET CLR [\\d\\.]+;?| Media Center PC [\\d\\.]+;?| OfficeLive[a-zA-Z0-9\\.\\d]+;?| InfoPath[\\.\\d]+;?)");
    private static final Map<String, String> DEVICE_BY_MAJOR_VERSION;

    static {
        DEVICE_BY_MAJOR_VERSION = new HashMap<>();
        DEVICE_BY_MAJOR_VERSION.put("0", "msie");
        DEVICE_BY_MAJOR_VERSION.put("4", "msie_4");
        DEVICE_BY_MAJOR_VERSION.put("5", "msie_5");
        DEVICE_BY_MAJOR_VERSION.put("6", "msie_6");
        DEVICE_BY_MAJOR_VERSION.put("7", "msie_7");
        DEVICE_BY_MAJOR_VERSION.put("8", "msie_8");
        DEVICE_BY_MAJOR_VERSION.put("9", "msie_9");
        DEVICE_BY_MAJOR_VERSION.put("10", "msie_10");
        DEVICE_BY_MAJOR_VERSION.put("11", "msie_11");
        DEVICE_BY_MAJOR_VERSION.put("12", "msie_12");
        DEVICE_BY_MAJOR_VERSION.put("13", "edge_13");
        DEVICE_BY_MAJOR_VERSION.put("14", "edge_14");
        DEVICE_BY_MAJOR_VERSION.put("15", "edge_15");
        DEVICE_BY_MAJOR_VERSION.put("16", "edge_16");
        DEVICE_BY_MAJOR_VERSION.put("17", "edge_17");
    }

    public MSIEMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * Returns the require devic eds.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(DEVICE_BY_MAJOR_VERSION.values());
        requiredDeviceIds.add("generic");
        requiredDeviceIds.add("generic_web_browser");
        requiredDeviceIds.add("msie_5_5");
        return requiredDeviceIds;
    }

    @Override
/**
 * Returns whether this ca nandle.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        if (!request._internalIsMobileBrowser() && cleanedDeviceUserAgent.startsWith("Mozilla") && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Opera", "armv", "MOTO", "BREW")) {
            return StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Trident", "rv:") || StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "MSIE", " Edge/");
        } else {
            return false;
        }
    }

    @Override
/**
 * Appl yonclusiv eatch.
 */

    protected String applyConclusiveMatch(WURFLRequest request) {
        String normalizedUserAgent = UNIMPORTANT_TOKENS.matcher(request.getNormalizedDeviceUserAgent()).replaceFirst("");
        Matcher[] matchers = new Matcher[]{EDGE.matcher(normalizedUserAgent), TRIDENT_RV.matcher(normalizedUserAgent), MSIE.matcher(normalizedUserAgent)};
        boolean matched = false;
        Matcher matchedMatcher = null;

        for (int i = 0; i < 3; ++i) {
            Matcher matcher = matchers[i];
            matched = matcher.find();
            if (matched) {
                matchedMatcher = matcher;
                break;
            }
        }

        if (matched) {
            String majorVersion = matchedMatcher.group(1);
            String minorVersion = matchedMatcher.group(2);
            Integer parsedMinorVersion = -1;

            try {
                parsedMinorVersion = Integer.parseInt(minorVersion);
            } catch (NumberFormatException ignore) {
            }

            if ("5".equals(majorVersion) && parsedMinorVersion == 5) {
                return "msie_5_5";
            }

            String deviceId;
            deviceId = DEVICE_BY_MAJOR_VERSION.get(majorVersion);
            if (deviceId != null) {
                return deviceId;
            }
        }

        return super.applyConclusiveMatch(request);
    }

    @Override
/**
 * Ri satch.
 */

    protected String risMatch(String userAgent) {
        String normalizedUserAgent = UNIMPORTANT_TOKENS.matcher(userAgent).replaceFirst("");
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "Trident");
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    @Override
/**
 * Appl yecover yatch.
 */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = UNIMPORTANT_TOKENS.matcher(request.getNormalizedDeviceUserAgent()).replaceFirst("");
        return StringMatchUtils.containsAnyOf(normalizedUserAgent, "SLCC1", "Media Center PC", ".NET CLR", "OfficeLiveConnector") ? "generic_web_browser" : "generic";
    }

    @Override
/**
 * Returns the matche rame.
 */

    public String getMatcherName() {
        return "MSIEMatcher";
    }

    @Override
/**
 * Returns the bucke tatche rame.
 */

    public String getBucketMatcherName() {
        return "MSIE";
    }
}
