package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * BlackBerry（黑莓）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 blackberry、(BB10; 或 (PlayBook 来识别黑莓品牌的移动设备。支持 BlackBerry OS 2.x 到 10.x 的版本映射。</p>
 */

final class BlackBerryMatcher extends AbstractMatcher {
    private static final String BLACKBERRY_GENERIC_VER10 = "blackberry_generic_ver10";
    private static final String BLACKBERRY_GENERIC_VER10_TABLET = "blackberry_generic_ver10_tablet";
    private static final String RIM_PLAYBOOK_VER1 = "rim_playbook_ver1";
    private static final Map<String, String> OS_VERSION_TO_DEVICE_ID;
    private static final Pattern BLACKBERRY_OS_VERSION;

    static {
        OS_VERSION_TO_DEVICE_ID = new LinkedHashMap<>();
        OS_VERSION_TO_DEVICE_ID.put("2.", "blackberry_generic_ver2");
        OS_VERSION_TO_DEVICE_ID.put("3.2", "blackberry_generic_ver3_sub2");
        OS_VERSION_TO_DEVICE_ID.put("3.3", "blackberry_generic_ver3_sub30");
        OS_VERSION_TO_DEVICE_ID.put("3.5", "blackberry_generic_ver3_sub50");
        OS_VERSION_TO_DEVICE_ID.put("3.6", "blackberry_generic_ver3_sub60");
        OS_VERSION_TO_DEVICE_ID.put("3.7", "blackberry_generic_ver3_sub70");
        OS_VERSION_TO_DEVICE_ID.put("4.1", "blackberry_generic_ver4_sub10");
        OS_VERSION_TO_DEVICE_ID.put("4.2", "blackberry_generic_ver4_sub20");
        OS_VERSION_TO_DEVICE_ID.put("4.3", "blackberry_generic_ver4_sub30");
        OS_VERSION_TO_DEVICE_ID.put("4.5", "blackberry_generic_ver4_sub50");
        OS_VERSION_TO_DEVICE_ID.put("4.6", "blackberry_generic_ver4_sub60");
        OS_VERSION_TO_DEVICE_ID.put("4.7", "blackberry_generic_ver4_sub70");
        OS_VERSION_TO_DEVICE_ID.put("4.", "blackberry_generic_ver4");
        OS_VERSION_TO_DEVICE_ID.put("5.", "blackberry_generic_ver5");
        OS_VERSION_TO_DEVICE_ID.put("6.", "blackberry_generic_ver6");
        OS_VERSION_TO_DEVICE_ID.put("10", BLACKBERRY_GENERIC_VER10);
        OS_VERSION_TO_DEVICE_ID.put("10t", BLACKBERRY_GENERIC_VER10_TABLET);
        BLACKBERRY_OS_VERSION = Pattern.compile("BlackBerry[^/\\s]+/(\\d\\.\\d)");
    }

    public BlackBerryMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.addAll(OS_VERSION_TO_DEVICE_ID.values());
        requiredDeviceIds.add("generic_mobile");
        requiredDeviceIds.add(RIM_PLAYBOOK_VER1);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        boolean isBlackBerryUserAgent = cleanedDeviceUserAgent != null && (
                cleanedDeviceUserAgent.toLowerCase().contains("blackberry")
                        || StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "(BB10;", "(PlayBook")
        );
        return !request._internalIsDesktopBrowser() && isBlackBerryUserAgent;
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        if (normalizedUserAgent.contains("BB10")) {
            matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, ")");
        } else if (normalizedUserAgent.startsWith("Mozilla/4")) {
            matchLength = StringMatchUtils.secondSlash(normalizedUserAgent);
        } else if (normalizedUserAgent.startsWith("Mozilla/5")) {
            matchLength = StringMatchUtils.ordinalIndexOfOrNotFound(normalizedUserAgent, ";", 3);
        } else if (normalizedUserAgent.contains("PlayBook")) {
            matchLength = StringMatchUtils.firstCloseParenthesis(normalizedUserAgent);
        } else {
            matchLength = StringMatchUtils.firstSlash(normalizedUserAgent);
        }

        return matchLength != -1
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength)
                : StringMatchUtils.NULL_STRING;
    }

    @Override
    /**
     * 恢复匹配策略：根据 User-Agent 中的 BlackBerry 版本信息返回对应的通用设备 ID。
     * <p>分别处理 BB10、PlayBook 和传统 BlackBerry OS 三种场景。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        Matcher osVersionMatcher = BLACKBERRY_OS_VERSION.matcher(normalizedDeviceUserAgent);
        String osVersion = osVersionMatcher.find() ? osVersionMatcher.group(1) : null;
        if (normalizedDeviceUserAgent.contains("BB10")) {
            return normalizedDeviceUserAgent.contains("Mobile") ? BLACKBERRY_GENERIC_VER10 : BLACKBERRY_GENERIC_VER10_TABLET;
        } else if (normalizedDeviceUserAgent.contains("PlayBook")) {
            return RIM_PLAYBOOK_VER1;
        } else {
            if (osVersion != null) {
                for (Map.Entry<String, String> entry : OS_VERSION_TO_DEVICE_ID.entrySet()) {
                    if (osVersion.contains(entry.getKey())) {
                        return entry.getValue();
                    }
                }
            }

            return "generic";
        }
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "BlackBerryMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "BlackBerry";
    }
}
