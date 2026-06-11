package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * KDDI 日本运营商品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 "KDDI-" 来识别 KDDI 品牌的移动设备。
 * RIS 匹配根据 User-Agent 是否以 "KDDI/" 开头选择不同的斜杠位置截断策略。</p>
 */

final class KDDIMatcher extends MatcherBase {
    private static final String OPWV_V62_GENERIC = "opwv_v62_generic";

    public KDDIMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds;
        requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(OPWV_V62_GENERIC);
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        return !request._internalIsDesktopBrowser() && request.getCleanedDeviceUserAgent().contains("KDDI-");
    }

    /**
     * 执行 RIS 匹配：以 "KDDI/" 开头的取第二个斜杠位置，其他情况取第一个斜杠位置。
     *
     * @param normalizedUserAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */

    protected String risMatch(String normalizedUserAgent) {
        int matchLength = normalizedUserAgent.startsWith("KDDI/") ? StringMatchUtils.secondSlash(normalizedUserAgent) : StringMatchUtils.firstSlash(normalizedUserAgent);
        return matchLength == -1
                ? StringMatchUtils.NULL_STRING
                : StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
    }

    /**
     * 恢复匹配策略：统一返回 Openwave (OPWV) v6.2 通用设备 ID。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        return OPWV_V62_GENERIC;
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "KDDIMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Kddi";
    }
}
