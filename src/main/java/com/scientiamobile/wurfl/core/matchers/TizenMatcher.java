package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tizen 操作系统匹配器（三星主导的 Linux 移动/智能设备操作系统）。
 * <p>通过检查 User-Agent 是否以 Mozilla 开头且包含 Tizen 来识别。支持 Tizen 1.0 到 3.0 的版本映射。</p>
 */

public class TizenMatcher extends MatcherBase {
    private static final String GENERIC_TIZEN = "generic_tizen";
    private static final Pattern TIZEN_VERSION_PATTERN = Pattern.compile("Tizen (\\d+?\\.\\d+?)");
    private static final List<String> SUPPORTED_DEVICE_IDS = new ArrayList<>();
    private static final List<String> SUPPORTED_VERSIONS = new ArrayList<>();

    static {
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver1_0");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_0");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_1");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_2");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_3");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver2_4");
        SUPPORTED_DEVICE_IDS.add("generic_tizen_ver3_0");
        SUPPORTED_VERSIONS.add("1.0");
        SUPPORTED_VERSIONS.add("2.0");
        SUPPORTED_VERSIONS.add("2.1");
        SUPPORTED_VERSIONS.add("2.2");
        SUPPORTED_VERSIONS.add("2.3");
        SUPPORTED_VERSIONS.add("2.4");
        SUPPORTED_VERSIONS.add("3.0");
    }

    public TizenMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    @Override
/**
 * 返回所需验证的设备 ID 集合.
 */

    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>(SUPPORTED_DEVICE_IDS);
        requiredDeviceIds.add(GENERIC_TIZEN);
        return requiredDeviceIds;
    }

    @Override
/**
 * 判断当前匹配器能否处理该请求.
 */

    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return cleanedDeviceUserAgent.startsWith("Mozilla") && cleanedDeviceUserAgent.contains("Tizen");
    }

    @Override
/**
 * 执行 RIS 匹配.
 */

    protected String risMatch(String userAgent) {
        int appleWebKitIndex = userAgent.indexOf("AppleWebKit/");
        return appleWebKitIndex >= 0 ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, appleWebKitIndex + 12) : null;
    }

    @Override
    /**
     * 恢复匹配策略：从 User-Agent 中提取 Tizen 版本号，构造对应的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        Matcher versionMatcher = TIZEN_VERSION_PATTERN.matcher(normalizedUserAgent);
        String deviceIdSuffix = versionMatcher.find() && SUPPORTED_VERSIONS.contains(versionMatcher.group(1)) ? versionMatcher.group(1).replace('.', '_') : "1_0";
        String tizenDeviceId = (new StringBuilder("generic_tizen_ver")).append(deviceIdSuffix).toString();
        return SUPPORTED_DEVICE_IDS.contains(tizenDeviceId) ? tizenDeviceId : GENERIC_TIZEN;
    }

    @Override
/**
 * 获取匹配器名称.
 */

    public String getMatcherName() {
        return "TizenMatcher";
    }

    @Override
/**
 * 获取桶匹配器名称.
 */

    public String getBucketMatcherName() {
        return "Tizen";
    }
}
