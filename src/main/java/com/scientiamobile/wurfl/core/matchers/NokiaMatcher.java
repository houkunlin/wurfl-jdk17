package com.scientiamobile.wurfl.core.matchers;

import com.scientiamobile.wurfl.core.request.WURFLRequest;
import com.scientiamobile.wurfl.core.resource.WURFLModel;
import com.scientiamobile.wurfl.core.utils.StringMatchUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Nokia（诺基亚）品牌设备匹配器。
 * <p>通过检查 User-Agent 是否包含 Nokia 来识别诺基亚品牌的移动设备。支持 Series60、Series80 和 MeeGo 平台的恢复匹配。</p>
 */

final class NokiaMatcher extends MatcherBase {
    private static final String NOKIA_GENERIC_SERIES60 = "nokia_generic_series60";
    private static final String NOKIA_GENERIC_SERIES80 = "nokia_generic_series80";
    private static final String NOKIA_GENERIC_MEEGO = "nokia_generic_meego";

    public NokiaMatcher(WURFLModel wurflModel) {
        super(wurflModel);
    }

    /**
     * 返回所需验证的设备 ID 集合.
     */
    @Override
    protected Set<String> getRequiredDeviceIds() {
        HashSet<String> requiredDeviceIds = new HashSet<>();
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES60);
        requiredDeviceIds.add(NOKIA_GENERIC_SERIES80);
        requiredDeviceIds.add(NOKIA_GENERIC_MEEGO);
        requiredDeviceIds.add("generic_mobile");
        return requiredDeviceIds;
    }

    /**
     * 判断当前匹配器能否处理该请求.
     */
    @Override
    public boolean canHandle(WURFLRequest request) {
        String cleanedDeviceUserAgent = request.getCleanedDeviceUserAgent();
        return !request._internalIsDesktopBrowser() && cleanedDeviceUserAgent.contains("Nokia") && !StringMatchUtils.containsAnyOf(cleanedDeviceUserAgent, "Android", "iPhone");
    }

    /**
     * 执行 RIS 匹配：找到 "Nokia" 关键字后的斜杠或空格位置作为截断点。
     * <p>如果 User-Agent 以 "Nokia/" 或 "Nokia " 开头，则使用完整的字符串长度进行匹配。</p>
     *
     * @param userAgent 要匹配的 User-Agent 字符串
     * @return RIS 匹配结果
     */

    protected String risMatch(String userAgent) {
        int matchLength = StringMatchUtils.indexOfAnyOrLength(userAgent, new String[]{"/", " "}, userAgent.indexOf("Nokia"));
        if (StringMatchUtils.startsWithAnyOf(userAgent, "Nokia/", "Nokia ")) {
            matchLength = userAgent.length();
        }

        return StringMatchUtils.risMatch(this.getFilter().getIndex().getUserAgents(), userAgent, matchLength);
    }

    /**
     * 恢复匹配策略：根据 User-Agent 中包含的平台关键字返回对应的通用设备 ID。
     * <p>分别处理 Series60、Series80 和 MeeGo 平台。</p>
     *
     * @param request WURFL 请求对象
     * @return 恢复匹配的设备 ID
     */

    protected String applyRecoveryMatch(WURFLRequest request) {
        String normalizedUserAgent = request.getNormalizedDeviceUserAgent();
        if (normalizedUserAgent.contains("Series60")) {
            return NOKIA_GENERIC_SERIES60;
        } else if (normalizedUserAgent.contains("Series80")) {
            return NOKIA_GENERIC_SERIES80;
        } else {
            return normalizedUserAgent.contains("MeeGo") ? NOKIA_GENERIC_MEEGO : "generic";
        }
    }

    /**
     * 获取匹配器名称.
     */
    @Override
    public String getMatcherName() {
        return "NokiaMatcher";
    }

    /**
     * 获取桶匹配器名称.
     */
    @Override
    public String getBucketMatcherName() {
        return "Nokia";
    }
}
