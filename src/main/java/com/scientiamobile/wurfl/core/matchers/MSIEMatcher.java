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
 * Microsoft Internet Explorer 和 Edge 浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 "MSIE"、"Trident"+"rv:" 或 " Edge/" 来识别
 * IE 和 Edge 桌面浏览器（排除移动浏览器和特定平台）。使用三个正则表达式依次尝试匹配：
 * Edge 格式、Trident 格式和 MSIE 格式。支持 IE v4 到 v12 及 Edge v13 到 v17 的版本映射。</p>
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

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(DEVICE_BY_MAJOR_VERSION.values());
        requiredDeviceIds.add("generic");
        requiredDeviceIds.add("generic_web_browser");
        requiredDeviceIds.add("msie_5_5");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        if (!request._internalIsMobileBrowser() && cleanedDeviceUserAgent.startsWith("Mozilla") && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Opera", "armv", "MOTO", "BREW")) {
            return StringMatchUtils.containsAllOf(cleanedDeviceUserAgent, "Trident", "rv:") || StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "MSIE", " Edge/");
        } else {
            return false;
        }
    }

    /**
     * 执行确定匹配.
     */
    @Override
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
    /**
     * 执行 RIS 匹配：先去除不重要的标记，然后以 "Trident" 关键字位置截断。
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        String normalizedUserAgent = UNIMPORTANT_TOKENS.matcher(userAgent).replaceFirst("");
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "Trident");
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    /**
     * 执行恢复匹配.
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = UNIMPORTANT_TOKENS.matcher(request.getNormalizedDeviceUserAgent()).replaceFirst("");
        return StringMatchUtils.containsAnyOf(normalizedUserAgent, "SLCC1", "Media Center PC", ".NET CLR", "OfficeLiveConnector") ? "generic_web_browser" : "generic";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "MSIEMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "MSIE";
    }
}
