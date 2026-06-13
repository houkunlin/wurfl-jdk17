package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Skyfire 浏览器匹配器。
 * <p>通过检查 User-Agent 是否包含 Skyfire 来识别 Skyfire 移动浏览器。支持 Skyfire v1 和 v2 的恢复匹配。</p>
 *
 * @deprecated 当前 WURFL 数据文件中已无 Skyfire 设备（对应设备数为 0），
 * 该匹配器已不再参与实际设备匹配，保留仅用于兼容旧版 WURFL 数据。
 * 计划在后续主要版本中移除。
 */
@Deprecated
final class SkyfireMatcher extends MatcherBase {
    private static final String GENERIC_SKYFIRE_VERSION2 = "generic_skyfire_version2";
    private static final String GENERIC_SKYFIRE_VERSION1 = "generic_skyfire_version1";

    public SkyfireMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION1);
        requiredDeviceIds.add(GENERIC_SKYFIRE_VERSION2);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Skyfire");
    }

    /**
     * 执行 RIS 匹配.
     */
    @Override
    protected String risMatch(String userAgent) {
        int skyfireIndex = StringMatchUtils.indexOf(userAgent, "Skyfire");
        int matchLength = StringMatchUtils.indexOfOrLength(userAgent, ".", skyfireIndex);
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 恢复匹配策略：根据 User-Agent 是否包含 "Skyfire/2." 返回对应的版本。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        return request.getNormalizedDeviceUserAgent().contains("Skyfire/2.") ? GENERIC_SKYFIRE_VERSION2 : GENERIC_SKYFIRE_VERSION1;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SkyfireMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Skyfire";
    }
}
