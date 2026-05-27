package com.scientiamobile.wurfl.core.request.normalizer.specific;

import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppleNormalizer implements UserAgentNormalizer {
    private static final Pattern IOS_APP_UA_PATTERN = Pattern.compile("^[^/]+?/[\\d\\.]+? \\(i[A-Za-z]+; iOS ([\\d\\.]+); Scale/[\\d\\.]+\\)");
    private static final Pattern SERVER_BAG_PATTERN = Pattern.compile("^server-bag \\[iPhone OS,([\\d\\.]+),");
    private static final Pattern IOS_DEVICE_MODEL_VERSION_PATTERN = Pattern.compile("^i(?:Phone|Pad|Pod)\\d+?,\\d+?/([\\d\\.]+)");
    private static final Pattern IOS_DEVICE_MODEL_IOS_VERSION_PATTERN = Pattern.compile("i(?:Phone|Pad|Pod)\\d+?,\\d+? iOS/([\\d_]+)");
    private static final Pattern IOS_CLIENT_SDK_PATTERN = Pattern.compile("^iOSClientSDK/\\d+\\.+[0-9\\.]+ +?\\((Mozilla.+)\\)$");
    private static final Pattern CPU_IOS_PATTERN = Pattern.compile("CPU iOS \\d+?\\.\\d+?");
    private static final Pattern CPU_OS_LIKE_PATTERN = Pattern.compile("(CPU(?: iPhone)? OS [\\d\\.]+ like)");

    private static Matcher findMatcher(String userAgent, Pattern pattern) {
        if (userAgent != null && pattern != null) {
            Matcher matcher = pattern.matcher(userAgent);
            return matcher.find() ? matcher : null;
        } else {
            return null;
        }
    }

    @Override
    public String normalize(String userAgent) {
        Matcher versionMatcher;
        versionMatcher = findMatcher(userAgent, IOS_APP_UA_PATTERN);
        if (versionMatcher == null) {
            versionMatcher = findMatcher(userAgent, SERVER_BAG_PATTERN);
        }

        if (versionMatcher == null) {
            versionMatcher = findMatcher(userAgent, IOS_DEVICE_MODEL_VERSION_PATTERN);
        }

        if (versionMatcher == null) {
            versionMatcher = findMatcher(userAgent, IOS_DEVICE_MODEL_IOS_VERSION_PATTERN);
        }

        if (versionMatcher != null) {
            String iosVersion = versionMatcher.group(1).replace(".", "_");
            String prefix;
            if (userAgent.contains("iPad")) {
                prefix = "Mozilla/5.0 (iPad; CPU OS " + iosVersion + " like Mac OS X) AppleWebKit/538.39.2 (KHTML, like Gecko) Version/7.0 Mobile/12A4297e Safari/9537.53 ";
            } else if (userAgent.contains("iPod touch")) {
                prefix = "Mozilla/5.0 (iPod touch; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ";
            } else if (userAgent.contains("iPod")) {
                prefix = "Mozilla/5.0 (iPod; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/538.41 (KHTML, like Gecko) Version/7.0 Mobile/12A307 Safari/9537.53 ";
            } else {
                prefix = "Mozilla/5.0 (iPhone; CPU iPhone OS " + iosVersion + " like Mac OS X) AppleWebKit/601.1.10 (KHTML, like Gecko) Version/8.0 Mobile/12E155 Safari/600.1.4 ";
            }
            return prefix + userAgent;
        }

        if (userAgent != null) {
            Matcher iosClientSdkMatcher = IOS_CLIENT_SDK_PATTERN.matcher(userAgent);
            if (iosClientSdkMatcher.matches()) {
                return iosClientSdkMatcher.group(1);
            }
            if (findMatcher(userAgent, CPU_IOS_PATTERN) != null) {
                String rewrittenUserAgent = userAgent.contains("iPad")
                    ? userAgent.replace("CPU iOS", "CPU OS")
                    : userAgent.replace("CPU iOS", "CPU iPhone OS");
                versionMatcher = findMatcher(rewrittenUserAgent, CPU_OS_LIKE_PATTERN);
                if (versionMatcher != null) {
                    String cpuOsLike = versionMatcher.group(1).replace(".", "_");
                    return rewrittenUserAgent.replace(" U;", "").replaceAll("CPU(?: iPhone)? OS ([\\d\\.]+) like", cpuOsLike);
                }
            }
        }
        return userAgent;
    }
}
