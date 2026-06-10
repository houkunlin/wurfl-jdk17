package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Opera 桌面浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 Opera 或 OPR/（排除移动浏览器场景）来识别 Opera 桌面浏览器。支持 Opera v7 到 v43 的版本映射。</p>
 */

final class OperaMatcher extends MatcherBase {
    private static final String OPERA_GENERIC = "opera";
    private static final Pattern OPERA_VERSION = Pattern.compile("Opera[ /]?(\\d+\\.\\d+)");
    private static final Map<String, String> MAJOR_VERSION_TO_DEVICE_ID;

    static {
        MAJOR_VERSION_TO_DEVICE_ID = new HashMap<>();
        MAJOR_VERSION_TO_DEVICE_ID.put("", OPERA_GENERIC);
        MAJOR_VERSION_TO_DEVICE_ID.put("7", "opera_7");
        MAJOR_VERSION_TO_DEVICE_ID.put("8", "opera_8");
        MAJOR_VERSION_TO_DEVICE_ID.put("9", "opera_9");
        MAJOR_VERSION_TO_DEVICE_ID.put("10", "opera_10");
        MAJOR_VERSION_TO_DEVICE_ID.put("11", "opera_11");
        MAJOR_VERSION_TO_DEVICE_ID.put("12", "opera_12");
        MAJOR_VERSION_TO_DEVICE_ID.put("15", "opera_15");
        MAJOR_VERSION_TO_DEVICE_ID.put("16", "opera_16");
        MAJOR_VERSION_TO_DEVICE_ID.put("17", "opera_17");
        MAJOR_VERSION_TO_DEVICE_ID.put("18", "opera_18");
        MAJOR_VERSION_TO_DEVICE_ID.put("19", "opera_19");
        MAJOR_VERSION_TO_DEVICE_ID.put("20", "opera_20");
        MAJOR_VERSION_TO_DEVICE_ID.put("21", "opera_21");
        MAJOR_VERSION_TO_DEVICE_ID.put("22", "opera_22");
        MAJOR_VERSION_TO_DEVICE_ID.put("23", "opera_23");
        MAJOR_VERSION_TO_DEVICE_ID.put("24", "opera_24");
        MAJOR_VERSION_TO_DEVICE_ID.put("25", "opera_25");
        MAJOR_VERSION_TO_DEVICE_ID.put("26", "opera_26");
        MAJOR_VERSION_TO_DEVICE_ID.put("27", "opera_27");
        MAJOR_VERSION_TO_DEVICE_ID.put("28", "opera_28");
        MAJOR_VERSION_TO_DEVICE_ID.put("29", "opera_29");
        MAJOR_VERSION_TO_DEVICE_ID.put("30", "opera_30");
        MAJOR_VERSION_TO_DEVICE_ID.put("31", "opera_31");
        MAJOR_VERSION_TO_DEVICE_ID.put("32", "opera_32");
        MAJOR_VERSION_TO_DEVICE_ID.put("33", "opera_33");
        MAJOR_VERSION_TO_DEVICE_ID.put("34", "opera_34");
        MAJOR_VERSION_TO_DEVICE_ID.put("35", "opera_35");
        MAJOR_VERSION_TO_DEVICE_ID.put("36", "opera_36");
        MAJOR_VERSION_TO_DEVICE_ID.put("37", "opera_37");
        MAJOR_VERSION_TO_DEVICE_ID.put("38", "opera_38");
        MAJOR_VERSION_TO_DEVICE_ID.put("39", "opera_39");
        MAJOR_VERSION_TO_DEVICE_ID.put("40", "opera_40");
        MAJOR_VERSION_TO_DEVICE_ID.put("41", "opera_41");
        MAJOR_VERSION_TO_DEVICE_ID.put("42", "opera_42");
        MAJOR_VERSION_TO_DEVICE_ID.put("43", "opera_43");
    }

    public OperaMatcher(UserAgentNormalizer userAgentNormalizer, WURFLModel wurflModel) {
        super(userAgentNormalizer, wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(MAJOR_VERSION_TO_DEVICE_ID.values());
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsMobileBrowser() && StringMatchUtils.containsAnyOf(request.getCleanedDeviceUserAgent(), "Opera", "OPR/");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        int operaIndex = StringMatchUtils.indexOf(userAgent, "Opera");
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ".", operaIndex);
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    @Override
    /**
     * 恢复匹配策略：从 User-Agent 中提取 Opera 版本号，根据主版本号返回对应的设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        Matcher versionMatcher = OPERA_VERSION.matcher(request.getNormalizedDeviceUserAgent());
        if (!versionMatcher.find()) {
            return OPERA_GENERIC;
        } else {
            String version = versionMatcher.group(1);
            if (StringUtils.isEmpty(version)) {
                return OPERA_GENERIC;
            } else {
                String[] parts = version.split("\\.");
                if (ArrayUtils.isEmpty(parts)) {
                    return OPERA_GENERIC;
                } else {
                    String deviceId = MAJOR_VERSION_TO_DEVICE_ID.get(parts[0]);
                    return StringUtils.isNotEmpty(deviceId) ? deviceId : OPERA_GENERIC;
                }
            }
        }
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "OperaMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Opera";
    }
}
