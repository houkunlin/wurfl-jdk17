package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.request.normalizer.UserAgentNormalizer;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Maemo 操作系统匹配器（诺基亚 N900 等设备）。
 * <p>通过检查 User-Agent 是否包含 Maemo 来识别。恢复匹配根据浏览器类型（Opera Mobi 或 Firefox）返回对应的通用设备 ID。</p>
 */

final class MaemoMatcher extends MatcherBase {
    private static final String GENERIC_OPERA_MOBI_MAEMO = "generic_opera_mobi_maemo";
    private static final String NOKIA_GENERIC_MAEMO_WITH_FIREFOX = "nokia_generic_maemo_with_firefox";
    private static final String NOKIA_GENERIC_MAEMO = "nokia_generic_maemo";

    public MaemoMatcher(UserAgentNormalizer normalizer, WURFLModel wurflModel) {
        super(normalizer, wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(GENERIC_OPERA_MOBI_MAEMO);
        requiredDeviceIds.add(NOKIA_GENERIC_MAEMO_WITH_FIREFOX);
        requiredDeviceIds.add(NOKIA_GENERIC_MAEMO);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return request.getCleanedDeviceUserAgent().contains("Maemo");
    }

    /**
     * 恢复匹配策略：根据 User-Agent 是否包含 "Opera Mobi" 或 "Firefox" 返回不同的通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */
    @Override
    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedDeviceUserAgent;
        normalizedDeviceUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedDeviceUserAgent.contains("Opera Mobi")) {
            return GENERIC_OPERA_MOBI_MAEMO;
        } else {
            return normalizedDeviceUserAgent.contains("Firefox") ? NOKIA_GENERIC_MAEMO_WITH_FIREFOX : NOKIA_GENERIC_MAEMO;
        }
    }

    /**
     * 执行 RIS 匹配：以 "---" 分隔符位置加 3 截断，如果没有则退回到基类的 RIS 方法。
     *
     * @param normalizedUserAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String normalizedUserAgent) {
        int matchLength;
        matchLength = normalizedUserAgent.indexOf("---");
        return matchLength >= 0
                ? StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength + 3)
                : super.risMatch(normalizedUserAgent);
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "MaemoMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Maemo";
    }
}
