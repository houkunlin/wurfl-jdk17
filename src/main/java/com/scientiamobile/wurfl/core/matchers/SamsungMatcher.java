package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Samsung（三星）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否以 SEC-、SPH、SGH、SCH 等前缀开头或包含 samsung 来识别三星品牌的移动设备。</p>
 */

final class SamsungMatcher extends MatcherBase {
    private static final String SAMSUNG = "Samsung";
    private static final String[] LEADING_SLASH_PREFIXES = new String[]{"SEC-", "SAMSUNG-", "SCH"};
    private static final String[] LEADING_SPACE_PREFIXES = new String[]{SAMSUNG, "SPH", "SGH"};
    private static final String[] CAN_HANDLE_PREFIXES = new String[]{"SEC-", "SPH", "SGH", "SCH"};

    public SamsungMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add("generic");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        if (request.getOriginalUserAgent().contains("SamsungBrowser")) {
            return false;
        }
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser()
                && (StringMatchUtils.startsWithAnyOf(cleanedDeviceUserAgent, CAN_HANDLE_PREFIXES)
                || cleanedDeviceUserAgent.toLowerCase().contains("samsung"));
    }

    /**
     * 根据 User-Agent 的前缀特征选择不同的截断位置执行 RIS 匹配。
     * <ul>
     *   <li>以 "SEC-"、"SAMSUNG-"、"SCH" 开头的 → 取第一个斜杠位置</li>
     *   <li>以 "Samsung"、"SPH"、"SGH" 开头的 → 取第一个空格位置</li>
     *   <li>其他情况 → 取第二个斜杠位置</li>
     * </ul>
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */
    @Override
    protected String risMatch(String userAgent) {
        int matchLength;
        if (StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SLASH_PREFIXES)) {
            matchLength = StringMatchUtils.firstSlash(userAgent);
        } else if (StringMatchUtils.startsWithAnyOf(userAgent, LEADING_SPACE_PREFIXES)) {
            matchLength = StringMatchUtils.firstSpace(userAgent);
        } else {
            matchLength = StringMatchUtils.secondSlash(userAgent);
        }
        if (matchLength == -1) {
            return StringMatchUtils.NULL_STRING;
        }
        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 恢复匹配策略：找到 "Samsung" 关键字后的斜杠位置，基于该长度尝试 RIS 匹配。
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID，未匹配则返回 "generic"
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        int samsungIndex = StringMatchUtils.indexOf(normalizedUserAgent, SAMSUNG);
        int matchLength = StringMatchUtils.indexOfOrLength(normalizedUserAgent, "/", samsungIndex);
        String matchedUserAgent = StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), normalizedUserAgent, matchLength);
        return !StringUtils.isBlank(matchedUserAgent) ? this.getFilter().getIndex().getDeviceIdByUserAgent(matchedUserAgent) : "generic";
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "SamsungMatcher";
    }

    /**
     * 获取桶匹配器名称。
     *
     * @return 固定返回 {@code "Samsung"}
     */
    @Override
    public String getBucketMatcherName() {
        return SAMSUNG;
    }
}
